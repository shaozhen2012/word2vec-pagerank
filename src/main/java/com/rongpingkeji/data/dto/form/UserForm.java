package com.rongpingkeji.data.dto.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by Daniel on 2019/1/28.
 */
public class UserForm {

    @ApiModel("授权登录")
    @Data
    public static class LoginForm {

        @ApiModelProperty("昵称")
        private String nickname;
        @ApiModelProperty("头像")
        private String avatar;
        @ApiModelProperty("手机号")
        private String tel;
        @ApiModelProperty("微信unionid")
        private String unionid;
        @ApiModelProperty("微信openid")
        private String openid;
    }


}
