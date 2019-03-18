package com.rongpingkeji.common.constant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Computer on 2018/4/8.
 */
public enum SystemLang {
    CN("cn", "中文", "zh_CN"), EN("en", "English", "en_US");

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getResourceName() {
        return resourceName;
    }


    private String code;
    private String name;
    private String resourceName;

    SystemLang(String code, String name, String resourceName) {
        this.code = code;
        this.name = name;
        this.resourceName = resourceName;
    }


    public static List getSysLang() {

        List<Map> list = new ArrayList<>();
        SystemLang[] types = SystemLang.values();
        for (SystemLang item : types) {
            Map value = new HashMap();
            value.put("key", item.getCode());
            value.put("value", item.getName());
            list.add(value);
        }
        return list;
    }

    public static String getLangByKey(String code) {

        SystemLang[] types = SystemLang.values();
        for (SystemLang item : types) {
            if (item.getCode().equals(code)) {
                return item.getResourceName();
            }
        }
        return "zh_CN";
    }
}
