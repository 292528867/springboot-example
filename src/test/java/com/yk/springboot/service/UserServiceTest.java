package com.yk.springboot.service;

import com.yk.springboot.SpringBootExampleApplicationTests;
import com.yk.springboot.entity.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

/**
 * Created by yk on 16/6/7.
 */
public class UserServiceTest extends SpringBootExampleApplicationTests{

    @Autowired
    private UserService userService;

    @Test
    public void saveUser() throws Exception {
        User user = new User();
        user.setName("张三");
        user.setPassword("111");
        userService.saveUser(user);
    }
}