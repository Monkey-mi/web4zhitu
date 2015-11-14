package com.hts.web.wechat.dao.impl;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Repository;

import com.hts.web.base.constant.CacheKeies;
import com.hts.web.common.dao.impl.BaseCacheDaoImpl;
import com.hts.web.wechat.dao.WechatCacheDao;

import net.sf.json.JSONObject;

@Repository("HTSWechatAccessTokenCacheDao")
public class WechatCacheDaoImpl extends BaseCacheDaoImpl<String>implements WechatCacheDao {

	private String appid = "wxc1f27cca18587a9d";
	private String secret = "5a535584bd88ae94bc097525289e4c9b";

	@Override
	public String getAppid() {
		return appid;
	}
	
	@Override
	public String getToken() {
		String token = null;
		token = getRedisTemplate().boundValueOps(CacheKeies.OP_WECHAT_TOKEN).get();
		
		if(token == null) {
			JSONObject jsObj = fetchToken();
			token = jsObj.getString("access_token");
			Integer expire = jsObj.getInt("expires_in");
			updateToken(token, expire/2);
		}
		return token;
	}
	
	@Override
	public String getTicket() {
		String ticket = null;
		ticket = getRedisTemplate().boundValueOps(CacheKeies.OP_WECHAT_TICKET).get();
		
		if(ticket == null) {
			String token = getToken();
			JSONObject jsObj = fetchTicket(token);
			ticket = jsObj.getString("ticket");
			Integer expire = jsObj.getInt("expires_in");
			updateTicket(ticket, expire/2);
		}
		return ticket;
	}
	
	/**
	 * 更新Token到缓存
	 * 
	 * @param token
	 * @param expire
	 */
	private void updateToken(String token, long expire) {
		getRedisTemplate().boundValueOps(CacheKeies.OP_WECHAT_TOKEN)
			.set(token, expire, TimeUnit.SECONDS);
	}
	
	/**
	 * 更新ticket到缓存
	 * 
	 * @param ticket
	 * @param expire
	 */
	private void updateTicket(String ticket, long expire) {
		getRedisTemplate().boundValueOps(CacheKeies.OP_WECHAT_TICKET)
			.set(ticket, expire, TimeUnit.SECONDS);
	}
	
	/**
	 * 通过HTTPClient获取数据
	 * 
	 * @param url
	 * @return
	 */
	private JSONObject fetchData(String url) {
		//get请求返回结果
        JSONObject jsonResult = null;
        try {
        	CloseableHttpClient client = HttpClients.createDefault();
            //发送get请求
            HttpGet request = new HttpGet(url);
            HttpResponse response = client.execute(request);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                String strResult = EntityUtils.toString(response.getEntity());
                jsonResult = JSONObject.fromObject(strResult);
            } else {
            }
        } catch (IOException e) {
        }
        return jsonResult;
	}
	
	/**
	 * 通过http请求获取Token
	 * 
	 * @return
	 */
	private JSONObject fetchToken() {
		String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="
				+appid+"&secret=" + secret;
		return fetchData(url);
	}
	
	/**
	 * 通过http请求获取Token
	 * 
	 * @param token
	 * @return
	 */
	private JSONObject fetchTicket(String token) {
		String url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="
				+token+"&type=jsapi";
		return fetchData(url);
	}


}
