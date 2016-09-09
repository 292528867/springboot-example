package com.yk.springboot.shiro;

import com.yk.springboot.entity.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.SimpleSession;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.subject.support.WebDelegatingSubject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;

import javax.servlet.ServletRequest;
import java.io.Serializable;
import java.util.Collection;
import java.util.UUID;

/**
 * Created by yk on 16/6/6.
 */
public class RedisSessionDao extends AbstractSessionDAO {

    private final static Logger LOGGER = LoggerFactory.getLogger(RedisSessionDao.class);

    private RedisTemplate redisTemplate;
    private String sessionKeyPrefix = "shiro_redis_session:";

    public RedisSessionDao(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    protected Serializable doCreate(Session session) {
        //默认的生成sessionId 也可以用自定义的生成规则
        final Serializable sessionId = this.generateSessionId(session);
        LOGGER.debug("Creating a new session identified by[{}]", sessionId);
        this.assignSessionId(session, sessionId);
        //保存到redis
        this.update(session);
        return sessionId;
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
        Object values = redisTemplate.execute(new RedisCallback() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                byte[] key = getKeyWithSession(sessionId);
                if (connection.exists(key)) {
                    byte[] values = connection.get(key);
                    //反序列化
                    return redisTemplate.getDefaultSerializer().deserialize(values);
                }
                return null;
            }
        });
        if (values != null) {
            SimpleSession simpleSession = (SimpleSession) values;
            simpleSession.setExpired(false);
            return simpleSession;
        }
        return null;
    }

    @Override
    public void update(Session session) throws UnknownSessionException {
        if (session == null || session.getId() == null) {
            LOGGER.error("session or sessionId is null");
        }
        //保存到redis
        redisTemplate.execute(new RedisCallback() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                session.setTimeout(30 * 60 * 1000); //30分钟有效期
                //序列化
                connection.setEx(getKeyWithSession(session.getId()), 30 * 60, redisTemplate.getDefaultSerializer().serialize(session));
                return null;
            }
        });
    }

    @Override
    public void delete(Session session) {
        if (session == null || session.getId() == null) {
            LOGGER.error("session or sessionId is null");
        }
        //从redis删除key
        redisTemplate.execute(new RedisCallback() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                connection.del(getKeyWithSession(session.getId()));
                return null;
            }
        });
    }

    @Override
    public Collection<Session> getActiveSessions() {
        return null;
    }

    @Override
    protected Serializable generateSessionId(Session session) {
        WebDelegatingSubject subject = (WebDelegatingSubject) SecurityUtils.getSubject();
        ServletRequest request = subject.getServletRequest();
        User user = (User) subject.getPrincipal();
        return request.getParameter("tel")+"_"+subject.getHost();
    }

    /**
     * 生成redis的key
     *
     * @param sessionId
     * @return
     */
    private byte[] getKeyWithSession(Serializable sessionId) {
        String prekey = sessionKeyPrefix + sessionId;
        return prekey.getBytes();
    }


    public RedisTemplate getRedisTemplate() {
        return redisTemplate;
    }

    public void setRedisTemplate(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public String getSessionKeyPrefix() {
        return sessionKeyPrefix;
    }

    public void setSessionKeyPrefix(String sessionKeyPrefix) {
        this.sessionKeyPrefix = sessionKeyPrefix;
    }
}
