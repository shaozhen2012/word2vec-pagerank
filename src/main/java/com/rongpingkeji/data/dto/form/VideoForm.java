package com.rongpingkeji.data.dto.form;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * Created by Daniel on 2019/2/16.
 */


public class VideoForm {


    @ApiModel("读取视频帧")
    @Data
    public static class VideoFrame {

        @NotBlank(message = "请输入视频地址")
        private String videoLink;

        @NotBlank(message = "所属项目")
        private String projectName;

        @NotBlank(message = "请输入视频名称")
        private String videoName;
    }


}
