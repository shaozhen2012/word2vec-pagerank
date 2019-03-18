package com.rongpingkeji.service.Impl;

import com.rongpingkeji.app.beans.Current;
import com.rongpingkeji.common.constant.UserType;
import com.rongpingkeji.common.exception.LoginInvalidException;
import com.rongpingkeji.common.util.*;
import com.rongpingkeji.data.dto.form.ManagerForm;
import com.rongpingkeji.data.dto.form.UserForm;
import com.rongpingkeji.data.dto.resultset.ManagerResult;


import com.rongpingkeji.service.LoginService;
import com.rongpingkeji.service.VideoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Daniel on 2019/1/28.
 */
@Service
public class LoginServiceImpl implements LoginService {


    @Value("${spring.redis.tokenLessTime}")
    private long tokenLessTime;

    @Autowired
    private RedisUtil redisUtil;


    @Autowired
    private Current currentStore;

    @Autowired
    private VideoService store;



    @Override
    public boolean checkLogin(String token) {

        String value = redisUtil.hashget(UserType.TOKEN_CACHE_NAME, token);
        if (StringUtils.isBlank(value)) {
            throw new LoginInvalidException();
        }
        long time = Long.parseLong(value);
        long now = DateUtils.getTimestamp();
        if (time < now) {
            throw new LoginInvalidException();
        }
        if (time < now + 1800 * 1000) {
            redisUtil.hashset(UserType.TOKEN_CACHE_NAME, token, String.valueOf(DateUtils.getTimestamp() + tokenLessTime));  //延长过期时间
        }
        return true;
    }


    @Override
    public ManagerResult.Detail managerLogin(ManagerForm.LoginForm loginForm) {
        return null;
    }

    @Override
    public boolean checkAuth(String token, String link) {
        return false;
    }
}


