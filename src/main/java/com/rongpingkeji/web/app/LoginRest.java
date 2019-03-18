package com.rongpingkeji.web.app;

import com.rongpingkeji.common.client.wechat.authlogin.WechatLogin;
import com.rongpingkeji.common.client.wechat.authlogin.WxLoginRequest;
import com.rongpingkeji.common.client.wechat.wxresponse.LoginResponse;
import com.rongpingkeji.common.util.http.ResponseMessage;
import com.rongpingkeji.common.util.http.Result;
import com.rongpingkeji.data.dto.form.UserForm;
import com.rongpingkeji.service.LoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * Created by Daniel on 2019/1/27.
 */
@RestController
@RequestMapping("/app/login")
@Api(value = "登录", description = "授权登录")
@Slf4j
public class LoginRest {

    @Autowired
    private LoginService login;

    @Autowired
    private WxLoginRequest wRequest;

    @Autowired
    private WechatLogin wechatLogin;


    private String appid = "wxbd8d731a3138b0ed";


    private String appkey = "076d0e73281eb73d3f825fa1def8777e";




    @ApiOperation("通过code换取openid和unionid")
    @GetMapping("/getOpenid")
    public ResponseMessage<LoginResponse.SessionKey> getOpenid(@NotEmpty(message = "请传入code") String code) {
        LoginResponse.SessionKey data = wRequest.code2Session(appid, appkey, code);
        log.info("请求appid={}&secret={}&code={}", appid, appkey, code);
        return Result.success("授权登录", data);
    }


    @ApiOperation("解密获取微信用户信息")
    @PostMapping("/decryptWchatInfo")
    public ResponseMessage<LoginResponse.WchatInfo> wchatInfo(@Valid @RequestBody LoginResponse.EncryptInfo encryptInfo) {
        LoginResponse.WchatInfo data = wechatLogin.decryptUserInfo(encryptInfo.getEncryptedData(), encryptInfo.getVi(), encryptInfo.getSessionKey());
        log.info("解密数据={}", data);
        return Result.success("授权登录", data);
    }


    @ApiOperation("解密获取微信用户信息")
    @PostMapping("/codeForWechtInfo")
    public ResponseMessage<LoginResponse.WchatInfo> codeForWechtInfo(@Valid @RequestBody LoginResponse.EncryptInfo encryptInfo) {

        LoginResponse.SessionKey sessionKey = wRequest.code2Session(appid, appkey, encryptInfo.getCode());
        LoginResponse.WchatInfo data = wechatLogin.decryptUserInfo(encryptInfo.getEncryptedData(), encryptInfo.getVi(), sessionKey.getSession_key());
        log.info("解密数据={}", data);
        return Result.success("授权登录", data);
    }


}
