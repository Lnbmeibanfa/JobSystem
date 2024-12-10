package com.example.util;


import cn.hutool.core.date.DateUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import java.util.Date;

/**
 * @author 林万奇
 * @since 2024-12-8
 */
public class JWTUtil {

    public static String createJWT(String data, String sign) {
        // token的创建也包含了,token需要哪些要素
        // 需要Audience,也就是这个token的受众,或者说是用户的信息, 1-ADMIN
        // token有效时间,为一天
        // token的加密算法
        return JWT.create().withAudience(data)
                .withExpiresAt(DateUtil.offsetDay(new Date(), 1))
                .sign(Algorithm.HMAC256(sign));
    }
}
