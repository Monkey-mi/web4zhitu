package com.hts.web.plat.dao.impl;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import weibo4j.Friendships;
import weibo4j.model.WeiboException;

import com.hts.web.base.constant.PlatFormCode;
import com.hts.web.common.pojo.PlatConcern;
import com.hts.web.common.util.StringUtil;
import com.hts.web.plat.dao.PlatConcernDao;

@Repository("HTSPlatConcernDao")
public class PlatConcernDaoImpl implements PlatConcernDao {

	private static Logger log = Logger.getLogger(PlatConcernDaoImpl.class);

	@Override
	public void pushConcern(PlatConcern concern) throws Exception {
		switch(concern.getPid()) {
		case PlatFormCode.SINA:
			pushWeiboConcern(concern);
			Thread.sleep(1000);
			break;
		default:
			
		}
	}
	
	/**
	 * 推送微博关注
	 * 
	 * @param concern
	 * @throws WeiboException 
	 */
	private void pushWeiboConcern(PlatConcern concern) throws WeiboException {
		Friendships fm = new Friendships();
		fm.client.setToken(concern.getUid());
		if(!StringUtil.checkIsNULL(concern.getCid())) 
			fm.createFriendshipsById(concern.getCid());
		else 
			fm.createFriendshipsByName(concern.getCname());
	}
	
	public static void main(String[] args) throws WeiboException {
		
		Friendships fm = new Friendships();
		fm.client.setToken("2.0067kvRCFZjt7D65af25eb320HQTHF");
		fm.createFriendshipsById("2695150601");
	}
	
}
