package com.yk.springboot.shiro;

import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.authc.AnonymousFilter;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by yk on 16/6/3.
 */
@Configuration
public class ShiroConfiguration {

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * BeanPostProcessor是spring容器的一个扩展点，注意这里配置成static。
     * shiro的这个扩展，用于自动执行init()方法，destory()方法，
     * init() 是 org.apache.shiro.util.Initializable 接口实现方法
     * destory() 是 org.apache.shiro.util.Destroyable 接口实现方法
     *
     * @return
     */
    @Bean(name = "lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    /**
     * realm
     *
     * @return
     */
    @Bean(name = "userRealm")
    @DependsOn(value = "lifecycleBeanPostProcessor")
    public UserRealm userRealm() {
        final UserRealm userRealm = new UserRealm();
        return userRealm;
    }


    @Bean
    public RedisSessionDao sessionDAO(RedisTemplate redisTemplate) {
        return new RedisSessionDao(redisTemplate);
    }

    @Bean
    public DefaultWebSessionManager sessionManager(RedisSessionDao redisSessionDao) {
        final DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setSessionDAO(redisSessionDao);
        sessionManager.setGlobalSessionTimeout(30 * 60 * 1000);
        return sessionManager;
    }

    @Bean
    public DefaultWebSecurityManager securityManager(DefaultWebSessionManager sessionManager) {
        final DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setSessionManager(sessionManager);
        securityManager.setRealm(userRealm());
        return securityManager;
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilter(DefaultWebSecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter();
        customAuthenticationFilter.setAppLoginUrl("/user/login");
        customAuthenticationFilter.setRequestBeforeLoginUrl("/user/requestBeforeLogin");
        customAuthenticationFilter.setUsernameParam("tel");
        customAuthenticationFilter.setPasswordParam("password");

        Map<String, Filter> filterMap = new HashMap<>();
        filterMap.put("appAuthc", customAuthenticationFilter);
        filterMap.put("anon", new AnonymousFilter());
        shiroFilterFactoryBean.setFilters(filterMap);

        Map<String, String> filterChainDefinitions = new HashMap<>();
        filterChainDefinitions.put("/user/login", "appAuthc");
        filterChainDefinitions.put("/user/logout", "logout");
        filterChainDefinitions.put("/user/test", "appAuthc");
        filterChainDefinitions.put("/user/requestBeforeLogin", "anon");
//        filterChainDefinitions.put("/user/loginSuccess", "authc");
//        filterChainDefinitions.put("/user/loginFailure", "authc");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitions);
        return shiroFilterFactoryBean;
    }
}
