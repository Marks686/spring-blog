package com.bite.blog.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

import java.security.Key;
import java.util.Date;
import java.util.Map;

/**
 * Jwt工具类
 */
@Slf4j
public class JwtUtils {
    //过期时间: 1小时
    private static final long expiration = 60 * 60 * 1000;
    private static final String secretString = "5CRMLhF7dQnOLCNjJw8dawYK2zTUxS4jDgUW2L99Tdo=";
    private static final Key key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretString));

    public static String genToken(Map<String, Object> claim){
        String token = Jwts.builder()
                .setClaims(claim) //设置载荷信息
                .setExpiration(new Date(System.currentTimeMillis()+expiration)) //设置过期时间
                .signWith(key)  //设置签名
                .compact();
        return token;
    }

    public static Claims parseToken(String token){
        if (token==null){
            return null;
        }
        JwtParser build = Jwts.parserBuilder().setSigningKey(key).build();
        Claims claims = null;
        try {
            claims = build.parseClaimsJws(token).getBody();
        }catch (Exception e){
//            e.printStackTrace();
            log.error("解析token失败, token:"+token);
        }
        return claims;
    }

    public static Integer getUserIdFromToken(String token){
        Claims claims = parseToken(token);
        if (claims==null){
            return null;
        }
        return (Integer) claims.get("id");
    }

//    public static void main(String[] args) {
//        String token = "eyJhbGciOiJIUzI1NiJ9.eyJuYW1lIjoiemhhbmdzYW4iLCJpZCI6MSwiZXhwIjoxNzA1ODAyNzUyfQ.atKGO-q32Dy5PYfFZZgoogHwbkULivFR_7sXF1Dw-so";
//        Claims claims = parseToken(token);
//        System.out.println(claims);
//    }

}
