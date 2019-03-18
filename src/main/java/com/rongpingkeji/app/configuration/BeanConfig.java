package com.rongpingkeji.app.configuration;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.rongpingkeji.app.beans.MybatisPlusHandler;
import com.rongpingkeji.common.client.opencv.OpenCVVideoService;
import com.rongpingkeji.common.client.wechat.authlogin.WxLoginRequest;
import feign.Feign;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.MultipartConfigElement;
import java.io.File;

/**
 * Created by Computer on 2018/10/22.
 */
@Configuration
public class BeanConfig {

    @Value("${application.file.upload.temp}")
    private String fileTemp;

    @Bean
    public WxLoginRequest initCarMachine() {
        return Feign.builder().encoder(new JacksonEncoder()).decoder(new JacksonDecoder()).target(WxLoginRequest.class, "https://api.weixin.qq.com/");
    }

    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

    @Bean
    MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        String location = System.getProperty("user.dir") + fileTemp;
        File tmpFile = new File(location);
        if (!tmpFile.exists()) {
            tmpFile.mkdirs();
        }
        factory.setLocation(location);
        return factory.createMultipartConfig();
    }

    @Bean
    MybatisPlusHandler mybatisPlusHandler() {
        return new MybatisPlusHandler();
    }

    @Bean
    OpenCVVideoService openCVVideoService() {
        return new OpenCVVideoService();
    }


}
