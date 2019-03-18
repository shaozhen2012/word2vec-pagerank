package com.wrod2vec.crawldata.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("评论数据")
public class ReviewsDTO {

    @ApiModelProperty("评论编号")
    private String reviewId;
    @ApiModelProperty("酒店名称")
    private String hotelName;
    @ApiModelProperty("评论时间")
    private String reviewTime;
    @ApiModelProperty("酒店编号")
    private String hotelID;
    @ApiModelProperty("评论内容")
    private String reviewContent;
}
