package com.rongpingkeji.service;

import com.rongpingkeji.common.client.wechat.wxresponse.LoginResponse;
import com.rongpingkeji.data.dto.form.ManagerForm;
import com.rongpingkeji.data.dto.form.UserForm;
import com.rongpingkeji.data.dto.resultset.ManagerResult;


/**
 * Created by Daniel on 2019/1/28.
 */
public interface LoginService {




    boolean checkLogin(String token);



    //管理员登录
    ManagerResult.Detail managerLogin(ManagerForm.LoginForm loginForm);

    boolean checkAuth(String token, String requestUri);


}
