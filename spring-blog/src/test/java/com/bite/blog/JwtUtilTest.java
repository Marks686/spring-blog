package com.bite.blog;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.Test;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtilTest {
    //过期时间: 30分钟
    private static final long expiration = 60 * 60 * 1000;
    private static final String secretString = "5CRMLhF7dQnOLCNjJw8dawYK2zTUxS4jDgUW2L99Tdo=";
    private static final Key key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretString));
    //1. 生成token
    //2. 验证token
    @Test
    public void genToken(){

        Map<String, Object> claim  = new HashMap<>();
        claim.put("id",5);
        claim.put("name","zhangsan");

        String token = Jwts.builder()
                            .setClaims(claim)
                            .setExpiration(new Date(System.currentTimeMillis()+expiration))
                            .signWith(key)
                            .compact();
        System.out.println(token);
    }
    @Test
    public void genKey(){
        //随机生成一个key
        SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        String key = Encoders.BASE64.encode(secretKey.getEncoded());
        System.out.println(key);

    }
    @Test
    public void parseToken(){
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJuYW1lIjoiemhhbmdzYW4iLCJpZCI6NSwiZXhwIjoxNzA5MDg0NzAwfQ.Vv_K-Emhkk1vNeATLI2z-NJsOgbocSpZPZapJr1hzqo";
        JwtParser build = Jwts.parserBuilder().setSigningKey(key).build();
        Claims body = build.parseClaimsJws(token).getBody();
        System.out.println(body);
    }
}
