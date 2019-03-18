package com.rongpingkeji.app.beans;


import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;

import java.util.Date;

/**
 * Created by Daniel on 2019/1/28.
 */
public class MybatisPlusHandler implements MetaObjectHandler {


    @Override
    public void insertFill(MetaObject metaObject) {
        Object field = getFieldValByName("createtime", metaObject);
        if (field == null) {
            setFieldValByName("createtime", new Date(), metaObject);
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {

        Object field = getFieldValByName("updatetime", metaObject);
        if (field == null) {
            setFieldValByName("updatetime", new Date(), metaObject);
        }
    }
}
