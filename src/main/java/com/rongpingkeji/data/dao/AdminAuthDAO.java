package com.rongpingkeji.data.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rongpingkeji.data.entity.AdminAuthDO;
import com.rongpingkeji.data.entity.AdminRoleDO;
import org.springframework.stereotype.Service;

/**
 * Created by Daniel on 2019/1/30.
 */
@Service
public interface AdminAuthDAO extends BaseMapper<AdminAuthDO> {
}
