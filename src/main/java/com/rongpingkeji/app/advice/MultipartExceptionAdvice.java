package com.rongpingkeji.app.advice;

import com.rongpingkeji.common.util.http.ResponseMessage;
import com.rongpingkeji.common.util.http.Result;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartException;

@ControllerAdvice
@Slf4j
public class MultipartExceptionAdvice {


    @Value("${application.file.upload.maxSize}")
    private String maxFileSize;

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(MultipartException.class)
    @ResponseBody
    public ResponseMessage handlerOneBaseException(MultipartException exception) {
        log.warn(exception.getMessage(), exception);
        if (exception.getMessage().contains("temporary upload location")) {
            return Result.error("系统错误，请重启服务。");
        }
        return Result.error("请选择文件");
    }

}
