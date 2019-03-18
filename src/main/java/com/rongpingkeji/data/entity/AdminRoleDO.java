package com.rongpingkeji.data.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * Created by Daniel on 2019/1/27.
 */

@Data
@ApiModel("管理员角色中心")
@TableName("rp_admin_role")
public class AdminRoleDO {
    @TableId
    private int id;

    @ApiModelProperty("角色名称")
    private String role_name;

    @ApiModelProperty("0: 角色无效")
    private int role_state;

    @ApiModelProperty("创建者")
    private int createby;

    @ApiModelProperty("创建时间")
    private Date createtime;


}
