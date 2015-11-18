package com.hts.web.operations.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hts.web.base.constant.OptResult;
import com.hts.web.common.pojo.HTWorld;
import com.hts.web.common.pojo.OpActivity;
import com.hts.web.common.pojo.OpActivitySharePageInfo;
import com.hts.web.common.pojo.OpActivityStar;
import com.hts.web.common.service.impl.BaseServiceImpl;
import com.hts.web.operations.dao.ActivityDao;
import com.hts.web.operations.dao.ActivityStarCacheDao;
import com.hts.web.operations.service.ActivityService;
import com.hts.web.ztworld.dao.HTWorldLabelDao;

@Service("HTSActivityService")
public class ActivityServiceImpl extends BaseServiceImpl implements ActivityService {

	@Autowired
	private ActivityDao activityDao;
	
	@Autowired
	private HTWorldLabelDao worldLabelDao;
	
	@Autowired
	private ActivityStarCacheDao activityStarCacheDao;
	
	@Override
	public void getactivityPageInfoByAid(Integer aid,  Map<String, Object> jsonMap) throws Exception {
		int limites = 27;
		
		OpActivitySharePageInfo opActivitySharePageInfo = new OpActivitySharePageInfo();  
		OpActivity opActivity = activityDao.queryActivityById(aid);
		String lableName = activityDao.queryLableName(aid);
		List<HTWorld> htWorlds = activityDao.getHtWorldByAid(aid,limites);
		List<OpActivityStar> starList = activityStarCacheDao.queryStar(aid);
		
		long now = new Date().getTime();
		Date deadline = opActivity.getDeadline();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		String s=sdf.format(deadline);
		
		long deadLineTime = sdf.parse(s).getTime();
		long remainDay = (deadLineTime - now) / (24 *60 * 60 * 1000) + 2;
		/*int activityCount = activityDao.getActivityCount(aid);*/
		int worldCount = worldLabelDao.queryWorldCount(aid);
		
		opActivitySharePageInfo.setRemianDay((int)remainDay);
		opActivitySharePageInfo.setOpActivity(opActivity);
		opActivitySharePageInfo.setHtWorlds(htWorlds);
		opActivitySharePageInfo.setLabelName(lableName);
		opActivitySharePageInfo.setActivityCount(worldCount);
		opActivitySharePageInfo.setOpActivityStars(starList);
		
		jsonMap.put(OptResult.JSON_KEY_OBJ, opActivitySharePageInfo);
	}
	
}
