package com.rongpingkeji.app.Intercept;

import com.auth0.jwt.interfaces.Claim;
import com.rongpingkeji.common.constant.UserType;
import com.rongpingkeji.common.util.ExceptionUtil;
import com.rongpingkeji.common.util.JwtUtil;
import com.rongpingkeji.common.util.StringUtils;
import com.rongpingkeji.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.security.auth.login.LoginException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by Daniel on 2019/1/27.
 */
@Slf4j
public class AuthIntercept implements HandlerInterceptor {


    @Autowired
    private LoginService loginService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        String token = request.getHeader("Token");
//        String contextPath = request.getContextPath();
//        String path = request.getRequestURI().replace(contextPath, "");
//        if (StringUtils.isBlank(token) || !JwtUtil.verifyToken(UserType.TOKEN_SECRET, token)) {
//            throw new LoginException();
//        }
//        Map<String, Claim> data = JwtUtil.decodeJwt(UserType.TOKEN_SECRET, token);
//        String userType = data.get(UserType.USER_TYPE).asString();
//        switch (userType) {
//            case UserType.USER:  //用户登录
//                return loginService.checkLogin(token);
//            case UserType.MANAGER:  //管理员登录
//
//                break;
//        }
        return true;
    }
}
