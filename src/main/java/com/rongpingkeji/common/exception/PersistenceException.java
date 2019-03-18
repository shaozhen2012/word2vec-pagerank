package com.rongpingkeji.common.exception;


import com.rongpingkeji.common.util.http.ResponseMessageCodeEnum;

/**
 * @version 2017-05-27.
 */
public class PersistenceException extends RpBaseException {


    private String errorCode;

    public PersistenceException() {
    }

    public PersistenceException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public PersistenceException(String message) {
        this(ResponseMessageCodeEnum.VALID_ERROR.getCode(), message);
    }


}
