package com.hts.web.security.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;

public interface LoginService {

	public Authentication persistentLoginStatus(Integer userId, HttpServletRequest request, HttpServletResponse response);
}
