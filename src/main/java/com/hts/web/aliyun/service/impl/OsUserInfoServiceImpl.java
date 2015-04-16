package com.hts.web.aliyun.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hts.web.aliyun.dao.OsUserInfoCacheDao;
import com.hts.web.aliyun.dao.OsUserLoginCacheDao;
import com.hts.web.aliyun.service.OsUserInfoService;
import com.hts.web.common.pojo.UserOpenSearch;
import com.hts.web.common.util.TimeUtil;

@Service("HTSOsUserInfoService")
public class OsUserInfoServiceImpl implements OsUserInfoService {
	
	@Autowired
	private OsUserInfoCacheDao osUserInfoCacheDao;
	
	@Autowired
	private OsUserLoginCacheDao osUserLoginCacheDao;
	
	private Integer optCommitLimit = 100;

	@Override
	public void saveUser(Integer id, String userName, String userAvatar,
			String signature, String platformSign, Integer star, Integer activity)
			throws Exception {
		osUserInfoCacheDao.addUser(new UserOpenSearch(id, userName, userAvatar,
				signature, platformSign, star, activity));
		osUserLoginCacheDao.saveLogin(id, TimeUtil.getTimeINT());
	}

	@Override
	public void updateUser(Integer id, String userName, String userAvatar,
			String signature, String platformSign, Integer star, Integer activity)
			throws Exception {
		osUserInfoCacheDao.updateUser(new UserOpenSearch(id, userName, userAvatar,
				signature, platformSign, star, activity));
		osUserLoginCacheDao.updateLogin(id, TimeUtil.getTimeINT());
	}

	@Override
	public void updateUserWithoutNULL(Integer id, String userName,
			String userAvatar, String signature, String platformSign,
			Integer star, Integer activity) throws Exception {
		osUserInfoCacheDao.updatUserWithoutNULL(new UserOpenSearch(id, userName, userAvatar,
				signature, platformSign, star, activity));
		osUserLoginCacheDao.updateLogin(id, TimeUtil.getTimeINT());
	}

	@Override
	public void updateLastLogin(Integer id, Integer lastLogin) throws Exception {
		osUserLoginCacheDao.updateLogin(id, lastLogin);
	}

	@Override
	public void commitOpt() throws Exception {
		osUserInfoCacheDao.popOpts(optCommitLimit);
		osUserLoginCacheDao.popOpts(optCommitLimit);
	}

}
