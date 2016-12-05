package com.yk.springboot.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.ValidationUtils;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by yk on 16/6/3.
 */
@Entity
@Table(name = "t_user")
@ApiModel(value = "用户信息")
public class User extends AbstractBaseEntity {

    private String tel;

    private String name;

    private String password;

    private String token;
    
   
    public User() {
	}

	public User(String tel, String name, String password, String token) {
		super();
		this.tel = tel;
		this.name = name;
		this.password = password;
		this.token = token;
	}

	public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }


}
