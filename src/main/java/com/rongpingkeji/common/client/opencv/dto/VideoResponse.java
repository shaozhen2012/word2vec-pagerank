package com.rongpingkeji.common.client.opencv.dto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by Daniel on 2019/2/16.
 */
public class VideoResponse {


    @Data
    public  static  class VideoFrameResponse{

        @ApiModelProperty("帧的数量")
        private int frameNum;

        @ApiModelProperty("视频名称")
        private String videoName;

        @ApiModelProperty("视频地址")
        private String videoLink;

        @ApiModelProperty("压缩文件的地址")
        private String zipLink;
    }
}
