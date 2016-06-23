package com.yk.springboot.shiro;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * Created by yk on 16/6/12.
 */
public class TelPasswordToken extends UsernamePasswordToken {

    private String tel;
    private String password;
    private boolean rememberMe;
    private String host;

    public TelPasswordToken() {
    }

    public TelPasswordToken(String username, String password) {
        super(username, password);
    }

    public TelPasswordToken(String tel, String password, boolean rememberMe, String host) {
        super(tel, password, rememberMe, host);
    }

    @Override
    public Object getPrincipal() {
        return super.getPrincipal();
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) { //这个判断是个优化语句
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(this.getClass() == obj.getClass())) {
            return false;
        }
        TelPasswordToken token = (TelPasswordToken) obj;
        return new EqualsBuilder()
                .append(this.getUsername(), token.getUsername())
                .append(this.getPassword(), token.getPassword())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(this.getUsername())
                .append(this.getPassword())
                .build();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append(this.tel)
                .append(this.password)
                .append(this.host)
                .append(this.rememberMe)
                .build();
    }
}
