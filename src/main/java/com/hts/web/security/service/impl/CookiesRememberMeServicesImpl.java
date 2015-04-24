package com.hts.web.security.service.impl;

import java.util.Arrays;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.rememberme.AbstractRememberMeServices;
import org.springframework.security.web.authentication.rememberme.InvalidCookieException;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.authentication.rememberme.RememberMeAuthenticationException;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;

import com.hts.web.aliyun.service.OsUserInfoService;
import com.hts.web.common.util.TimeUtil;
import com.hts.web.security.service.LoginService;
import com.hts.web.security.service.UserLoginPersistentService;
import com.hts.web.userinfo.dao.UserInfoDao;

/**
 * <p>
 * 使用Cookies记录登陆状态业务逻辑访问对象
 * </p>
 * 
 * 创建时间：2013-8-26
 * @author ztj
 *
 */
public class CookiesRememberMeServicesImpl extends AbstractRememberMeServices
		implements LoginService {

	private static Logger log = Logger.getLogger(CookiesRememberMeServicesImpl.class);
	
	public static final String REMEMBER_ME_VER = "REMEMBER_ME_VER";
	
	@Autowired
	private UserLoginPersistentService userLoginPersistentService;
	
	@Autowired
	private PersistentTokenRepository tokenRepository;
	
	@Autowired
	private UserInfoDao userInfoDao;

	@Autowired
	private OsUserInfoService osUserInfoService;
	
	private SecurityContextRepository securityContextRepository = new HttpSessionSecurityContextRepository();

	public UserLoginPersistentService getUserLoginPersistentService() {
		return userLoginPersistentService;
	}

	/**
	 * @deprecated Use constructor injection
	 */
	@Deprecated
	public CookiesRememberMeServicesImpl() {
	}

	public CookiesRememberMeServicesImpl(String key,
			UserDetailsService userDetailsService) {
		super(key, userDetailsService);
	}

	@Override
	protected void onLoginSuccess(HttpServletRequest request,
			HttpServletResponse response,
			Authentication successfulAuthentication) {
	}
	
	
	@Override
	protected UserDetails processAutoLoginCookie(String[] cookieTokens,
			HttpServletRequest request, HttpServletResponse response) {

		if (cookieTokens.length != 2) {
			throw new InvalidCookieException("Cookie token did not contain " + 2 + " tokens, but contained '"
					+ Arrays.asList(cookieTokens) + "'");
		}
		
		final String presentedSeries = cookieTokens[0];
//		final String presentedToken = cookieTokens[1];
		
		PersistentRememberMeToken token = tokenRepository.getTokenForSeries(presentedSeries);
		
		if (token == null) {
			// No series match, so we can't authenticate using this cookie
			throw new RememberMeAuthenticationException(
					"No persistent token found for series id: " + presentedSeries);
		}
		
		PersistentRememberMeToken newToken = new PersistentRememberMeToken(token.getUsername(), token.getSeries(),
				userLoginPersistentService.generateTokenData(), new Date());

		try {
			tokenRepository.updateToken(newToken.getSeries(), newToken.getTokenValue(), newToken.getDate());
			try {
				osUserInfoService.updateLastLogin(Integer.parseInt(newToken.getUsername()),
						TimeUtil.getTimeLONG(newToken.getDate()));
			} catch(Exception e) {
				log.warn("update opensearch userinfo last login error", e);
			}
			addCookie(newToken, request, response);
		} catch (DataAccessException e) {
			logger.error("Failed to update token: ", e);
			throw new RememberMeAuthenticationException(
					"Autologin failed due to data access problem");
		}

		return getUserDetailsService().loadUserByUsername(token.getUsername());
	}

	private void addCookie(PersistentRememberMeToken token,
			HttpServletRequest request, HttpServletResponse response) {
		
		setCookie(new String[] { token.getSeries(), token.getTokenValue() },
				getTokenValiditySeconds(), request, response);
	}
	
	
	@Override
	public Authentication persistentLoginStatus(Integer userId,
			HttpServletRequest request, HttpServletResponse response) {
		String username = String.valueOf(userId);
		
		tokenRepository.removeUserTokens(username); // 移除之前的登陆状态
		PersistentRememberMeToken newToken = new PersistentRememberMeToken(
				username,userLoginPersistentService.generateSeriesData(),
				userLoginPersistentService.generateTokenData(), new Date());
		tokenRepository.createNewToken(newToken);
		try {
			osUserInfoService.updateLastLogin(userId, TimeUtil.getTimeLONG(newToken.getDate()));
		} catch(Exception e) {
			log.warn("update opensearch userinfo last login error", e);
		}
		addCookie(newToken, request, response);
		
		UserDetails userDetails = getUserDetailsService().loadUserByUsername(username);  
		Authentication authentication = createSuccessfulAuthentication(request, userDetails);
		SecurityContext context = SecurityContextHolder.getContext();
		context.setAuthentication(authentication);
		securityContextRepository.saveContext(context, request, response);
		
		return authentication;

	}

	@Override
	public void logout(HttpServletRequest request,
			HttpServletResponse response, Authentication authentication) {
		String userId = null;
		if(authentication != null) {
			userId = authentication.getName();
		} else {
			String rememberMeCookie = extractRememberMeCookie(request);
	        if (rememberMeCookie != null && rememberMeCookie.length() != 0) {
	        	String[] cookieTokens = decodeCookie(rememberMeCookie);
	        	final String presentedSeries = cookieTokens[0];
	    		PersistentRememberMeToken token = tokenRepository.getTokenForSeries(presentedSeries);
	    		userId = token != null ? token.getUsername() : null;
	        }
		}
		if (userId != null) {
			userInfoDao.updateLogoutStatus(Integer.valueOf(userId));
			tokenRepository.removeUserTokens(userId);
		}
		super.logout(request, response, authentication);
	}

}
