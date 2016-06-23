package com.yk.springboot.repository;

import com.yk.springboot.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by yk on 16/6/7.
 */
public interface UserRepository extends JpaRepository<User,Long> {
    User findByTel(String tel);
}
