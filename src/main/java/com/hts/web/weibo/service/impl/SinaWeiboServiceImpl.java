package com.hts.web.weibo.service.impl;

import org.springframework.stereotype.Service;

import weibo4j.Friendships;
import weibo4j.model.WeiboException;

import com.hts.web.common.service.impl.BaseServiceImpl;
import com.hts.web.weibo.service.SinaWeiboService;

@Service("HTSSinaWeiboService")
public class SinaWeiboServiceImpl extends BaseServiceImpl implements SinaWeiboService{

	@Override
	public String[] getFollowerIdsById(String uid,String accessToken,Integer limit) throws Exception{
		if(uid == null || accessToken == null || accessToken.trim().equals("")){
			return null;
		}
		if(limit <= 0){
			limit = 50;
		}
		Friendships fm = new Friendships();
		fm.client.setToken(accessToken);
		try {
			String[] ids = fm.getFollowersIdsById(uid, limit, 0);
			return ids;
		} catch (WeiboException e){
			throw new Exception(e.getMessage());
		}
	}

}
