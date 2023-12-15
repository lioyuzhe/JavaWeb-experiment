package com.usst.usstcafeteriahub.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.Map;

public class JwtUtils {
    private static final String signKey = "usstcafeteriahub";
    private static final long expire = 43200000L; // 12 hours

    /**
     * 生成JwtToken
     *
     */
    public static String generateToken(Map<String, Object> claims) {
        return Jwts.builder()
                .addClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + expire))
                .signWith(SignatureAlgorithm.HS512, signKey)
                .compact();
    }

    /**
     * 解析JwtToken
     *
     */
    public static Map<String, Object> parseToken(String token) {
        return Jwts.parser()
                .setSigningKey(signKey)
                .parseClaimsJws(token)
                .getBody();
    }
}
