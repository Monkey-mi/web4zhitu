package com.hts.web.operations.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hts.web.base.constant.OptResult;
import com.hts.web.common.pojo.HTWorld;
import com.hts.web.common.pojo.OpActivity;
import com.hts.web.common.pojo.OpActivitySharePageInfo;
import com.hts.web.common.service.impl.BaseServiceImpl;
import com.hts.web.operations.dao.ActivityDao;
import com.hts.web.operations.service.ActivityService;

@Service("HTSActivityService")
public class ActivityServiceImpl extends BaseServiceImpl implements ActivityService {

	@Autowired
	private ActivityDao activityDao;
	
	@Override
	public void getactivityPageInfoByAid(Integer aid,  Map<String, Object> jsonMap) throws Exception {
		int limites = 27;
		
		OpActivitySharePageInfo opActivitySharePageInfo = new OpActivitySharePageInfo();  
		OpActivity opActivity = activityDao.queryActivityById(aid);
		List<HTWorld> htWorlds = activityDao.getHtWorldByAid(aid,limites);
		
		long now = new Date().getTime();
		long deadline = opActivity.getDeadline().getTime();
		long remainDay = (deadline - now) / (24 *60 * 60 * 1000) + 1;
		
		opActivitySharePageInfo.setRemianDay((int)remainDay);
		opActivitySharePageInfo.setOpActivity(opActivity);
		opActivitySharePageInfo.setHtWorlds(htWorlds);
		
		jsonMap.put(OptResult.JSON_KEY_OBJ, opActivitySharePageInfo);
	}
	
}