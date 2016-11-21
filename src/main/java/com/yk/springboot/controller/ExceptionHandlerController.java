package com.yk.springboot.controller;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import springfox.documentation.annotations.ApiIgnore;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yk on 16/6/21.
 */
@ControllerAdvice
@ApiIgnore
public class ExceptionHandlerController {


    @ResponseStatus(HttpStatus.OK)
    //如果多个异常{NullPointerException.class,......}
    @ExceptionHandler({UnknownAccountException.class, IncorrectCredentialsException.class, AuthenticationException.class})
    @ResponseBody
    public Object unauthorized(Exception e) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        if (e instanceof UnknownAccountException) {
            resultMap.put("error","账号不存在");
        } else if (e instanceof IncorrectCredentialsException) {
            resultMap.put("error", "密码错误");
        } else if (e instanceof AuthenticationException) {
            resultMap.put("error", "用户未验证，请先登录app！");
        }
        return resultMap;
    }
}
