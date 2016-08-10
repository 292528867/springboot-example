package com.yk.springboot.shiro;

import org.apache.shiro.mgt.DefaultSubjectFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.SubjectContext;
import org.apache.shiro.web.subject.WebSubjectContext;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * Created by yukui on 2016/8/9.
 */
public class CustomSubjectFactory extends DefaultSubjectFactory {

    @Override
    public Subject createSubject(SubjectContext context) {
        WebSubjectContext wsc = (WebSubjectContext) context;
        SecurityManager securityManager = wsc.getSecurityManager();
        Session session = wsc.getSession();
        ServletRequest servletRequest = wsc.resolveServletRequest();
        ServletResponse servletResponse = wsc.resolveServletResponse();
        String host = wsc.resolveHost();
        boolean authenticated = wsc.resolveAuthenticated();
        boolean sessionCreationEnabled = wsc.isSessionCreationEnabled();
        PrincipalCollection principalCollection = wsc.resolvePrincipals();
        return new CustomSubject(principalCollection,authenticated,host,session,sessionCreationEnabled,
                servletRequest,servletResponse,securityManager);
    }
}
