package com.yk.springboot.controller;

import com.yk.springboot.entity.User;
import com.yk.springboot.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by yk on 16/6/20.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Object login(String tel, String password) {
//        TelPasswordToken token = new TelPasswordToken(tel, password);
        final Subject subject = SecurityUtils.getSubject();
     /*   try {
            subject.login(token);
        } catch (UnknownAccountException e) {
            throw new UnknownAccountException("账号不存在");
        } catch (IncorrectCredentialsException e) {
            throw new IncorrectCredentialsException("用户名或者密码错误");
        }*/
        Collections.emptyMap();
        User user = (User) subject.getPrincipal();
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("userId", user.getId());
        resultMap.put("sessionId", subject.getSession().getId());
        return resultMap;
    }

    @RequestMapping(value = "/loginOut", method = RequestMethod.POST)
    public Object loginOut() {
        return "loginOut";
    }

    /**
     * 未登录时请求需登录才能请求的接口时 跳转到该接口提示异常
     *
     * @return
     */
    @RequestMapping(value = "requestBeforeLogin", method = RequestMethod.GET)
    @ApiIgnore
    public Object requestBeforeLogin(String errMsg) {
        return errMsg;
    }

    /**
     * 保存用户
     *
     * @param user
     * @return
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ApiOperation(value = "新增用户", httpMethod = "POST", notes = "新增用户")
    public Object save(User user) {
        return userService.saveUser(user);
    }

    @RequestMapping(value = "getUserInfo", method = RequestMethod.GET)
    public Object getUserInfo() {
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        return user;
    }

}
