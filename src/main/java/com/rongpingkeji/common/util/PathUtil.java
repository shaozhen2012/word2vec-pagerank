package com.rongpingkeji.common.util;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Computer on 2018/10/21.
 */
public class PathUtil {

    public static String getContext(HttpServletRequest request) {
        StringBuffer url = request.getRequestURL();
        String contextUrl = url.delete(url.length() - request.getRequestURI().length(), url.length()).append(request.getServletContext().getContextPath()).append("/").toString();
        return contextUrl;
    }


}
