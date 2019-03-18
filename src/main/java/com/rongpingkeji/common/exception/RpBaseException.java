package com.rongpingkeji.common.exception;


import com.rongpingkeji.common.util.http.ResponseMessageCodeEnum;

public class RpBaseException extends RuntimeException {

    private String errorCode;

    public RpBaseException() {
    }

    public RpBaseException(String message) {
        this(ResponseMessageCodeEnum.VALID_ERROR.getCode(), message);
    }

    public RpBaseException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }


}
