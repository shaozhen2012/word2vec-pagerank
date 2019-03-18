package com.rongpingkeji.app.Intercept;

import com.rongpingkeji.common.util.ExceptionUtil;
import com.rongpingkeji.common.util.StringUtils;
import com.rongpingkeji.service.VideoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Daniel on 2019/1/30.
 */
@Slf4j
public class StoreAuthIntercept implements HandlerInterceptor {

    @Autowired
    private VideoService store;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        String code = request.getHeader("AuthCode");
//        if (StringUtils.isEmpty(code)) {
//            ExceptionUtil.BaseError("请绑定合作授权码");
//        }
        return true;
    }
}
