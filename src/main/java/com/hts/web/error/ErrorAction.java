package com.hts.web.error;

import com.hts.web.base.StrutsKey;
import com.hts.web.common.BaseAction;
import com.hts.web.common.util.JSONUtil;

/**
 * 错误控制器
 * 
 * 创建时间：2015-7-08
 * @author lynch
 * 
 *
 */
public class ErrorAction extends BaseAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4059250520592111251L;

	/**
	 * 403错误
	 * 
	 * @return
	 */
	public String error403() {
		response.setStatus(403);
		JSONUtil.optFailed(403, "无访问权限，请登录", jsonMap);
		return StrutsKey.JSON;
	}
	
	/**
	 * 404错误
	 * 
	 * @return
	 */
	public String error404() {
		response.setStatus(404);
		JSONUtil.optFailed(404, "notfound", jsonMap);
		return StrutsKey.JSON;
	}
	
	/**
	 * 500错误
	 * 
	 * @return
	 */
	public String error500() {
		response.setStatus(500);
		JSONUtil.optFailed(500, "internal error", jsonMap);
		return StrutsKey.JSON;
	}
	
	
}
