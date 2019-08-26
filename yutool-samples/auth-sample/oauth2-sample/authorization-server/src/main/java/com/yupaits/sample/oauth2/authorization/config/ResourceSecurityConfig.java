package com.yupaits.sample.oauth2.authorization.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yupaits.sample.oauth2.authorization.constant.SecurityConstants;
import com.yupaits.yutool.commons.result.ResultCode;
import com.yupaits.yutool.commons.result.ResultWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

/**
 * @author yupaits
 * @date 2019/8/24
 */
@Configuration
@EnableResourceServer
public class ResourceSecurityConfig extends ResourceServerConfigurerAdapter {
    private final ObjectMapper objectMapper;
    private final JwtTokenStore tokenStore;

    @Autowired
    public ResourceSecurityConfig(ObjectMapper objectMapper, JwtTokenStore tokenStore) {
        this.objectMapper = objectMapper;
        this.tokenStore = tokenStore;
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.tokenStore(tokenStore);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .exceptionHandling()
                .accessDeniedHandler((request, response, accessDeniedEx) -> {
                    response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
                    objectMapper.writeValue(response.getWriter(), ResultWrapper.fail(ResultCode.FORBIDDEN));
                })
                .and()
                .authorizeRequests()
                .antMatchers(SecurityConstants.ignorePaths).permitAll()
                .anyRequest().authenticated()
                .and().httpBasic();
    }
}
