package com.rongpingkeji.data.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * Created by Daniel on 2019/1/27.
 */

@Data
@ApiModel("管理员信息")
@TableName("rp_admin")
public class AdminDO {


    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("用户名")
    private String name;

    @ApiModelProperty("联系人")
    private String username;

    @ApiModelProperty("头像")
    private String avatar;

    @ApiModelProperty("关联角色")
    private int role_id;

    @ApiModelProperty("所属店铺")
    private String store_id;

    @ApiModelProperty("密码")
    private String pwd;

    @ApiModelProperty("1: 有效")
    private int active;

    @ApiModelProperty("创建时间")
    private Date createtime;


}
