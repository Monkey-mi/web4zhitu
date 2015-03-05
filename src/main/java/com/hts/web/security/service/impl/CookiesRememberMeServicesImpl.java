package com.hts.web.security.service.impl;

import java.util.Arrays;
import java.util.Date;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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

	
	public static final String REMEMBER_ME_VER = "REMEMBER_ME_VER";
	
	@Autowired
	private UserLoginPersistentService userLoginPersistentService;
	@Autowired
	private PersistentTokenRepository tokenRepository;
	@Autowired
	private UserInfoDao userInfoDao;
	
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
		
//		String username = successfulAuthentication.getName();
//        PersistentRememberMeToken persistentToken = new PersistentRememberMeToken(username, userLoginPersistentService.generateSeriesData(),
//        		userLoginPersistentService.generateTokenData(), new Date());
//        try {
//            tokenRepository.createNewToken(persistentToken);
//            addCookie(persistentToken, request, response);
//        } catch (DataAccessException e) {
//            logger.error("Failed to save persistent token ", e);
//        }

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
		
		/*
		 * 为了避免多并发请求时误判为token被盗，暂时屏蔽被盗判断功能
		 */
		// We have a match for this user/series combination
//		if (!presentedToken.equals(token.getTokenValue())) {
			// Token doesn't match series value. Delete all logins for this user
			// and throw an exception to warn them.
//			tokenRepository.removeUserTokens(token.getUsername());
//			logger.debug("PersistentTokenBasedRememberMeServices.cookieStolen for user '" + token.getUsername() + "', series '" + token.getSeries() + "'");
//			throw new CookieTheftException(messages.getMessage(
//							"PersistentTokenBasedRememberMeServices.cookieStolen",
//							"Invalid remember-me token (Series/token) mismatch. Implies previous cookie theft attack."));
//			logger.warn(message)
//		}

		if (token.getDate().getTime() + getTokenValiditySeconds() * 1000L < System.currentTimeMillis()) {
			throw new RememberMeAuthenticationException("Remember-me login has expired");
		}

		// Token also matches, so login is valid. Update the token value,
		// keeping the *same* series number.
		if (logger.isDebugEnabled()) {
			logger.debug("Refreshing persistent login token for user '" + token.getUsername() + "', series '" + token.getSeries() + "'");
		}

		
		PersistentRememberMeToken newToken = new PersistentRememberMeToken(token.getUsername(), token.getSeries(),
				userLoginPersistentService.generateTokenData(), new Date());

		try {
			tokenRepository.updateToken(newToken.getSeries(), newToken.getTokenValue(), newToken.getDate());
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
		
		boolean isAddSessionId = false;
		String userAgent = request.getHeader("User-Agent").toLowerCase();
		if(userAgent.contains("android")) {
			Cookie[] cookies = request.getCookies();
	        if ((cookies != null) && (cookies.length != 0)) {
	        	for (Cookie cookie : cookies) {
	                if (REMEMBER_ME_VER.equals(cookie.getName())) {
	                	isAddSessionId = true;
	                	break;
	                }
	            }
	        }
		} else {
			isAddSessionId = true;
		}
		
		if(isAddSessionId) {
			HttpSession session = request.getSession();
			int maxAge = session.getMaxInactiveInterval();
			String contextPath = request.getContextPath();
	        String path = contextPath.length() > 0 ? contextPath : "/";
			Cookie cookie = new Cookie("JSESSIONID", request.getSession().getId());
			cookie.setMaxAge(maxAge);
			cookie.setPath(path);
			response.addCookie(cookie);
		}
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
