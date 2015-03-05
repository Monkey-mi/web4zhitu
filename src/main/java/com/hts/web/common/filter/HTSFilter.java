package com.hts.web.common.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 全局过滤器
 * @author ztj
 *
 */
public class HTSFilter implements Filter {

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest)req;
		HttpServletResponse response = (HttpServletResponse)resp;
		String addr = request.getLocalAddr();
		String uri = request.getRequestURI();
		if(addr.contains("htshuo.com") && !uri.contains("notice")) {
			String context = request.getContextPath();
			String redirect = context + "/zhitu_notice";
			chain.doFilter(req, resp);
			response.sendRedirect(redirect);
		} else {
			chain.doFilter(req, resp);
		}
		

	}

	@Override
	public void init(FilterConfig config) throws ServletException {

	}

}
