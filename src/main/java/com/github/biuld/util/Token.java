package com.github.biuld.util;


import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;

/**
 * Created by biuld on 2019/8/21.
 */
public class Token {

    private static final SecretKey JWT_SECRET = Keys.secretKeyFor(SignatureAlgorithm.HS256); //密匙(每次重启服务都会重新生成)
    private static final long JWT_TTL = 60 * 60 * 1000; //token有效时间1小时

    public static String create(Map<String, Object> content) {
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        Date expDate = new Date(nowMillis + JWT_TTL);
        return Jwts.builder()
                .addClaims(content)     // 内容
                .setIssuer("vintage")   // 签发者
                .setIssuedAt(now)       // 签发时间
				.setExpiration(expDate) // 过期时间
				.signWith(JWT_SECRET, SignatureAlgorithm.HS256) // 签名算法以及密匙
                .compact();
    }

    public static Result validate(String jwtStr) {
        Claims claims;
        Result result = new Result();

        try {
            claims = Jwts.parser()
					.setSigningKey(JWT_SECRET)
					.parseClaimsJws(jwtStr)
					.getBody();
            result = Result.success("ok", claims);
        } catch (ExpiredJwtException e) {
            result = Result.error(Result.ErrCode.JWT_ERRCODE_EXPIRE);
        } catch (JwtException e) {
            result = Result.error(Result.ErrCode.JWT_ERRCODE_FAIL);
        }

        return result;
    }

}
