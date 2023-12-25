package com.usst.usstcafeteriahub.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.Map;

public class JwtUtils {
    private static final String signKey = "usstcafeteriahub";
    private static final long expire = 43200000L; // 12 hours

    /**
     * 生成JWT令牌
     * @param claims JWT的负载payload 中存储的内容
     * @return 返回生成的令牌
     */
    public static String generateToken(Map<String, Object> claims) {
        String jwt = Jwts.builder()
                .addClaims(claims) // 添加自定义数据
                .signWith(SignatureAlgorithm.HS256, signKey) // 自设签名密钥算法
                .setExpiration(new Date(System.currentTimeMillis() + expire)) // 设置过期时间ms
                .compact(); // 生成并返回jwt令牌
        return jwt;
    }

    /**
     * 解析JWT令牌
     * @param token 令牌
     * @return 返回解析后 负载paylaod 中存储的自定义内容
     */
    public static Map<String, Object> parseToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(signKey)
                .parseClaimsJws(token)
                .getBody();
        return claims;
    }
}
