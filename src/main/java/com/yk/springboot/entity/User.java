package com.yk.springboot.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by yk on 16/6/3.
 */
@Entity
@Table(name = "t_user")
public class User extends AbstractBaseEntity {

    private String tel;

    private String name;

    private String password;

    private String token;

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
