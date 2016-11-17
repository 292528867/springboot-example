package com.yk.springboot.service;

import com.yk.springboot.SpringBootExampleApplicationTests;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

/**
 * Created by yukui on 2016/11/17.
 */
public class RedisServiceTest extends SpringBootExampleApplicationTests{

    @Autowired
    private RedisService redisService;

    @Test
    public void setValue() throws Exception {
         redisService.setValue("key1","test1");
    }

    @Test
    public void getValue() throws Exception {
        System.out.println(redisService.getValue("key1"));
        System.out.println(redisService.getValue("foo"));
    }

}