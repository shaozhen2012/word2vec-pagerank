package com.rongpingkeji.common.client.wechat.authlogin.Impl;

import com.rongpingkeji.common.client.wechat.authlogin.WechatLogin;
import com.rongpingkeji.common.client.wechat.wxresponse.LoginResponse;
import com.rongpingkeji.common.util.AES;

import com.rongpingkeji.common.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;

/**
 * Created by Daniel on 2018/11/12.
 */
@Component
@Slf4j
public class WechatLoginImpl implements WechatLogin {


    @Override
    public LoginResponse.WchatInfo decryptUserInfo(String encrypt, String iv, String sessionId) {
        String value = AES.decrypt(Base64.decodeBase64(encrypt), Base64.decodeBase64(sessionId), Base64.decodeBase64(iv));
        LoginResponse.WchatInfo result = JsonUtil.parse(value, LoginResponse.WchatInfo.class);
        return result;
    }
}
