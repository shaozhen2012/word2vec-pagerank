package com.rongpingkeji.web.app;

import com.hankcs.hanlp.mining.word2vec.DocVectorModel;
import com.hankcs.hanlp.mining.word2vec.WordVectorModel;
import com.rongpingkeji.common.client.fileservice.FileService;
import com.rongpingkeji.common.client.opencv.OpenCVVideoService;
import com.rongpingkeji.common.client.opencv.dto.VideoResponse;
import com.rongpingkeji.common.util.FileUtil;
import com.rongpingkeji.common.util.JsonUtil;
import com.rongpingkeji.common.util.http.ResponseMessage;
import com.rongpingkeji.common.util.http.ResponseMessageCodeEnum;
import com.rongpingkeji.common.util.http.Result;
import com.rongpingkeji.data.dto.form.VideoForm;
import com.rongpingkeji.data.entity.VideoCenterDO;
import com.rongpingkeji.service.VideoService;
import com.wrod2vec.crawldata.CrawlDianping;
import com.wrod2vec.crawldata.dto.CrawlResultDTO;
import com.wrod2vec.crawldata.dto.ReviewsDTO;
import com.wrod2vec.pagerank.PageRank;
import com.wrod2vec.pagerank.dto.PageRankResultDTO;
import com.wrod2vec.segwords.DivideWords;
import com.wrod2vec.trainmodel.TrainCallback;
import com.wrod2vec.trainmodel.TrainWord2VecModel;
import com.wrod2vec.trainmodel.dto.NearWordDTO;
import com.wrod2vec.trainmodel.dto.TrainProcessDTO;
import com.wrod2vec.utils.MongodbUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.management.modelmbean.ModelMBean;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Daniel on 2019/1/30.
 */
@RestController
@RequestMapping("/wvp")
@Api(value = "视频接口", description = "视频的上传，opencv处理接口")
@Slf4j
public class Word2vecRest {


    private String pagerankInput = "Corpus/pagerank/input/";
    private String pagerankOutput = "Corpus/pagerank/output/";

    @ApiOperation("提取评论集数据")
    @PostMapping("/crawl")
    public ResponseMessage<CrawlResultDTO> crawl(@RequestParam("name") String name) {
        CrawlDianping crawlDianping = new CrawlDianping();
        List<ReviewsDTO> data = crawlDianping.crawlFloderReviews(name);
        int valueNum = crawlDianping.insertIntoDb(data);
        CrawlResultDTO result = new CrawlResultDTO();
        result.setNumber(data.size());
        result.setValueNum(valueNum);
        result.setReviews(data);
        return Result.success("爬取评论数据", result);
    }


    @ApiOperation("分词工具")
    @PostMapping("/segword")
    public ResponseMessage<List<String>> segwords(@RequestParam("sentence") String sentence) {
        DivideWords divideWords = new DivideWords();
        String[] result = divideWords.divideWords(sentence);
        return Result.success("分词结果", Arrays.asList(result));
    }


    @ApiOperation("查询附近词汇")
    @PostMapping("/findNearWord")
    public ResponseMessage<List<NearWordDTO>> findNearWord(@RequestParam("model") String modelName, @RequestParam("word") String word) {
        WordVectorModel wordVectorModel = null;
        try {
            wordVectorModel = TrainWord2VecModel.loadModel(modelName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<NearWordDTO> result = TrainWord2VecModel.getNearWords(word, wordVectorModel);
        return Result.success("查找附近词汇", result);
    }


    @ApiOperation("训练模型")
    @PostMapping("/train")
    public ModelMap train(int layer, String modelName, int state, HttpServletResponse response) {
        ModelMap modelMap = new ModelMap();
        modelMap.put("code", ResponseMessageCodeEnum.SUCCESS.getCode());
        response.setContentType("application/json;charset=UTF-8");
        modelMap.put("msg", "开始训练模型");

        if (state == 0) {
            log.info("开始训练模型");
            Thread thread = new Thread() {
                @Override
                public void run() {
                    try {
                        TrainWord2VecModel.trainOrLoadModel(TrainWord2VecModel.TRAIN_FILE_NAME, modelName, layer);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            };
            thread.start();
        }
        return modelMap;
    }


    @ApiOperation("加载训练进度")
    @PostMapping("/loadTrain")
    public ResponseMessage loadTrain() {
        String data = "";
        try {
            data = FileUtil.readFileToString(new File(TrainCallback.loadingText), "utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Result.success("训练进度", data);
    }


    @ApiOperation("计算评论相似度")
    @PostMapping("/distance")
    public ResponseMessage<ModelMap> distance(String modelName, String sentence_1, String sentence_2) {
        ModelMap result = new ModelMap();
        float value = TrainWord2VecModel.getSectenceDistance(modelName, sentence_1, sentence_2);
        result.put("distance", String.format("%.4f", value));
        return Result.success("计算句子间相似度", result);
    }


    @ApiOperation("提取评论")
    @PostMapping("/getTopReviews")
    public ResponseMessage<List<String>> getTopReviews(String modelName, String dataSet, int number) {
        WordVectorModel model = null;
        try {
            model = TrainWord2VecModel.loadModel(modelName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<String> result = new ArrayList<>();
        CrawlDianping crawlDianping = new CrawlDianping();
        List<ReviewsDTO> data = crawlDianping.findByCollection(dataSet).getReviews();
        DocVectorModel docVectorModel = new DocVectorModel(model);
        String inputName = pagerankInput + dataSet + ".txt";
        File file = new File(inputName);
        String line = "";
        if (!file.exists()) {
            for (int i = 0; i < data.size(); i++) {
                for (int j = i + 1; j < data.size(); j++) {
                    float itemDistance = docVectorModel.similarity(data.get(i).getReviewContent(), data.get(j).getReviewContent());
                    if (itemDistance > 0.5) {  //相似度大于0.5的考虑进入权重
                        line += data.get(i).getReviewId() + "\t" + data.get(j).getReviewId() + "\t" + itemDistance + "\n";
                        FileUtil.writeFile(line.getBytes(), inputName);
                    }
                    log.info("句子1={}", data.get(i).getReviewId());
                    log.info("句子2={}", data.get(j).getReviewId());
                    log.info("相似度={}", itemDistance);
                    FileUtil.writeFile(line.getBytes(), inputName);
                }
            }
        }
        int iterations = 10;
        double dumpingFactor = 0.85;
        PageRank pagerank = new PageRank();
        pagerank.initializeMap(inputName);
        pagerank.rank(iterations, dumpingFactor);
        pagerank.showResults(number);
        pagerank.saveRankedResults(pagerankOutput + dataSet + ".txt");
        List<PageRankResultDTO> results = pagerank.getRankedResults();
        for (int i = 0; i < number; i++) {
            log.info("评论排名数据={}", results.get(i));
            result.add(MongodbUtil.findValue(dataSet, results.get(i).getNode()).getReviewContent());
        }
        return Result.success("提取核心评论", result);
    }


}
