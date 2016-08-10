package com.yk.springboot.shiro;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.web.subject.support.WebDelegatingSubject;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * Created by yukui on 2016/8/9.
 */
public class CustomSubject extends WebDelegatingSubject {

    private TelPasswordToken token;

    public TelPasswordToken getToken() {
        return token;
    }

    public void setToken(TelPasswordToken token) {
        this.token = token;
    }

    public CustomSubject(PrincipalCollection principals, boolean authenticated, String host, Session session, ServletRequest request, ServletResponse response, SecurityManager securityManager) {
        super(principals, authenticated, host, session, request, response, securityManager);
    }

    public CustomSubject(PrincipalCollection principals, boolean authenticated, String host, Session session, boolean sessionEnabled, ServletRequest request, ServletResponse response, SecurityManager securityManager) {
        super(principals, authenticated, host, session, sessionEnabled, request, response, securityManager);
    }
}
