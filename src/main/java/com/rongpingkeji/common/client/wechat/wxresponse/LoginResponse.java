package com.rongpingkeji.common.client.wechat.wxresponse;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * Created by Computer on 2018/10/28.
 */

public class LoginResponse {

    @ApiModel("code2Session返回数据结果")
    @Data
    public static class SessionKey {
        private String openid;
        private String session_key;
        private String unionid;
        private String errcode;
        private String errMsg;
    }

    @ApiModel("解密返回数据信息")
    @Data
    public static class WchatInfo {
        private String openId;
        private String nickName;
        private String avatarUrl;
        private String unionId;
    }


    @ApiModel("请求数据(解密微信用户数据)")
    @Data
    public static  class EncryptInfo{

        private  String encryptedData;
        private  String sessionKey;
        private  String vi;
        private  String code;
    }


}
