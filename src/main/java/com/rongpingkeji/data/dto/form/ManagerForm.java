package com.rongpingkeji.data.dto.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * Created by Daniel on 2019/1/30.
 */
public class ManagerForm {

    @ApiModel("管理员登录")
    @Data
    public static class LoginForm {

        @ApiModelProperty("用户名")
        @NotBlank(message = "请输入用户名")
        private String name;

        @ApiModelProperty("密码")
        @NotBlank(message = "请输入密码")
        private String pwd;
    }

}
