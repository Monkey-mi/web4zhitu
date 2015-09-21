package com.hts.web.aliyun.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.stereotype.Repository;

import com.hts.web.aliyun.dao.OsUserInfoCacheDao;
import com.hts.web.aliyun.dao.OsUserInfoDao;
import com.hts.web.base.constant.CacheKeies;
import com.hts.web.common.pojo.UserOpenSearch;
import com.hts.web.common.util.StringUtil;

@Repository("HTSOsUserInfoCacheDao")
public class OsUserInfoCacheDaoImpl extends BaseOsCacheDaoImpl implements
		OsUserInfoCacheDao {

	private static Logger log = Logger.getLogger(OsUserInfoCacheDaoImpl.class);
	
	@Autowired
	private OsUserInfoDao userInfoDao;
	
	@Override
	public void updateUser(UserOpenSearch user){
		Map<String, Object> fields = new HashMap<String, Object>();
		fields.put("id", user.getId());
		fields.put("user_name", user.getUserName());
		fields.put("name_short_text", user.getUserName());
		fields.put("user_avatar", user.getUserAvatar());
		fields.put("signature", user.getSignature());
		fields.put("platform_sign", user.getPlatformSign());
		fields.put("star", user.getStar());
		fields.put("activity", user.getActivity());
		saveOpt("update", fields, CacheKeies.OS_USER_INFO);
	}

	@Override
	public void updatUserWithoutNULL(UserOpenSearch user) {
		Map<String, Object> fields = new HashMap<String, Object>();
		fields.put("id", user.getId());
		if(user.getUserName() != null) {
			fields.put("user_name", user.getUserName());
			fields.put("name_short_text", user.getUserName());
		}
		
		if(user.getUserAvatar() != null) {
			fields.put("user_avatar", user.getUserAvatar());
		}
		
		if(user.getSignature() != null) {
			fields.put("signature", user.getSignature());
		}
		
		if(user.getPlatformSign() != null) {
			fields.put("platform_sign", user.getPlatformSign());
		}
		
		if(user.getStar() != null) {
			fields.put("star", user.getStar());
		}
		
		if(user.getActivity() != null) {
			fields.put("activity", user.getActivity());
		}
		saveOpt("update", fields, CacheKeies.OS_USER_INFO);
	}
	
	@Override
	public void addUser(UserOpenSearch user) {
		Map<String, Object> fields = new HashMap<String, Object>();
		fields.put("id", user.getId());
		fields.put("user_name", user.getUserName());
		fields.put("name_short_text", user.getUserName());
		fields.put("user_avatar", user.getUserAvatar());
		fields.put("signature", user.getSignature());
		fields.put("platform_sign", user.getPlatformSign());
		fields.put("star", user.getStar());
		fields.put("activity", user.getActivity());
		saveOpt("add", fields, CacheKeies.OS_USER_INFO);
	}
	
	@Override
	public void popOpts(int limit) {
		BoundListOperations<String, String> op = 
				getRedisKeyTemplate().boundListOps(CacheKeies.OS_USER_INFO);
		List<String> list = op.range(0, limit-1);
		if(list == null || list.size() == 0) {
			return;
		}
		
		JSONArray jsarray = JSONArray.fromObject(list);
		try {
			userInfoDao.pushUpdate(jsarray.toString());
		} catch (Exception e) {
			log.warn("pop opensearch userinfo operations error", e);
			return;
		}
		// 清空已生效的操作缓存
		op.trim(list.size(), -1);
	}

}
