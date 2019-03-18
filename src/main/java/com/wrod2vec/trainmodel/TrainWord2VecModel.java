package com.wrod2vec.trainmodel;

import com.hankcs.hanlp.corpus.io.IOUtil;
import com.hankcs.hanlp.mining.word2vec.DocVectorModel;
import com.hankcs.hanlp.mining.word2vec.Word2VecTrainer;
import com.hankcs.hanlp.mining.word2vec.WordVectorModel;
import com.rongpingkeji.common.util.ExceptionUtil;
import com.wrod2vec.trainmodel.dto.NearWordDTO;
import com.wrod2vec.utils.PathSummary;
import lombok.extern.slf4j.Slf4j;


import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 训练词向量模型
 */

@Slf4j
public class TrainWord2VecModel {

    public static final String TRAIN_FILE_NAME = PathSummary.CorpusPath;
    public static final String MODEL_FILE_NAME = PathSummary.Word2VecModel;


    /**
     * 训练模型
     *
     * @param inputPath
     * @param modelName
     * @return
     * @throws IOException
     */
    public static WordVectorModel trainOrLoadModel(String inputPath, String modelName) throws IOException {

        String outPath = MODEL_FILE_NAME + modelName;
        if (!IOUtil.isFileExisted(outPath)) {
            if (!IOUtil.isFileExisted(inputPath)) {
                ExceptionUtil.BaseError("");
                System.exit(1);
            }
            Word2VecTrainer trainerBuilder = new Word2VecTrainer();
            trainerBuilder.setLayerSize(200);
            trainerBuilder.setCallback(new TrainCallback());
            return trainerBuilder.train(inputPath, outPath);
        }
        return loadModel(modelName);
    }


    /**
     * 训练模型
     *
     * @param inputPath
     * @param modelName
     * @return
     * @throws IOException
     */
    public static WordVectorModel trainOrLoadModel(String inputPath, String modelName, int layer) throws IOException {

        String outPath = MODEL_FILE_NAME + modelName;
        if (!IOUtil.isFileExisted(outPath)) {
            if (!IOUtil.isFileExisted(inputPath)) {
                ExceptionUtil.BaseError("");
                System.exit(1);
            }
            Word2VecTrainer trainerBuilder = new Word2VecTrainer();
            trainerBuilder.setLayerSize(layer);
            trainerBuilder.setCallback(new TrainCallback());
            return trainerBuilder.train(inputPath, outPath);
        }
        return loadModel(modelName);
    }


    /**
     * 加载词向量模型
     *
     * @return
     * @throws IOException
     */
    public static WordVectorModel loadModel(String name) throws IOException {
        return new WordVectorModel(MODEL_FILE_NAME + name);
    }


    /**
     * 输出测试
     *
     * @param word
     * @param model
     */
    public static List<NearWordDTO> getNearWords(String word, WordVectorModel model) {
        List<NearWordDTO> data = new ArrayList<>();
        for (Map.Entry<String, Float> entry : model.nearest(word)) {
            NearWordDTO item = new NearWordDTO();
            item.setDistance(entry.getValue());
            item.setWords(entry.getKey());
            data.add(item);
        }
        return data;
    }


    /**
     * 计算两个句子的相似度
     *
     * @param modelName
     * @param sentence_1
     * @param sentence_2
     * @return
     */
    public static float getSectenceDistance(String modelName, String sentence_1, String sentence_2) {
        WordVectorModel wordVectorModel = null;
        try {
            wordVectorModel = loadModel(modelName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        DocVectorModel docVectorModel = new DocVectorModel(wordVectorModel);
        return docVectorModel.similarity(sentence_1, sentence_2);
    }


    /**
     * 测试文档间距离
     * @param args
     */


    /**
     * 测试预算
     *
     * @param args
     */

    public static void main(String[] args) {
        try {
            WordVectorModel wordVectorModel = loadModel("002.txt");
            log.info("附近单词={}", getNearWords("洗手间", wordVectorModel));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
