package com.yk.springboot.shiro;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * Created by yk on 16/6/21.
 */
public class CustomAuthenticationFilter extends AuthenticatingFilter {

    private static final Logger LOG = LoggerFactory.getLogger(CustomAuthenticationFilter.class);

    public static final String DEFAULT_USERNAME_PARAM = "tel";
    public static final String DEFAULT_PASSWORD_PARAM = "password";

    private String usernameParam = DEFAULT_USERNAME_PARAM;
    private String passwordParam = DEFAULT_PASSWORD_PARAM;


    private String appLoginUrl;
    private String requestBeforeLoginUrl;


    @Override
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) throws Exception {
        String tel = WebUtils.getCleanParam(request, usernameParam);
        String password = WebUtils.getCleanParam(request, passwordParam);
        TelPasswordToken token = new TelPasswordToken(tel, password);
        return token;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        if (isLoginRequest(request, response)) {
            // 判定是否是post请求，只允许post请求的验证
            if (WebUtils.toHttp(request).getMethod().equalsIgnoreCase(POST_METHOD)) {
                if (LOG.isTraceEnabled())
                    LOG.trace("开始验证用户");
                return this.executeLogin(request, response);
            } else {
                if (LOG.isTraceEnabled())
                    LOG.trace("验证请求不是post请求！");
                errMsgDispatcher(request, response, "非法请求");
                return false;
            }
        } else {
            if (LOG.isTraceEnabled())
                LOG.trace("用户未验证，请先登录app！");
            saveRequest(request);
            //跳转到通知前端异常的接口
            errMsgDispatcher(request, response, "用户未验证，请先登录app！");
            return false;
        }
    }

    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
        AuthenticationToken token = createToken(request, response);
        if (token == null) {
            String msg = "createToken method implementation returned null. A valid non-null AuthenticationToken " +
                    "must be created in order to execute a login attempt.";
            throw new IllegalStateException(msg);
        }
        try {
            Subject subject = getSubject(request, response);
            subject.login(token);
            return onLoginSuccess(token, subject, request, response);
        } catch (UnknownAccountException e) {
            //跳转到通知前端异常的接口
            errMsgDispatcher(request, response, "用户不存在");
            return false;
        } catch (IncorrectCredentialsException e) {
            errMsgDispatcher(request, response, "密码错误");
            return false;
        } catch (AuthenticationException e) {
            errMsgDispatcher(request, response, "未知错误");
            return false;
        }
    }

    @Override
    protected boolean isLoginRequest(ServletRequest request,
                                     ServletResponse response) {
        // 使用apploginin url 匹配登录request
        return pathsMatch(this.getAppLoginUrl(), request);
    }

    /**
     * 跳转到登录异常信息接口
     *
     * @param errMsg
     */
    private void errMsgDispatcher(ServletRequest request, ServletResponse response, String errMsg) throws Exception {
        RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher(this.requestBeforeLoginUrl + "?errMsg=" + errMsg);
        dispatcher.forward(request, response);
    }

//    @Override
//    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response) throws Exception {
//        // 不用原来filter的请求重定向，使用请求转发，这样就不会再一次filter了
//        RequestDispatcher rd = request.getServletContext().getRequestDispatcher(this.appLoginSuccessUrl);
//        rd.forward(request, response);
//        return false;
//    }

    //登录失败可以不用重写 用spring的全局异常捕捉 返回前台
   /* @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
        RequestDispatcher rd = request.getServletContext().getRequestDispatcher(this.appLoginFailureUrl);
        try {
            rd.forward(request, response);
        } catch (Exception e1) {
            throw new RuntimeException(e1);
        }
        return false;
    }*/

    public String getUsernameParam() {
        return usernameParam;
    }

    public void setUsernameParam(String usernameParam) {
        this.usernameParam = usernameParam;
    }

    public String getPasswordParam() {
        return passwordParam;
    }

    public void setPasswordParam(String passwordParam) {
        this.passwordParam = passwordParam;
    }

    public void setAppLoginUrl(String appLoginUrl) {
        this.appLoginUrl = appLoginUrl;
    }

    /**
     * 登录url
     *
     * @return
     */
    public String getAppLoginUrl() {
        return appLoginUrl;
    }

    /**
     * 请求登录后才能访问的接口url
     *
     * @return
     */
    public String getRequestBeforeLoginUrl() {
        return requestBeforeLoginUrl;
    }

    public void setRequestBeforeLoginUrl(String requestBeforeLoginUrl) {
        this.requestBeforeLoginUrl = requestBeforeLoginUrl;
    }
}
