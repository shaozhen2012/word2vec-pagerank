package com.rongpingkeji.data.dto.resultset;

import com.rongpingkeji.data.entity.AdminAuthInfoDO;
import com.rongpingkeji.data.entity.AdminDO;
import com.rongpingkeji.data.entity.AdminRoleDO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * Created by Daniel on 2019/1/30.
 */
public class ManagerResult {

    @ApiModel("管理员登录返回信息")
    @Data
    public static class Detail extends AdminDO {

        @ApiModelProperty("授权token")
        private String token;

        @ApiModelProperty("管理员具备的角色详情")
        private AdminRoleDetail roleDetail;

        @ApiModelProperty("角色名称")
        private String roleName;

    }

    @ApiModel("角色详情")
    @Data
    public static class AdminRoleDetail extends AdminRoleDO {

        @ApiModelProperty("权限名称")
        private String auth_name;

        @ApiModelProperty("授权链接")
        private String auth_link;

    }

}
