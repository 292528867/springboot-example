package com.yk.springboot.controller;

import com.yk.springboot.entity.User;
import com.yk.springboot.shiro.TelPasswordToken;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yk on 16/6/20.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Object login(String tel, String password) {
        TelPasswordToken token = new TelPasswordToken(tel, password);
        final Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);
        } catch (UnknownAccountException e) {
            throw new UnknownAccountException("账号不存在");
        } catch (IncorrectCredentialsException e) {
            throw new IncorrectCredentialsException("用户名或者密码错误");
        }
        User user = (User) subject.getPrincipal();
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("userId", user.getId());
        resultMap.put("sessionId", subject.getSession().getId());
        return resultMap;
    }

    @RequestMapping(value = "/loginOut",method = RequestMethod.POST)
    public Object loginOut() {
        return "loginOut";
    }

    /**
     * 未登录时请求需要登录才能访问的url
     * @return
     */
    @RequestMapping(value = "requestBeforeLogin")
    public Object requestBeforeLogin() {
        return "用户未验证，请先登录app！";
    }

    @RequestMapping(value = "test")
    public Object test() {
        return "test";
    }

}
