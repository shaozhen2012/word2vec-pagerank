package com.wrod2vec.trainmodel;

import com.hankcs.hanlp.mining.word2vec.TrainingCallback;
import com.rongpingkeji.common.util.FileUtil;
import com.rongpingkeji.common.util.JsonUtil;
import com.rongpingkeji.common.util.http.ResponseMessageCodeEnum;
import com.wrod2vec.trainmodel.dto.TrainProcessDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.ui.ModelMap;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

public class TrainCallback implements TrainingCallback {


    private long startTime;

    public static String loadingText = "Corpus/model/loading.txt";

    private TrainProcessDTO trainProcessDTO;


    private ModelMap modelMap;


    public TrainCallback() {
        startTime = System.currentTimeMillis();
        trainProcessDTO = new TrainProcessDTO();
        modelMap = new ModelMap();
        modelMap.put("code", ResponseMessageCodeEnum.SUCCESS.getCode());
        //FileUtil.writeFile(JsonUtil.stringify(modelMap).getBytes(), loadingText);
    }

    public void corpusLoading(float v) {
        trainProcessDTO.setLoadCorpus(v);
        System.out.println("语料加载进度：" + v + "%");
        if (v < 100) {
            modelMap.put("data", trainProcessDTO);
            FileUtil.writeFile(JsonUtil.stringify(modelMap).getBytes(), loadingText);
        }
    }

    public void corpusLoaded(int i, int i1, int i2) {
        long endTime = System.currentTimeMillis();
        System.out.println("语料加载完毕，花费：" + ((endTime - startTime) / 1000 / 60) + "m");

        trainProcessDTO.setLoadCorpusTime(((endTime - startTime) / 1000 / 60) + "m");
        trainProcessDTO.setWordsNum(i2);
        modelMap.put("data", trainProcessDTO);
        FileUtil.writeFile(JsonUtil.stringify(modelMap).getBytes(), loadingText);
        System.out.println("词表大小：" + i);
        System.out.println("训练词数：" + i1);
        System.out.println("语料词数：" + i2);
    }


    /**
     * 训练模型
     *
     * @param v
     * @param v1
     */

    public void training(float v, float v1) {

        System.out.println("学习率：" + v);
        System.out.println("训练进度：" + v1 + "%");
        if (v1 >= 100) {
            long endTime = System.currentTimeMillis();
            System.out.println("模型训练完成，花费：" + ((endTime - startTime) / 1000 / 60) + "m");
            trainProcessDTO.setLoadTrainTime(((endTime - startTime) / 1000 / 60) + "m");
        }
        trainProcessDTO.setNTa(v);
        trainProcessDTO.setLoadTrain(v1);
        modelMap.put("data", trainProcessDTO);
        FileUtil.writeFile(JsonUtil.stringify(modelMap).getBytes(), loadingText);

    }
}
