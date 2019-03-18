package com.wrod2vec.pagerank.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("pagerank排名结果")
@Data
public class PageRankResultDTO {



    @ApiModelProperty("句子编号")
    private String node;

    @ApiModelProperty("句子PR值")
    private double value;

}
