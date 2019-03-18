package com.rongpingkeji.common.client.wechat.authlogin;

import com.rongpingkeji.common.client.wechat.wxresponse.LoginResponse;
import feign.Headers;
import feign.Param;
import feign.RequestLine;

/**
 * Created by Computer on 2018/10/28.
 */
@Headers({"Content-Type: application/json"})
public interface WxLoginRequest {

    @RequestLine("GET https://api.weixin.qq.com/sns/jscode2session?appid={appid}&secret={secret}&js_code={jscode}&grant_type=authorization_code")
    LoginResponse.SessionKey code2Session(@Param("appid") String appid, @Param("secret") String secret, @Param("jscode") String jscode);

}
