package com.hts.web.userinfo.service.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hts.web.aliyun.service.OsUserInfoService;
import com.hts.web.base.constant.Tag;
import com.hts.web.common.pojo.UserActivity;
import com.hts.web.common.service.impl.BaseServiceImpl;
import com.hts.web.operations.service.OpStarRecommendService;
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
	
	@Autowired
	private OpStarRecommendService starRecommendService;

	// 发图积分=5
	private static Integer scoreWorld = 5;
	
	//一周登录有效总次数 2次
	private static Integer oneWeekLoginValidTimes = 2;
	//登录一次算100分
	private static Integer loginScore = 100;
	
	//点赞 一周最多20次
	private static Integer oneWeekLikeValidTimes = 20;
	//点赞一次10分
	private static Integer likeScore = 10;
	
	//评论 一周最多 10
	private static Integer oneWeekCommentValidTimes = 10;
	//评论一次算20分
	private static Integer commentScore = 20;
	
	//轮换一次达人推荐，减200分
	private static Integer changeStarRecommendSub = -200;
	
	//发图积分
	private static Integer firstWorldOfDayScore = 200;
	private static Integer secondWorldOfDayScore = 100;
	private static Integer thirdWorldOfDayScore = 50;

	public Integer getScoreWorld() {
		return scoreWorld;
	}

	public void setScoreWorld(Integer scoreWorld) {
		this.scoreWorld = scoreWorld;
	}

	@Override
	@Deprecated
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
	
	
	/**
	 * 活动规则
	 * @param typeId 定义在Tag类当中
	 * @param userId
	 */
	public void addActivityScore(Integer  typeId,Integer userId)throws Exception{
		Date now = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Integer score = 0;
		if(typeId == Tag.ACT_TYPE_CHANGE_SUB){//轮换推荐导致的减分
			score = changeStarRecommendSub;
			updateAllActivity(userId,typeId,now,score);
		}else if(typeId == Tag.ACT_TYPE_WORLD){//发图
			String tmp = df.format(now);
			try{
				Date begin = df.parse(tmp);
				long r = userActivityDao.queryUserActivityTotalCount(userId, typeId, begin, now);
				if( r == 0 ){
					score = firstWorldOfDayScore;
					updateAllActivity(userId,typeId,now,score);
				}else if( r == 1 ){
					score = secondWorldOfDayScore;
					updateAllActivity(userId,typeId,now,score);
				}else if( r == 2 ){
					score = thirdWorldOfDayScore;
					updateAllActivity(userId,typeId,now,score);
				}
			}catch(Exception e){
				log.warn("update opensearch user activity fail", e);
			}
			
		}else{//非发图
			//转换时间到周一
			Calendar calendar = Calendar.getInstance(Locale.CHINA);
			String tmp = df.format(now);
			Date t = df.parse(tmp);
			calendar.setTime(t);
			int weekday = calendar.get(Calendar.DAY_OF_WEEK);
			if(weekday == Calendar.SUNDAY){
				weekday = 8;
			}
			long m = 1000L*60*60*(weekday-2);
			Date begin = new Date((calendar.getTimeInMillis()-m));
			long r = userActivityDao.queryUserActivityTotalCount(userId, typeId, begin, now);
			switch(typeId){
				case Tag.ACT_TYPE_COMMENT : //评论
					if( r < oneWeekCommentValidTimes ){
						score = commentScore;
						updateAllActivity(userId,typeId,now,score);
					}
					break;
				case Tag.ACT_TYPE_LIKE : 	//点赞
					if( r < oneWeekLikeValidTimes ){
						score = likeScore;
						updateAllActivity(userId,typeId,now,score);
					}
					break;
				case Tag.ACT_TYPE_LOGIN : 	//登录
					if( r < oneWeekLoginValidTimes ){
						score = loginScore;
						updateAllActivity(userId,typeId,now,score);
					}
					break;
			}
		}
		
	}
	
	
	private void updateAllActivity(Integer userId,Integer typeId,Date now,Integer score)throws Exception{
		//user_activity那张表对应的活跃值变化记录
		userActivityDao.saveActivity(new UserActivity(userId,typeId,now,score));
		
		//统计一次该用户的活跃值
		//Integer total = userActivityDao.queryTotalScore(userId);
		
		//用户那张表user_info对应的activity字段
		userInfoDao.updateActivity(userId, score);
		
		//达人推荐那张表operactions_star_recommend中 冗余的activity字段
		starRecommendService.updateStarRecommend(userId, score);
	}
}
