package com.rongpingkeji.app.advice;


import com.rongpingkeji.common.exception.LoginInvalidException;
import com.rongpingkeji.common.exception.NoAuthException;
import com.rongpingkeji.common.exception.RpBaseException;
import com.rongpingkeji.common.util.http.ResponseMessage;
import com.rongpingkeji.common.util.http.ResponseMessageCodeEnum;
import com.rongpingkeji.common.util.http.Result;
import com.rongpingkeji.common.util.http.ValidError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@ControllerAdvice
public class OneBaseExceptionAdvice {

    private static final Logger logger = LoggerFactory.getLogger(OneBaseExceptionAdvice.class);

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(LoginInvalidException.class)
    @ResponseBody
    public ResponseMessage handlerOneBaseException(LoginInvalidException exception) {
        logger.warn(exception.getMessage(), exception);
        return Result.error(ResponseMessageCodeEnum.RE_LOGIN.getCode(), ResponseMessageCodeEnum.RE_LOGIN.getMessage());
    }


    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(NoAuthException.class)
    @ResponseBody
    public ResponseMessage handlerOneBaseException(NoAuthException exception) {
        logger.warn(exception.getMessage(), exception);
        return Result.error(ResponseMessageCodeEnum.FORBIDDEN.getCode(), ResponseMessageCodeEnum.FORBIDDEN.getMessage());
    }




    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(RpBaseException.class)
    @ResponseBody
    public ResponseMessage handlerOneBaseException(RpBaseException exception) {
        logger.warn(exception.getMessage(), exception);
        return Result.error(exception.getErrorCode(), exception.getMessage());
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseMessage handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        List<ValidError> validErrorList = new ArrayList<>();
        BindingResult result = exception.getBindingResult();
        String message = ResponseMessageCodeEnum.VALID_ERROR.getMessage();
        for (FieldError fieldError : result.getFieldErrors()) {
            message = fieldError.getDefaultMessage();
            break;
        }
        return Result.error(ResponseMessageCodeEnum.VALID_ERROR.getCode(), message);
    }


    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseMessage<List<ValidError>> handleConstraintViolationException(ConstraintViolationException exception) {
        List<ValidError> validErrorList = new ArrayList<>();
        Set<ConstraintViolation<?>> violationSet = exception.getConstraintViolations();
        for (ConstraintViolation violation : violationSet) {
            validErrorList.add(new ValidError(violation.getPropertyPath().toString(), violation.getMessage()));
            logger.warn("param valid error: obj[{}], filed[{}], message[{}]", violation.getRootBeanClass(), violation.getPropertyPath(), violation.getMessage());
        }
        return Result.error(ResponseMessageCodeEnum.VALID_ERROR.getCode(), "", validErrorList);
    }


    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(BindException.class)
    @ResponseBody
    public ResponseMessage handlerOneBindException(BindException exception) {
        List<ValidError> validErrorList = new ArrayList<>();
        BindingResult result = exception.getBindingResult();
        String message = ResponseMessageCodeEnum.VALID_ERROR.getMessage();
        for (FieldError fieldError : result.getFieldErrors()) {
            message = fieldError.getDefaultMessage();
            break;
        }
        return Result.error(ResponseMessageCodeEnum.VALID_ERROR.getCode(), message);
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseBody
    public ResponseMessage paramFail(HttpMessageNotReadableException exception) {
        return Result.error(ResponseMessageCodeEnum.VALID_ERROR.getCode(), "参数错误");
    }


    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseMessage handlerOneBaseException(Exception exception) {

        exception.printStackTrace();
        logger.info("exception={},出现顶级异常={}", exception, exception.getMessage());
        if (exception instanceof NoHandlerFoundException) {
            return Result.error(ResponseMessageCodeEnum.ERROR.getCode(), "请求资源不存在");
        }
        if (exception instanceof org.springframework.web.HttpRequestMethodNotSupportedException) {
            return Result.error(ResponseMessageCodeEnum.ERROR.getCode(), "未知请求方式");
        }
        return Result.error(ResponseMessageCodeEnum.ERROR.getCode(), "服务出错");
    }

}
