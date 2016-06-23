package com.yk.springboot.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Created by yk on 16/6/7.
 */
@Configuration
@EnableJpaAuditing
@EnableJpaRepositories(basePackages = "com.yk.springboot")
public class JpaConfiguration {


}
