package com.rongpingkeji.service.Impl;

import com.rongpingkeji.common.util.*;
import com.rongpingkeji.data.dao.VideoDAO;
import com.rongpingkeji.data.entity.VideoCenterDO;
import com.rongpingkeji.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Created by Daniel on 2019/1/30.
 */
@Service
public class VideoServiceImpl implements VideoService {

    @Autowired
    private VideoDAO store;

    @Autowired
    private RedisUtil redisUtil;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public VideoCenterDO insert(VideoCenterDO storeInfoDO) {
        String code = "RP" + RandomUtils.randomString(5).toUpperCase() + DateUtils.dateToString(new Date(), DateUtils.yyyyMMddHHmmss);
        storeInfoDO.setVideo_id(code);
        store.insert(storeInfoDO);
        return storeInfoDO;
    }

    @Override
    public VideoCenterDO update(VideoCenterDO storeInfoDO) {
        store.updateById(storeInfoDO);
        return storeInfoDO;
    }


}
