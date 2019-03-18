package com.rongpingkeji.common.util;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

/**
 * Created by Computer on 2018/10/24.
 */
public class ObjectUtil {

    public static <T> Optional<T> isNull(T obj) {
        Optional<T> data = Optional.ofNullable(obj);
        return data;
    }

    public static <T> boolean ifNull(T obj) {
        return !Optional.ofNullable(obj).isPresent();
    }


}
