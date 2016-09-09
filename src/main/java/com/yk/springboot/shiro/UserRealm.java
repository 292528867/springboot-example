package com.yk.springboot.shiro;

import com.yk.springboot.entity.User;
import com.yk.springboot.repository.UserRepository;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by yk on 16/6/3.
 */
@Component
public class UserRealm extends AuthorizingRealm {

    @Autowired
    private UserRepository userRepository;

    public UserRealm() {
        setCachingEnabled(true);
        setAuthenticationCachingEnabled(true);
        setAuthenticationCacheName("userAuthenticationCache");
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        TelPasswordToken token = (TelPasswordToken) authenticationToken;
        String tel = token.getUsername();
        String passord = String.valueOf(token.getPassword());
        User user = userRepository.findByTel(tel);
        if (StringUtils.isEmpty(tel)) {
            throw new UnknownAccountException();
        } else if (user == null) {
            throw new UnknownAccountException();
        } else if (!passord.equals(user.getPassword())) {
            throw new IncorrectCredentialsException();
        }
        return new SimpleAuthenticationInfo(user, user.getPassword(), getName());
    }


    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }
}
