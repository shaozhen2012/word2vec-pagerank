package com.rongpingkeji.common.client.wechat.wxpay.impl;

import com.rongpingkeji.common.client.wechat.wxpay.IWXPayDomain;
import com.rongpingkeji.common.client.wechat.wxpay.WXPayConfig;
import com.rongpingkeji.common.client.wechat.wxpay.WXPayConstants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;


/**
 * Created by Computer on 2018/10/24.
 */
@Service
public class WxPayConfigImpl extends WXPayConfig {

    @Value("${application.pay.wechat.def.appId}")
    private String appId;

    @Value("${application.pay.wechat.def.mchId}")
    private String mechId;

    @Value("${application.pay.wechat.def.apiKey}")
    private String appKey;

    @Override
    public String getAppID() {
        return appId;
    }

    @Override
    public String getMchID() {
        return mechId;
    }

    @Override
    public String getKey() {
        return appKey;
    }

    @Override
    public InputStream getCertStream() {

        Resource resource=new ClassPathResource("cert/apiclient_cert.p12");
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(resource.getFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return inputStream;
    }

    @Override
    public IWXPayDomain getWXPayDomain() {
        IWXPayDomain iwxPayDomain = new IWXPayDomain() {
            @Override
            public void report(String domain, long elapsedTimeMillis, Exception ex) {
            }

            @Override
            public DomainInfo getDomain(WXPayConfig config) {
                return new IWXPayDomain.DomainInfo(WXPayConstants.DOMAIN_API, true);
            }
        };
        return iwxPayDomain;

    }


}
