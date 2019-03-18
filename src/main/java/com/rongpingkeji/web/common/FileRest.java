package com.rongpingkeji.web.common;

import com.rongpingkeji.common.client.fileservice.FileService;
import com.rongpingkeji.common.util.http.ResponseMessage;
import com.rongpingkeji.common.util.http.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.io.IOException;
import java.util.Map;

/**
 * Created by Daniel on 2019/2/16.
 */
@RestController
@RequestMapping("/common/file")
@Api(value = "文件接口", description = "文件的上传、下载...")
@Slf4j
public class FileRest {

    @Autowired
    @Qualifier("oos")
    private FileService fileService;

    @ApiOperation("上传文件")
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseMessage uploadFile(@RequestParam("file") MultipartFile file,@RequestParam("resource") String objectName) throws IOException {
        log.info("resource={}",objectName);
        Map<String, String> result = fileService.saveFileCenter(file.getOriginalFilename(), file.getSize(), file.getInputStream(), objectName,true);
        return Result.success("上传成功", result);
    }


}
