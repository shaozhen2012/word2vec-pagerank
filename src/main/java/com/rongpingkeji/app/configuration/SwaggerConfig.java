package com.rongpingkeji.app.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Computer on 2018/2/24.
 */

@Configuration
@EnableSwagger2
public class SwaggerConfig {


    private  String host="123";

    @Bean
    public Docket createRestApi() {

        ParameterBuilder  globalParam=new ParameterBuilder();
        globalParam.name("Token").defaultValue("user 24 0fa0b7dc-9b90-4118-8fe7-deaee3586e3f").description("请求token").modelRef(new ModelRef("string")).parameterType("header").required(false).build();
        List<Parameter> aParameters = new ArrayList<Parameter>();
        aParameters.add(globalParam.build());
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .globalOperationParameters(aParameters)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.rongpingkeji.web"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("亿滋小程序用户端接口文档")
                .description("亿滋小程序用户端接口文档")
                .termsOfServiceUrl("http://localhost:8070/")
                .version("1.0.0")
                .build();
    }

}
