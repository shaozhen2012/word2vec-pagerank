package com.rongpingkeji.data.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.rongpingkeji.common.constant.ConfigType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * Created by Daniel on 2019/1/27.
 */

@Data
@ApiModel("店铺信息")
@TableName("rp_video_center")

public class VideoCenterDO {

    @TableId(type = IdType.INPUT)
    @ApiModelProperty("视频编号")
    private String video_id;

    @ApiModelProperty("视频名称")
    private String video_name;

    @ApiModelProperty("视频帧数量")
    private int video_frame_num;

    @ApiModelProperty("视频帧压缩文件目录")
    private String video_file_zip;

    @ApiModelProperty("视频地址")
    private String video_link;

    @TableField(fill = FieldFill.INSERT)
    private Date createtime;

}
