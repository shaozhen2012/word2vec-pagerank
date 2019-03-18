package com.rongpingkeji.web.app;

import com.rongpingkeji.common.client.fileservice.FileService;
import com.rongpingkeji.common.client.opencv.OpenCVVideoService;
import com.rongpingkeji.common.client.opencv.dto.VideoResponse;
import com.rongpingkeji.common.util.http.ResponseMessage;
import com.rongpingkeji.common.util.http.Result;
import com.rongpingkeji.data.dto.form.VideoForm;
import com.rongpingkeji.data.entity.VideoCenterDO;
import com.rongpingkeji.service.VideoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Created by Daniel on 2019/1/30.
 */
@RestController
@RequestMapping("/app/video")
@Api(value = "视频接口", description = "视频的上传，opencv处理接口")
@Slf4j
public class VideoRest {


    @Value("${application.service.oss.filePath}")
    private String filePath;

    @Autowired
    private OpenCVVideoService video;

    @Autowired
    private VideoService videoService;


    @ApiOperation("读取视频帧打包下载")
    @PostMapping("/videoFrame")
    public ResponseMessage<VideoResponse.VideoFrameResponse> getVideoFrame(@Valid @RequestBody VideoForm.VideoFrame videoFrame) {

        VideoResponse.VideoFrameResponse data = video.getVideoFrameToAliyun(videoFrame.getVideoLink(), videoFrame.getProjectName());
        String zipLink = video.zipOssDirectory(filePath + videoFrame.getProjectName() + "/" + data.getVideoName(), videoFrame.getProjectName(), data.getVideoName() + ".zip");
        data.setZipLink(zipLink);
        data.setVideoLink(videoFrame.getVideoLink());
        VideoCenterDO param = new VideoCenterDO();
        param.setVideo_file_zip(zipLink);
        param.setVideo_frame_num(data.getFrameNum());
        param.setVideo_name(videoFrame.getVideoName());
        param.setVideo_link(videoFrame.getVideoLink());
        videoService.insert(param);
        return Result.success("读取视频帧", data);
    }


}
