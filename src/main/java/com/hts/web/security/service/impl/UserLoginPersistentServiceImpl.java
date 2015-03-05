package com.hts.web.security.service.impl;

import java.security.SecureRandom;

import org.springframework.security.crypto.codec.Base64;
import org.springframework.stereotype.Service;

import com.hts.web.security.service.UserLoginPersistentService;

/**
 * <p>
 * 用户持久化登录业务逻辑访问对象
 * </p>
 * 
 * 创建时间：2013-8-24
 * @author ztj
 *
 */

@Service
public class UserLoginPersistentServiceImpl implements
		UserLoginPersistentService {
	
	public static final int DEFAULT_SERIES_LENGTH = 16;
	public static final int DEFAULT_TOKEN_LENGTH = 16;
	
	private SecureRandom random = new SecureRandom();

	@Override
	public String generateSeriesData() {
		byte[] newSeries = new byte[DEFAULT_SERIES_LENGTH];
		random.nextBytes(newSeries);
		return new String(Base64.encode(newSeries));
	}

	@Override
	public String generateTokenData() {
		byte[] newToken = new byte[DEFAULT_SERIES_LENGTH];
		random.nextBytes(newToken);
		return new String(Base64.encode(newToken));
	}
	
	
}
