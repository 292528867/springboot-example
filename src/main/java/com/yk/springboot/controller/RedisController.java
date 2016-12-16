package com.yk.springboot.controller;

import com.yk.springboot.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by yukui on 2016/12/16.
 */
@RestController
@RequestMapping("redis")
public class RedisController {

    @Autowired
    private RedisService redisService;

    /**
     * @return
     */
    @RequestMapping(value = "testRedisCluster")
    public String testRedisCluster() {
        redisService.setValue("key1", "value1");
        return redisService.getValue("key1");
    }

}
