package com.wrod2vec.trainmodel.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("训练进度")
@Data
public class TrainProcessDTO {


    @ApiModelProperty("语料加载进度")
    private float loadCorpus;

    @ApiModelProperty("加载语料花费时间")
    private String loadCorpusTime;

    @ApiModelProperty("词表大小")
    private int wordsNum;

    @ApiModelProperty("学习率")
    private float nTa;

    @ApiModelProperty("训练进度")
    private float loadTrain;

    @ApiModelProperty("训练模型花费时间")
    private String loadTrainTime;

}
