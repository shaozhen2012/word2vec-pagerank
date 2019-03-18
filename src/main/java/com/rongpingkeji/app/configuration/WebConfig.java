package com.rongpingkeji.app.configuration;


import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.rongpingkeji.app.Intercept.AuthIntercept;
import com.rongpingkeji.app.Intercept.ChangeLangIntercept;
import com.rongpingkeji.app.Intercept.StoreAuthIntercept;
import com.rongpingkeji.common.util.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Configuration
@Slf4j
public class WebConfig extends WebMvcConfigurationSupport {
    /**
     * 设置跨域问题
     *
     * @return
     */

    @Override
    public void addCorsMappings(CorsRegistry registry) {

        registry.addMapping("/**")
                .allowedHeaders("*")
                .allowedMethods("GET", "HEAD", "POST", "PUT", "DELETE", "OPTIONS")
                .allowCredentials(true)
                .allowedOrigins("*");

    }

    private CorsConfiguration addcorsConfig() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("http://localhost:8080");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        return corsConfiguration;
    }


    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", addcorsConfig());
        return new CorsFilter(source);
    }


    @Bean
    public AuthIntercept authIntercept() {
        return new AuthIntercept();
    }

    @Bean
    public ChangeLangIntercept changeLangIntercept() {
        return new ChangeLangIntercept();
    }

    @Bean
    public StoreAuthIntercept storeAuthIntercept(){
        return new StoreAuthIntercept();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");

    }


    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration interceptorRegistration = registry.addInterceptor(authIntercept());
        interceptorRegistration.addPathPatterns("/app/user/**", "/admin/**").excludePathPatterns("/common/**","/app/video/**","/app/login/**", "/admin/login/**", "/resource/**", "/error", "/swagger-resources/**");
        //registry.addInterceptor(changeLangIntercept());  //语言切换拦截器
        InterceptorRegistration storeIntercept=registry.addInterceptor(storeAuthIntercept());
        storeIntercept.addPathPatterns("/app/**").excludePathPatterns("/common/**","/app/video/**");  //前端校验店铺授权码
        super.addInterceptors(registry);

    }

    /**
     * 自定义jackson数据转换格式
     *
     * @param converters
     */
    @Override
    protected void extendMessageConverters(List<HttpMessageConverter<?>> converters) {

        MappingJackson2HttpMessageConverter jackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.setDateFormat(DateUtils.getDateFormat(DateUtils.yyyy_MM_dd_HH_mm_ss_EN));
        jackson2HttpMessageConverter.setObjectMapper(objectMapper);
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(Double.class, ToStringSerializer.instance);
        simpleModule.addSerializer(Double.TYPE, ToStringSerializer.instance);
        simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
        simpleModule.addSerializer(Long.TYPE, ToStringSerializer.instance);
        objectMapper.registerModule(simpleModule);
        objectMapper.getSerializerProvider().setNullValueSerializer(new JsonSerializer<Object>() {  //null转为空
            @Override
            public void serialize(Object o, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
                jsonGenerator.writeString("");
            }
        });
        converters.add(0, jackson2HttpMessageConverter);
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new PageableHandlerMethodArgumentResolver());
    }


}
