package com.yupaits.sample.shirojwt.service.impl;

import com.google.common.collect.Maps;
import com.yupaits.sample.shirojwt.dto.LoginForm;
import com.yupaits.sample.shirojwt.service.AuthService;
import com.yupaits.sample.shirojwt.utils.EncryptUtils;
import com.yupaits.sample.user.model.User;
import com.yupaits.sample.user.service.UserService;
import com.yupaits.sample.user.vo.UserVo;
import com.yupaits.yutool.commons.exception.BusinessException;
import com.yupaits.yutool.commons.result.Result;
import com.yupaits.yutool.commons.result.ResultCode;
import com.yupaits.yutool.commons.result.ResultWrapper;
import com.yupaits.yutool.commons.service.OptService;
import com.yupaits.yutool.plugin.jwt.support.JwtHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author yupaits
 * @date 2019/8/22
 */
@Service
public class AuthServiceImpl implements AuthService {
    private final UserService userService;
    private final JwtHelper jwtHelper;
    private final OptService optService;

    @Autowired
    public AuthServiceImpl(UserService userService, JwtHelper jwtHelper, OptService optService) {
        this.userService = userService;
        this.jwtHelper = jwtHelper;
        this.optService = optService;
    }

    @Override
    public Result login(LoginForm loginForm) {
        if (loginForm == null || !loginForm.isValid()) {
            return ResultWrapper.fail(ResultCode.PARAMS_ERROR);
        }
        User user = userService.getByUsername(loginForm.getUsername());
        if (user == null || StringUtils.equals(EncryptUtils.encryptPassword(loginForm.getPassword(), user.getCredential()), user.getPassword())) {
            return ResultWrapper.fail(ResultCode.LOGIN_FAIL);
        }
        Map<String, Object> claims = Maps.newHashMap();
        claims.put("sub", loginForm.getUsername());
        return ResultWrapper.success(jwtHelper.generateToken(claims));
    }

    @Override
    public Result<UserVo> currentUser() throws BusinessException {
        String username = optService.getOperatorId();
        return userService.getVoByUsername(username);
    }
}
