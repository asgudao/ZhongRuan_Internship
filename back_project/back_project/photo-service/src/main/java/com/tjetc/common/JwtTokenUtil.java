package com.tjetc.common;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Date;
import java.util.Map;

/**
 * jwt token 生成工具类
 */
@Slf4j
public class JwtTokenUtil {
    //jwt加密的密钥（字符串）
    private final static String SECRET_KEY_STRING = "abc123";

    //创建一个jwt密钥（SecretKey对象） 加密和解密都需要用
    private static final SecretKey key = Jwts.SIG.HS256.key()
            .random(new SecureRandom(SECRET_KEY_STRING.getBytes(StandardCharsets.UTF_8)))
            .build();

    /**
     * 生成jwt token
     *
     * @param claims
     * @param subject
     * @param expiration
     * @return
     */
    public static String generateToken(Map<String, Object> claims, String subject, int expiration) {
        // 生成签名密钥
        //当前时间
        final Date now = new Date();
        //token过期时间
        final Date expirationDate = calculateExpirationDate(now, expiration);
        return Jwts.builder()
                .claims(claims)   //数据
                .subject(subject) //签发者
                .issuedAt(now)    //签发时间
                .expiration(expirationDate) //过期时间
                .signWith(key)    //秘钥key对象
                .compact();
    }

    private static Date calculateExpirationDate(Date createdDate, int expiration) {
        return new Date(createdDate.getTime() + expiration);
    }

    /**
     * 解密Jwt内容
     *
     * @param token
     * @return Claims
     * @RuntimeException
     * ExpiredJwtException token过期
     * SignatureException token无效（不可信）
     * 其他情况  token异常
     */
    public static Claims parseJwt(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(key) //设置秘钥
                .build()
                .parseSignedClaims(token) //解析token
                .getPayload(); //获取解析后的信息
        return claims;
    }
}