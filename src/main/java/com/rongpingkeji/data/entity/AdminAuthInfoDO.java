package com.rongpingkeji.data.entity;

import com.baomidou.mybatisplus.annotation.IdType;
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
@ApiModel("权限中心")
@TableName("rp_admin_auth_info")
public class AdminAuthInfoDO {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("权限名称")
    private String auth_name;

    @ApiModelProperty("授权地址")
    private String auth_link;

    @ApiModelProperty("0: 不进行验证")
    private int auth_state;

    @ApiModelProperty("创建者")
    private int createby;

    @ApiModelProperty("创建时间")
    private Date createtime;


}
