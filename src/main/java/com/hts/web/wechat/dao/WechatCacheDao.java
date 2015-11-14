package com.hts.web.wechat.dao;

import com.hts.web.common.dao.BaseCacheDao;

public interface WechatCacheDao extends BaseCacheDao {

	public String getAppid();
	
	public String getToken();
	
	public String getTicket();
	
}
