package com.rongpingkeji.app.Intercept;


import com.rongpingkeji.common.constant.SystemLang;
import com.rongpingkeji.common.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

/**
 * Created by Computer on 2018/4/1.
 */

public class ChangeLangIntercept implements HandlerInterceptor {

    private static Logger logger = LoggerFactory.getLogger(ChangeLangIntercept.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String lang = request.getHeader("Lang");
        if (StringUtils.isEmpty(lang)) {
            lang = SystemLang.CN.getCode();
        }
        lang = lang.toLowerCase();
        LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
        String[] values = SystemLang.getLangByKey(lang).split("_");
        localeResolver.setLocale(request, response, new Locale(values[0], values[1]));
        return true;
    }


}
