package com.rongpingkeji.common.client.wechat.authlogin;

import com.rongpingkeji.common.client.wechat.wxresponse.LoginResponse;

/**
 * Created by Computer on 2018/10/28.
 */
public interface WechatLogin {


    /**
     * 解密微信用户信息
     *
     * @param encrypt
     * @param sessionId
     * @return
     */
    LoginResponse.WchatInfo decryptUserInfo(String encrypt, String iv, String sessionId);


}
