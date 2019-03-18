package com.rongpingkeji.data.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rongpingkeji.common.constant.ConfigType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * Created by Daniel on 2019/1/27.
 */

@Data
@ApiModel("配置信息")
@TableName("rp_config")

public class ConfigDO {


    @ApiModelProperty("配置key")
    private String config_key;

    @ApiModelProperty("所属的店铺")
    private String store_id;

    @ApiModelProperty("配置描述信息")
    private String config_desp;

    @ApiModelProperty("配置value值")
    private String config_value;

    @ApiModelProperty("config类型")
    private ConfigType configType;

    @ApiModelProperty("配置状态")
    private int state = 1;

}
