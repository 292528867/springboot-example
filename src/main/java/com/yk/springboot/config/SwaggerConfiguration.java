package com.yk.springboot.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.async.DeferredResult;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * swagger2 配置
 * Created by yukui on 2016/11/21.
 */
@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    @Value("${swagger.show}")
    private boolean swaggerShow;

    @Bean
    public Docket demoApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .enable(this.swaggerShow)
                .groupName("demo")
                .genericModelSubstitutes(DeferredResult.class)
                .useDefaultResponseMessages(false)
                .forCodeGeneration(true)
                .pathMapping("/")
                .select()
             //   .apis(not(withClassAnnotation(SwaggerIgnore.class))) //SwaggerIngore的注解的controller将会被忽略
                .build()
                .apiInfo(demoApiInfo());
    }

    private ApiInfo demoApiInfo() {
        ApiInfo apiInfo = new ApiInfo("标题文档",//大标题
                "文档的详细说",//小标题
                "0.1",//版本
                "NO terms of service",
                "razorer@razorer.com",//作者
                "The Apache License, Version 2.0",//链接显示文字
                "www.razorer.com"//网站链接
        );
        return apiInfo;
    }
}
