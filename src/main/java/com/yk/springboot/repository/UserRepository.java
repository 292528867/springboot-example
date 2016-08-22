package com.yk.springboot.repository;

import com.yk.springboot.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by yk on 16/6/7.
 */
public interface UserRepository extends JpaRepository<User, String> {
    User findByTel(String tel);

    User findByName(String assignee);
}
