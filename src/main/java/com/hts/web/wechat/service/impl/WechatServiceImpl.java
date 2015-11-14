package com.hts.web.wechat.service.impl;

import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.script.DigestUtils;
import org.springframework.stereotype.Service;

import com.hts.web.wechat.dao.WechatCacheDao;
import com.hts.web.wechat.service.WechatService;

import net.sf.json.JSONObject;

@Service("HTSWechatService")
public class WechatServiceImpl implements WechatService {

	@Autowired
	private WechatCacheDao wechatCacheDao;
	
	@Override
	public String getToken() {
		return wechatCacheDao.getToken();
	}
	
	@Override
	public JSONObject getSignature(String url) {
		if(url == null || url == "") {
			throw new NullPointerException("url can't be null");
		}

		if(url.contains("#")) {
			url = url.substring(0, url.indexOf("#"));
		}
		
		String signature = null;
		long timestamp = new Date().getTime() / 1000;
		String noncestr = UUID.randomUUID().toString();
		String appid = wechatCacheDao.getAppid();
		String ticket = wechatCacheDao.getTicket();
		StringBuilder sb = new StringBuilder();
		sb.append("jsapi_ticket=")
			.append(ticket).append("&").append("noncestr=").append(noncestr)
			.append("&").append("timestamp=").append(timestamp).append("&").append("url=")
			.append(url);
		signature = DigestUtils.sha1DigestAsHex(sb.toString());
		
		JSONObject jsObj = new JSONObject();
		jsObj.put("appid", appid);
		jsObj.put("signature", signature);
		jsObj.put("timestamp", timestamp);
		jsObj.put("noncestr", noncestr);
		
		return jsObj;
	}
	
}
