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
@ApiModel("角色和权限映射关系")
@TableName("rp_admin_auth")
public class AdminAuthDO {
    @TableId
    private int id;

    @ApiModelProperty("角色编号")
    private int role_id;

    @ApiModelProperty("授权编号")
    private int auth_id;

    @ApiModelProperty("创建者")
    private int createby;

    @ApiModelProperty("创建时间")
    private Date createtime;


}
