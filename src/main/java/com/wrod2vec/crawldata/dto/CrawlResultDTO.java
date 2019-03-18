package com.wrod2vec.crawldata.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@ApiModel("评论爬取结果")
@Data
public class CrawlResultDTO {

    @ApiModelProperty("评论数量")
    private long number;

    @ApiModelProperty("有效评论数量")
    private long valueNum;

    @ApiModelProperty("评论数据")
    private List<ReviewsDTO> reviews;
}
