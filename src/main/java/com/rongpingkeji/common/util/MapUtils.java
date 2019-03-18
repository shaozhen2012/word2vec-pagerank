package com.rongpingkeji.common.util;

import lombok.Data;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.*;


/**
 * Created by shaozhen on 2018/4/5.
 * 校验用户的Map参数
 */
@Data
@Component
public class MapUtils {


    private boolean state;
    private String message;

    /**
     * @param param
     * @param list  {"key":"参数key值"  "message":"验证未通过返回信息内容 " "valid":"正则/空/List"  type:"notempty/enum',"maxLength":"最大长度"}
     */

    public void validMapParams(Map param, List<Map> list) {

        this.setState(true);
        this.setMessage("验证通过");

        for (Map mapitem : list) {
            if (StringUtils.isEmpty((CharSequence) mapitem.get("type"))) {
                mapitem.put("type", "notempty");
            }
            switch (mapitem.get("type").toString()) {
                case "notempty":
                    if (StringUtils.isEmpty(param.get(mapitem.get("key")).toString())) {

                        this.setState(false);
                        this.setMessage(mapitem.get("message").toString());

                    }
                    if (StringUtils.isNotEmpty((CharSequence) mapitem.get("maxLength"))) {
                        if (param.get(mapitem.get("key")).toString().length() > Integer.parseInt(mapitem.get("maxLength").toString())) {
                            this.setState(false);
                            this.setMessage("最多字数：".concat(String.valueOf(mapitem.get("maxLength"))));
                        }
                    }
                    break;
                case "enum":
                    if (!(mapitem.get("valid") instanceof List)) {
                        this.setState(false);
                        this.setMessage("验证参数传入失败");
                    }
                    ArrayList<Integer> paramList = (ArrayList<Integer>) mapitem.get("valid");
                    if (!paramList.contains(Integer.parseInt(String.valueOf(param.get(mapitem.get("key")))))) {
                        this.setState(false);
                        this.setMessage(mapitem.get("message").toString());
                    }
                    break;
                default:

                    if (StringUtils.isEmpty(param.get(mapitem.get("key")).toString())) {
                        this.setState(false);
                        this.setMessage(mapitem.get("message").toString());
                    }
                    break;


            }


        }


    }

    /**
     * merge map To single map
     *
     * @param map
     * @return
     */
    public static Map mergeMap(Map map) {
        Map result = new HashMap();
        Set<String> set = map.keySet();
        try {
            for (String key : set) {

                if (JsonUtil.isValidObject(JsonUtil.stringify(map.get(key)))) {
                    result.putAll(JsonUtil.toMap(JsonUtil.stringify(map.get(key))));
                } else {
                    result.put(key, map.get(key));
                }
            }
        } catch (IOException e) {
            //throw new RpBaseException("数据合并失败");
            e.printStackTrace();
        }
        return result;
    }


    /**
     * map转化成javaBean
     *
     * @param map
     * @param class1
     * @param <T>
     * @return
     */
    public static <T> T map2Bean(Map<String, Object> map, Class<T> class1) {
        T bean = null;
        try {
            bean = class1.newInstance();
            DateConverter dateConverter = new DateConverter(null);
            dateConverter.setPatterns(new String[]{"yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss"});
            ConvertUtils.register(dateConverter, Date.class);
            BeanUtils.populate(bean, map);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return bean;
    }


    public static <T> T map2BeanList(Map<String, Object> map, Class<T> class1) {

        Map<String, Object> cMap = new HashMap<>();
        for (String key : map.keySet()) {
            try {
                List result = JsonUtil.parse(map.get(key).toString(), List.class);
                cMap.put(key, result);
            } catch (Exception e) {
                cMap.put(key, map.get(key));
            }
        }
        T bean = null;
        try {
            bean = class1.newInstance();
            DateConverter dateConverter = new DateConverter(null);
            dateConverter.setPatterns(new String[]{"yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss"});
            ConvertUtils.register(dateConverter, Date.class);
            BeanUtils.populate(bean, cMap);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return bean;
    }


    /**
     * javabean转化成map
     *
     * @param bean
     * @return
     */
    public static Map bean2Map(Object bean) {

        Map map = new HashMap();
        try {
            map = BeanUtils.describe(bean);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        map.remove("class");
        return map;
    }


}
