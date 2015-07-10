package com.hts.web.security;

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

	/**
	 * 
	 */
	private static final long serialVersionUID = -4709462554516978675L;

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
