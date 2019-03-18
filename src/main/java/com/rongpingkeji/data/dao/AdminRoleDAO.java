package com.rongpingkeji.data.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rongpingkeji.data.dto.resultset.ManagerResult;
import com.rongpingkeji.data.entity.AdminDO;
import com.rongpingkeji.data.entity.AdminRoleDO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Daniel on 2019/1/30.
 */
@Service
public interface AdminRoleDAO extends BaseMapper<AdminRoleDO> {


    @Select("select r.*,au.auth_name,au.auth_link from rp_admin_role r left join rp_admin_auth a on a.role_id=r.id left join rp_admin_auth_info au on au.id=a.auth_id where r.id=#{roleId}")
    List<ManagerResult.AdminRoleDetail> detail(@Param("roleId") int roleId);
}
