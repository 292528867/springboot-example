package com.yk.springboot.shiro;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListenerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by yukui on 2016/8/10.
 */
public class CustomSessionListener extends SessionListenerAdapter {

    private static final Logger logger = LoggerFactory.getLogger(CustomSessionListener.class);

    @Override
    public void onStart(Session session) {
       logger.info("session id={}，start！", session.getId());
    }

    @Override
    public void onStop(Session session) {
        logger.info("session id={}，stop！", session.getId());
    }

    @Override
    public void onExpiration(Session session) {
        logger.info("session id={}，expiration！", session.getId());
    }
}
