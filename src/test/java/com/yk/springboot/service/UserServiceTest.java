package com.yk.springboot.service;

import com.yk.springboot.SpringBootExampleApplicationTests;
import com.yk.springboot.entity.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by yk on 16/6/7.
 */
public class UserServiceTest extends SpringBootExampleApplicationTests{

    @Autowired
    private UserService userService;

    @Test
    public void saveUser() throws Exception {
        for (int i=3;i<=10 ;i++) {

            User user = new User();
            user.setName("yk"+i);
            user.setPassword("33333");
            user.setToken("111");
            userService.saveUser(user);
        }
    }

    @Test
    public void findUserByPage() throws Exception {
        Page<User> userByPage = userService.findUserByPage(1, "111");
        List<User> userList = userByPage.getContent();
        for (User user : userList) {
            System.out.println(user.getName());
        }
    }
}