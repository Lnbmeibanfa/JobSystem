package com.example.util;


import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.common.enums.admin.Constants;
import com.example.common.enums.admin.RoleEnum;
import com.example.entity.Account;
import com.example.service.AdminService;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.RequestContext;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Date;

/**
 * @author 林万奇
 * @since 2024-12-8
 */
@Slf4j
public class JWTUtil {
    @Resource
    private AdminService adminService;

    private static AdminService staticAdminService;

    @PostConstruct
    private void init() {
        staticAdminService = adminService;
    }
    /**
     * 根据 1-ADMIN 和 密码创建tokne
     */
    public static String createJWT(String data, String sign) {
        // token的创建也包含了,token需要哪些要素
        // 需要Audience,也就是这个token的受众,或者说是用户的信息, 1-ADMIN
        // token有效时间,为一天
        // token的加密算法
        return JWT.create().withAudience(data)
                .withExpiresAt(DateUtil.offsetDay(new Date(), 1))
                .sign(Algorithm.HMAC256(sign));
    }

    /**
     * 获取当前登录用户
     */
    public static Account getCurAccount() {
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            String token = request.getHeader(Constants.token);
            String audience = JWT.decode(token).getAudience().get(0);
            String[] userRole = token.split("-");
            String userId = userRole[0];
            String role = userRole[1];
            if (RoleEnum.ADMIN.name().equals(role)) {
                return staticAdminService.selectById(Integer.valueOf(userId));
            }
        } catch (Exception e) {
            log.error("获取当前登录用户出错", e);
        }
        return null;
    }

}
