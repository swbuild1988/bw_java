package com.bandweaver.tunnel.common.platform.util;

import java.util.Date;
import java.util.Map;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
* Created by 張燿峰
* <p>Jwt工具类</p>
* jwt的claim里一般包含以下几种数据:
* 1. iss -- token的发行者
* 2. sub -- 该JWT所面向的用户
* 3. aud -- 接收该JWT的一方
* 4. exp -- token的失效时间
* 5. nbf -- 在此时间段之前,不会被处理
* 6. iat -- jwt发布时间
* 7. jti -- jwt唯一标识,防止重复使用
*
* @author 孤
* @date 2019/1/2
* @Varsion 1.0
*/
public class JwtUtil {

	private static final String subjectPrefix = "javaJWT";
    /**
     * 从Token中获取用户名称
     *
     * @param token
     * @return 用户名称
     */
    public static String getUserNameFromToken(String token) {
        return getClaimsFromToken(token).get("username").toString();
    }

    /**
     * 从Token中获取JWT发布时间
     *
     * @param token
     * @return 发布时间
     */
    public static Date getIsseudAtDateFromToken(String token) {
        return getClaimsFromToken(token).getIssuedAt();
    }

    /**
     * 从Token中获取JWT过期时间
     *
     * @param token
     * @return 过期时间
     */
    public static Date getExPirationDateFromToken(String token) {
        return getClaimsFromToken(token).getExpiration();
    }

    /**
     * 从Token中获取JWT接收者
     *
     * @param token
     * @return 接收者
     */
    public static String getAyduebceFromToken(String token) {
        return getClaimsFromToken(token).getAudience();
    }

    /**
     * 从Token中获取私有的JWT claim
     *
     * @param token
     * @param key
     * @return claim
     */
    public static String getPrivateClaimsFromToken(String token, String key) {
        Object queueName = getClaimsFromToken(token).get(key);
        return queueName == null ? null : queueName.toString();
    }

    /**
     *  解析token
     *设置允许的时间偏移(秒)为Long的最大值
     *   这样就可以解析出过期的token,而不抛ExpiredJwtException(过期异常)
     * @param token
     */
    public static Claims getClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(JwtConstants.SECRET)
                .setAllowedClockSkewSeconds(Long.MAX_VALUE/1000) //设置允许的时间偏移(秒)
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * 检查Token是否正确
     *
     * @param token
     */
    public static boolean verifyToken(String token) {
    	Claims claims = getClaimsFromToken(token);
    	if(claims.getSubject().endsWith(subjectPrefix)) {
    		return true;
    	}
    	return false;
    }

    /**
     * 检查Token是否过期
     *
     * @param token
     * @return false未过期  true过期
     */
    public static boolean isTokenExpired(String token) {
        try {
            final Date expiration = getExPirationDateFromToken(token);
            return expiration.before(new Date());
        } catch (ExpiredJwtException expiredJwtException) {
            return true;
        }
    }

    /**
     * 生成Token
     *
     * @param claims
     * @return Token
     */
    public static String createJwt(Map<String, Object> claims) {
        //Map<String, Object> claims = new HashMap<>();
    	String subject = subjectPrefix;
        return doGeneratorToken(claims, subject);
    }

    private static String doGeneratorToken(Map<String, Object> claims, String subject) {
        final Date startDate = new Date();
        final Date endDate = new Date(startDate.getTime() + JwtConstants.EXPIRATION * 1000);
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(startDate)
                .setExpiration(endDate)
                .signWith(SignatureAlgorithm.HS512,JwtConstants.SECRET)
                .compact();
    }






}
