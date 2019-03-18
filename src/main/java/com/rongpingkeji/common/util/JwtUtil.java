package com.rongpingkeji.common.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.Payload;

import java.util.List;
import java.util.Map;

/**
 * Created by Daniel on 2019/1/27.
 */
public class JwtUtil {


    private static String isUser = "rongpingkeji";

    /**
     * 生成token
     *
     * @param secret
     * @return
     */
    public static String generateToken(String secret) {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        String token = JWT.create().withIssuer(isUser).sign(algorithm);
        return token;
    }


    /**
     * 生成token绑定自定义参数
     *
     * @param secret
     * @param params
     * @return
     */
    public static String generateToken(String secret, Map<String, String> params) {
        Algorithm algorithm = Algorithm.HMAC256(secret);

        JWTCreator.Builder builder = JWT.create();
        for (String key : params.keySet()) {
            builder.withClaim(key, params.get(key));
        }
        String token = builder.withIssuer(isUser).sign(algorithm);
        return token;
    }


    /**
     * 验证token
     */
    public static boolean verifyToken(String secret, String token) {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        JWTVerifier verifier = JWT.require(algorithm).withIssuer(isUser).build();
        try {
            DecodedJWT decode = verifier.verify(token);
            return true;
        } catch (JWTVerificationException exception) {
            return false;
        }
    }


    /**
     * 验证token
     */
    public static Map<String, Claim> decodeJwt(String secret, String token) {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        JWTVerifier verifier = JWT.require(algorithm).withIssuer(isUser).build();
        try {
            DecodedJWT decode = verifier.verify(token);
            return decode.getClaims();
        } catch (JWTVerificationException exception) {
            throw new JWTVerificationException("token valid");
        }
    }


}
