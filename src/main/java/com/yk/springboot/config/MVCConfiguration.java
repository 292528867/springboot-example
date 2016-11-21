package com.yk.springboot.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by yukui on 2016/11/21.
 */
@Configuration
public class MVCConfiguration extends WebMvcConfigurerAdapter {

    @Value("${swagger.show}")
    private boolean swaggerShow;

    /**
     * 添加的两个ResourceHandler主要是指定swagger ui的地址，不然的话无法访问swagger的页面
     * @param registry
     */
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        if (this.swaggerShow) {
            registry.addResourceHandler("swagger-ui.html")
                    .addResourceLocations("classpath:/META-INF/resources/");
            registry.addResourceHandler("/webjars/**")
                    .addResourceLocations("classpath:/META-INF/resources/webjars/");
        }
    }
}
