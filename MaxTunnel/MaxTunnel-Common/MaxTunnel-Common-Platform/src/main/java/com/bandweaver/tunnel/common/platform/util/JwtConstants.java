package com.bandweaver.tunnel.common.platform.util;

public interface JwtConstants {
	// AUTH_HEADER 是HTTPHeader请求的参数
	String AUTH_HEADER = "Authorization";
	// SECRET 是具体的加密算法
	String SECRET = "defaultSecret";
	// EXPIRATION 是计算JWT过期时间需要用到的，当前是一周 24*60*60
	Long EXPIRATION = 86400L;
}
