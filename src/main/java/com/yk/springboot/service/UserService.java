package com.yk.springboot.service;

import com.yk.springboot.entity.User;
import com.yk.springboot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Created by yk on 16/6/7.
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User saveUser(User user) {
        System.out.println("保存用户");
        return userRepository.save(user);
    }

    public Page<User> findUserByPage(int currentPage ,String token){
        Pageable pageable = new PageRequest(currentPage,5);
        return userRepository.findByToken(token, pageable);
    }
}
