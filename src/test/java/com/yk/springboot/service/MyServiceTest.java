package com.yk.springboot.service;

import com.yk.springboot.SpringBootExampleApplicationTests;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.task.Task;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by yukui on 2016/8/18.
 */
public class MyServiceTest extends SpringBootExampleApplicationTests{

    @Autowired
    private MyService myService;

    @Test
    public void startProcess() throws Exception {
         myService.startProcess("yk1");
    }

    @Test
    public void getTasks() throws Exception {
        List<Task> tasks = myService.getTasks("yk1");
        for (Task task : tasks) {
            System.out.println(task.getId()+"-------"+task.getName());
        }
    }

}