package com.yk.springboot.shiro;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.Serializable;

/**
 * Created by yukui on 2016/8/10.
 */
public class CustomWebSessionManager extends DefaultWebSessionManager {

    private final static Logger LOG = LoggerFactory.getLogger(CustomWebSessionManager.class);

    @Override
    protected Serializable getSessionId(ServletRequest request, ServletResponse response) {
        // 从http头中获取hctoken
        String hctoken = WebUtils.toHttp(request).getHeader("hctoken");
        if (StringUtils.isNotEmpty(hctoken)) {
            // 定义id来源于cookie，这里不需要
//			request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_SOURCE,
//                    ShiroHttpServletRequest.COOKIE_SESSION_ID_SOURCE);
            // 定义id来源于url，这里不需要
//			request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_SOURCE,
//                    ShiroHttpServletRequest.URL_SESSION_ID_SOURCE);

            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID, hctoken);
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_IS_VALID, Boolean.TRUE);

            if (LOG.isInfoEnabled())
                LOG.info("从http请求头中获取hctoken={}", hctoken);
        } else {
//			if (LOG.isInfoEnabled())
//				LOG.info("hctoken为空，fuck！");
        }

        return hctoken;
    }
}
