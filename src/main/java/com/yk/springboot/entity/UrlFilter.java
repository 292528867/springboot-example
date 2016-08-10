package com.yk.springboot.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by yukui on 2016/8/9.
 */
@Entity
@Table(name="t_url_filter")
public class UrlFilter extends AbstractBaseEntity{

    private String url;

    private String filters;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFilters() {
        return filters;
    }

    public void setFilters(String filters) {
        this.filters = filters;
    }
}
