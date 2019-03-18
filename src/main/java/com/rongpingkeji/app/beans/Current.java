package com.rongpingkeji.app.beans;

import com.auth0.jwt.interfaces.Claim;
import com.rongpingkeji.common.constant.UserType;
import com.rongpingkeji.common.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by Daniel on 2019/1/30.
 */

@Configuration
public class Current {

    @Autowired
    private HttpServletRequest request;


    /**
     * 店铺
     *
     * @return
     */
    public String getAuthCode() {
        return request.getHeader("AuthCode");
    }

    /**
     * 访问用户
     *
     * @return
     */
    public long uid() {
        String token = request.getHeader("Token");
        Map<String, Claim> param = JwtUtil.decodeJwt(UserType.TOKEN_SECRET, token);
        Claim data = param.get("uid");
        return Long.parseLong(data.asString());
    }

    /**
     * 游客
     *
     * @return
     */
    public long travelId() {
        String token = request.getHeader("Token");
        if (StringUtils.isNotBlank(token)) {
            Map<String, Claim> param = JwtUtil.decodeJwt(UserType.TOKEN_SECRET, token);
            Claim data = param.get("uid");
            return Long.parseLong(data.asString());
        }
        return 0;
    }


}
