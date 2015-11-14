package com.hts.web.wechat.service;

import net.sf.json.JSONObject;

/**
 * 微信公众号业务逻辑访问接口
 * 
 * @author lynch
 *
 */
public interface WechatService {

	public String getToken();
	
	public JSONObject getSignature(String url);
}
