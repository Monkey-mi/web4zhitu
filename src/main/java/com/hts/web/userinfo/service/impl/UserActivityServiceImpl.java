package com.hts.web.userinfo.service.impl;

import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hts.web.aliyun.service.OsUserInfoService;
import com.hts.web.base.constant.Tag;
import com.hts.web.common.pojo.UserActivity;
import com.hts.web.common.service.impl.BaseServiceImpl;
import com.hts.web.userinfo.dao.UserActivityDao;
import com.hts.web.userinfo.dao.UserInfoDao;
import com.hts.web.userinfo.service.UserActivityService;

@Service("HTSUserActivityService")
public class UserActivityServiceImpl extends BaseServiceImpl implements
		UserActivityService {
	
	private static Logger log = Logger.getLogger(UserActivityServiceImpl.class);
	
	@Autowired
	private UserActivityDao userActivityDao;
	
	@Autowired
	private UserInfoDao userInfoDao;
	
	@Autowired
	private OsUserInfoService osUserInfoService;

	// 发图积分=5
	private Integer scoreWorld = 5;
	

	public Integer getScoreWorld() {
		return scoreWorld;
	}

	public void setScoreWorld(Integer scoreWorld) {
		this.scoreWorld = scoreWorld;
	}

	@Override
	public void addActivityScore(Integer typeId, int mutiple, Integer userId) {
		Integer score = 0;
		switch (typeId) {
		case Tag.ACT_TYPE_WORLD:
			score = scoreWorld;
			
			break;
		default:
			break;
		}
		
		if(score > 0) {
			if(mutiple < 1) {
				mutiple = 1;
			}
			userActivityDao.saveActivity(new UserActivity(userId, 
					typeId, new Date(), score * mutiple));
			Integer total = userActivityDao.queryTotalScore(userId);
			userInfoDao.updateActivity(userId, total);
			try {
				osUserInfoService.updateUserWithoutNULL(userId, null, null, null, null, null, total);
			} catch (Exception e) {
				log.warn("update opensearch user activity fail", e);
			}
		}
		
	}

}
