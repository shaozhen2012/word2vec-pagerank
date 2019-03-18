package com.rongpingkeji.common.util.http;

public enum ResponseMessageCodeEnum {

    SUCCESS("200", "请求成功"),
    ERROR("400", "请求失败"),
    SERVER_ERROR("500", "服务端出错"),
    VALID_ERROR("401", "校验失败"),
    RE_LOGIN("201", "登录失效"),
    FORBIDDEN("403","无权限访问");
    private String code;
    private String message;

    ResponseMessageCodeEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
