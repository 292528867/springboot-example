package com.yk.springboot.controller;

import com.yk.springboot.entity.UrlFilter;
import com.yk.springboot.repository.UrlFilterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * Created by yukui on 2016/8/9.
 */
@RestController
@ApiIgnore
@RequestMapping(value = "urlFilter")
public class UrlFilterController {

    @Autowired
    private UrlFilterRepository urlFilterRepository;

    @RequestMapping(value = "save", method = RequestMethod.POST)
    public UrlFilter save(@RequestBody UrlFilter filter) {
        return urlFilterRepository.save(filter);
    }
}
