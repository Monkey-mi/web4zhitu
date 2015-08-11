package com.hts.web.operations.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hts.web.base.constant.CacheKeies;
import com.hts.web.base.database.RowSelection;
import com.hts.web.common.dao.impl.BaseCacheDaoImpl;
import com.hts.web.common.pojo.OpUser;
import com.hts.web.common.pojo.UserInfoDto;
import com.hts.web.operations.dao.UserRecommendDao;
import com.hts.web.operations.dao.UserVerifyRecCacheDao;

@Repository("HTSUserVerifyRecCacheDao")
public class UserVerifyRecCacheDaoImpl extends BaseCacheDaoImpl<UserInfoDto> 
	implements UserVerifyRecCacheDao {
	
	@Autowired
	private UserRecommendDao dao;

	@Override
	public List<UserInfoDto> queryUserByVerifyId(Integer verifyId, Integer limit) {
		return getRedisTemplate().boundListOps(
				CacheKeies.OP_USER_VERIFY_REC + verifyId).range(0, limit - 1);
	}

	@Override
	public void update(Integer verifyId) {
		String key = CacheKeies.OP_USER_VERIFY_REC + verifyId;
		if(getRedisTemplate().hasKey(key)){
			getRedisTemplate().delete(key);
		}
		List<OpUser> ous = dao.queryRecommendUserOrderByAct(0, new RowSelection(1, 100));
		List<UserInfoDto> users = new ArrayList<UserInfoDto>();
		for(OpUser u : ous) {
			UserInfoDto dto = new UserInfoDto();
			dto.setId(u.getId());
			dto.setUserName(u.getUserName());
			dto.setUserAvatar(u.getUserAvatar());
			dto.setUserAvatarL(u.getUserAvatarL());
			System.out.println(dto.getId() + " : " + dto.getUserName());
			users.add(dto);
		}
		UserInfoDto[] objs = new UserInfoDto[users.size()];
		getRedisTemplate().opsForList().rightPushAll(key, users.toArray(objs));
	}
	
}
