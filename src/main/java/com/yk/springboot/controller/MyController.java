package com.yk.springboot.controller;

import com.sun.org.apache.regexp.internal.REUtil;
import com.yk.springboot.service.MyService;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yukui on 2016/8/18.
 */
@RestController
@RequestMapping("activiti")
public class MyController {

    @Autowired
    private MyService myService;

    @RequestMapping(value = "start", method = RequestMethod.GET)
    public String startProcess() {
        myService.startProcess("yk1");
        return "start success";
    }

    @RequestMapping(value = "getTasks", method = RequestMethod.GET)
    public Object getTasks(@RequestParam String assigine) {
        List<Map<String, String>> result = new ArrayList<>();
        List<Task> tasks = myService.getTasks(assigine);
        for (Task task : tasks) {
            Map<String, String> map = new HashMap<>();
            map.put("id", task.getId());
            map.put("name", task.getName());
            result.add(map);
        }
        return result;
    }
}
