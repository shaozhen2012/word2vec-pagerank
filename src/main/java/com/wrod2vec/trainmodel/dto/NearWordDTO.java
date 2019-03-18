package com.wrod2vec.trainmodel.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("附近的词汇")
@Data
public class NearWordDTO {

    @ApiModelProperty("单词")
    private String words;

    @ApiModelProperty("相似度")
    private float distance;

}
