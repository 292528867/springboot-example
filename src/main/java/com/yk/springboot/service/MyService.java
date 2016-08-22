package com.yk.springboot.service;

import com.yk.springboot.entity.User;
import com.yk.springboot.repository.UserRepository;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Created by yukui on 2016/8/18.
 */
@Service
public class MyService {


    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public void startProcess(String assignee) {
        User user = userRepository.findByName(assignee);
        Map<String, Object> variables = new HashedMap();
        variables.put("user", user);
        runtimeService.startProcessInstanceByKey("oneTaskProcess",variables);
    }

    @Transactional
    public List<Task> getTasks(String assignee) {
        return taskService.createTaskQuery().taskAssignee(assignee).list();
    }
}
