package com.yk.springboot.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import com.yk.springboot.entity.User;

/**
 * Created by yk on 16/6/7.
 */
public interface UserRepository extends CrudRepository<User, String>
                  ,QueryByExampleExecutor<User>{
    User findByTel(String tel);

    User findByName(String assignee);

    Page<User> findByToken(String token, Pageable pageable);
}
