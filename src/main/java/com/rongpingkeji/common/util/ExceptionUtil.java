package com.rongpingkeji.common.util;

import com.rongpingkeji.common.exception.LoginInvalidException;
import com.rongpingkeji.common.exception.RpBaseException;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Created by Computer on 2018/10/19.
 */
public class ExceptionUtil {

    public static void LoginError() {
        throw new LoginInvalidException();
    }

    public static void BaseError(String msg) {
        throw new RpBaseException(msg);
    }

    public static RpBaseException buildError(String msg) {
        return new RpBaseException(msg);
    }

    public static RpBaseException noLogin() {
        return new LoginInvalidException();
    }

    /**
     * 将CheckedException转换为UncheckedException.
     */
    public static RuntimeException unchecked(Exception e) {
        if (e instanceof RuntimeException) {
            return (RuntimeException) e;
        } else {
            return new RuntimeException(e);
        }
    }

    /**
     * 将ErrorStack转化为String.
     */
    public static String getStackTraceAsString(Exception e) {
        StringWriter stringWriter = new StringWriter();
        e.printStackTrace(new PrintWriter(stringWriter));
        return stringWriter.toString();
    }

    /**
     * 判断异常是否由某些底层的异常引起.
     */
    public static boolean isCausedBy(Exception ex, Class<? extends Exception>... causeExceptionClasses) {
        Throwable cause = ex.getCause();
        while (cause != null) {
            for (Class<? extends Exception> causeClass : causeExceptionClasses) {
                if (causeClass.isInstance(cause)) {
                    return true;
                }
            }
            cause = cause.getCause();
        }
        return false;
    }


}
