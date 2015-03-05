package com.hts.web.weibo.service;

import com.hts.web.common.service.BaseService;

public interface SinaWeiboService extends BaseService{
	
	public String[] getFollowerIdsById(String uid,String accessToken,Integer limit) throws Exception;
	
//	public String[] getBilateralIdsById(String uid, String accessToken, Integer limit) throws Exception; 

}
