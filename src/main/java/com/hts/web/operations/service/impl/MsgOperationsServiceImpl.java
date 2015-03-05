package com.hts.web.operations.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hts.web.base.constant.OptResult;
import com.hts.web.base.constant.Tag;
import com.hts.web.common.pojo.OpNotice;
import com.hts.web.common.pojo.OpSysMsgDto;
import com.hts.web.common.service.impl.BaseServiceImpl;
import com.hts.web.operations.dao.NoticeCacheDao;
import com.hts.web.operations.dao.SysMsgDao;
import com.hts.web.operations.service.MsgOperationsService;

@Service("HTSMsgOperationsService")
public class MsgOperationsServiceImpl extends BaseServiceImpl implements MsgOperationsService{

	@Autowired
	private NoticeCacheDao noticeCacheDao;
	
	@Autowired
	private SysMsgDao sysMsgDao;

	@Override
	public void buildNotice(Integer userId, Integer phoneCode, Map<String, Object> jsonMap) throws Exception {
		OpNotice notice = noticeCacheDao.queryNotice(phoneCode);
		jsonMap.put(OptResult.MSG, notice);
		
		OpSysMsgDto userRecMsg = sysMsgDao.querySysMsgByObjType(userId, Tag.USER_MSG_USER_RECOMMEND);
		if(userRecMsg != null) {
			jsonMap.put(OptResult.JSON_KEY_USER_REC_MSG, userRecMsg);
		}
	}
	
	
}
