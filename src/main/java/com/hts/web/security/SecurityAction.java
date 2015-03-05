package com.hts.web.security;

import javax.servlet.http.HttpServletResponse;



import uk.ltd.getahead.dwr.util.Logger;

import com.hts.web.base.StrutsKey;
import com.hts.web.base.constant.OptResult;
import com.hts.web.common.BaseAction;
import com.hts.web.common.util.JSONUtil;

/**
 * <p>
 * 拒绝访问控制器
 * </p>
 * 
 * 创建时间：2013-8-24
 * @author ztj
 *
 */
public class SecurityAction extends BaseAction {
	
	/**
	 * 退出成功提示
	 */
	public static final String TIP_LOGOUT_SUCCESS = "退出成功";
	private Logger logger = Logger.getLogger(SecurityAction.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = -4709462554516978675L;

	public String accessDeny() {
		// 记录user-agent
        String userAgent = request.getHeader("User-Agent");
        logger.warn("无访问权限,user-agent：" + userAgent);
        
		JSONUtil.optFailed(403, "无访问权限，请重新登陆", jsonMap);
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        
		return StrutsKey.JSON;
	}
	
	/**
	 * 退出成功
	 * 
	 * @return
	 */
	public String logoutSuccess() {
		JSONUtil.optResult(OptResult.OPT_SUCCESS, TIP_LOGOUT_SUCCESS, jsonMap);
		return StrutsKey.JSON;
	}
}
