package com.rongpingkeji.service;

import com.rongpingkeji.data.dto.resultset.ManagerResult;
import com.rongpingkeji.data.entity.AdminDO;
import com.rongpingkeji.data.entity.AdminRoleDO;

import java.util.List;

/**
 * Created by Daniel on 2019/1/30.
 */
public interface AdminService {


    ManagerResult.Detail findByName(String name);

    AdminDO updateManger(AdminDO data);

    AdminDO createManager(AdminDO data);


    //管理员角色


    List<ManagerResult.AdminRoleDetail> roleDetail(int id);

    AdminRoleDO createRole(AdminRoleDO data);

    AdminRoleDO updateRole(AdminRoleDO data);


}
