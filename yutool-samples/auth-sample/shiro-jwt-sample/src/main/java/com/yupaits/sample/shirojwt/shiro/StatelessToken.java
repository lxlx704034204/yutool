package com.yupaits.sample.shirojwt.shiro;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * 无状态Token
 * @author yupaits
 * @date 2019/8/21
 */
public class StatelessToken implements AuthenticationToken {
    private static final long serialVersionUID = 1L;

    private String username;
    private String token;

    public StatelessToken(String username, String token) {
        this.username = username;
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return this.username;
    }

    @Override
    public Object getCredentials() {
        return this.token;
    }
}
