package com.hts.web.userinfo;



import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.StrutsKey;
import com.hts.web.common.BaseAction;
import com.hts.web.common.util.JSONUtil;
import com.hts.web.userinfo.service.UserRetrievePWDService;



/**
 * 找回密码控制器
 * @author zxx
 *
 */

public class UserRetrievePWDAction extends BaseAction{
	private static final long serialVersionUID = 1859745867249584757L;
	/**
	 * 手机页面标志
	 */
	public static final String PHONE_PAGE_TAG = "phone";

	/**
	 * PC页面标志
	 */
	public static final String WEB_PAGE_TAG = "web";
	private String loginCode;
	private String pwd;
	private String sid;
	
	@Autowired
	private UserRetrievePWDService userRetrievePWDService;
	/**
	 * 智能手机User-Agent标识
	 */
	private static final String[] PHONE_USER_AGENTS = new String[] { "Android",
			"iPhone", "iPad" };
	
	public void setUserRetrievePWDService(UserRetrievePWDService userRetrievePWDService){
		this.userRetrievePWDService = userRetrievePWDService;
	}
	public UserRetrievePWDService getUserRetrievePWDService(){
		return this.userRetrievePWDService;
	}
	
	public void setLoginCode(String loginCode){
		this.loginCode = loginCode;
	}
	public String getLoginCode(){
		return this.loginCode;
	}
	
	public void setPwd(String pwd){
		this.pwd = pwd;
	}
	public String getPwd(){
		return this.pwd;
	}
	public void setSid(String sid){
		this.sid = sid;
	}
	public String getSid(){
		return this.sid;
	}
	
	/**
	 * 请求重置密码
	 * @return
	 */
	public String requestRPWD(){
		try{
			userRetrievePWDService.requestRPWD(loginCode);
			JSONUtil.optSuccess(jsonMap);
		}catch(Exception e){
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 请求修改页面
	 */
	public String resetPWD(){
		String userAgent = request.getHeader("User-Agent");
		String page = switchForwardByUserAgent(userAgent);
		if(pwd==null || pwd.equals("")){//若密码为空，则返回设置页面
			return page+"page";
		}else{
			try{				
				boolean r = userRetrievePWDService.resetPWD(loginCode,sid, pwd);
				if(r){
					return page+SUCCESS;
				}else{
					return page+ERROR;
				}				
			}catch(Exception e){
				e.printStackTrace();
				return page+ERROR;
			}
		}
	}
	public String success(){
		String userAgent = request.getHeader("User-Agent");
		String page = switchForwardByUserAgent(userAgent);
		return page + SUCCESS;
	}
	public String error(){
		String userAgent = request.getHeader("User-Agent");
		String page = switchForwardByUserAgent(userAgent);
		return page + ERROR;
	}
	public String resetPWDWEB(){
		return "phonepage";
	}
	
	/**
	 * 根据user-agent选择跳转页面
	 * 
	 * @param userAgent
	 * @return
	 */
	private static String switchForwardByUserAgent(String userAgent) {
		for (String key : PHONE_USER_AGENTS) {
			if (userAgent.equals("") || userAgent == null
					|| userAgent.contains(key)) {
				return PHONE_PAGE_TAG;
			}
		}
		return WEB_PAGE_TAG;
	}
}
