package com.rongpingkeji.service.Impl;

import com.rongpingkeji.data.dao.AdminRoleDAO;
import com.rongpingkeji.data.dto.resultset.ManagerResult;
import com.rongpingkeji.data.entity.AdminDO;
import com.rongpingkeji.data.entity.AdminRoleDO;
import com.rongpingkeji.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Daniel on 2019/1/30.
 */
@Service
public class AdminServiceImpl implements AdminService {


    @Autowired
    private AdminRoleDAO role;


    @Override
    public ManagerResult.Detail findByName(String name) {
        return null;
    }

    @Override
    public AdminDO createManager(AdminDO data) {
        return null;
    }

    @Override
    public AdminDO updateManger(AdminDO data) {
        return null;
    }

    @Override
    public List<ManagerResult.AdminRoleDetail> roleDetail(int id) {
        return role.detail(id);
    }

    @Override
    public AdminRoleDO createRole(AdminRoleDO data) {
        return null;
    }

    @Override
    public AdminRoleDO updateRole(AdminRoleDO data) {
        return null;
    }
}
