package com.hts.web.operations.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hts.web.base.constant.OptResult;
import com.hts.web.base.database.RowCallback;
import com.hts.web.common.pojo.HTWorldThumbUser;
import com.hts.web.common.pojo.OpStarRecommendDto;
import com.hts.web.common.pojo.UserWithWorld;
import com.hts.web.common.service.impl.BaseServiceImpl;
import com.hts.web.operations.dao.OpStarRecommendCacheDao;
import com.hts.web.operations.dao.OpStarRecommendDao;
import com.hts.web.operations.service.OpStarRecommendService;
import com.hts.web.userinfo.service.UserInfoService;
import com.hts.web.ztworld.dao.HTWorldDao;

@Repository("HTSOpStarRecommendService")
public class OpStarRecommendServiceImpl extends BaseServiceImpl implements OpStarRecommendService{

	@Autowired
	private OpStarRecommendDao starRecommendDao;
	
	@Autowired
	private HTWorldDao worldDao;
	
	@Autowired
	private UserInfoService userInfoService;
	
	@Autowired
	private OpStarRecommendCacheDao starRecommendCacheDao;
	
	public static int defaultWorldLimit = 3;
	public static int defaulStarRecommendLimit = 10;
	
	/**
	 * 本来打算用于分页查询的，结果好像用不上，所以就用来作为普通查询
	 */
	@Override
	public List<OpStarRecommendDto> queryStarRecommend(int maxId, int start, int limit, int worldLimit)throws Exception{
		// TODO Auto-generated method stub
		List<OpStarRecommendDto>list = starRecommendDao.queryStarRecommend(maxId, start-1, limit);
		extractHTWorldThumbUser(worldLimit, list);
		userInfoService.extractVerify(list);
		return list;
	}
	
	/**
	 * 查询达人推荐，先从缓存中查询，若缓存中没有数据，则从数据库中查询数据
	 */
	@Override
	public void queryStarRecommend(Map<String, Object> jsonMap)throws Exception {
		List<OpStarRecommendDto>list = queryStarRecommend();
		if(list == null || list.size() < 1){
			list = queryStarRecommend(0,1,defaulStarRecommendLimit,defaultWorldLimit);
		}
		jsonMap.put(OptResult.JSON_KEY_USER_INFO, list);
	}
	
	/**
	 * 从缓存中查询达人推荐数据
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<OpStarRecommendDto> queryStarRecommend()throws Exception{
		return starRecommendCacheDao.queryStarRecommendFromCache();
	}

	/**
	 * 更新达人活跃值
	 */
	@Override
	public void updateStarRecommend(Integer userId, Integer activity)
			throws Exception {
		// TODO Auto-generated method stub
		starRecommendDao.updateStarRecommendActivity(userId, activity);
	}
	
	/**
	 * 更新缓存
	 */
	@Override
	public void updateStarRecommendCache()throws Exception{
		List<OpStarRecommendDto>list = queryStarRecommend(0,1,defaulStarRecommendLimit,defaultWorldLimit);
		starRecommendCacheDao.updateStarRecommendCache(list);
	}
	
	
	public void extractHTWorldThumbUser(int worldLimit, final List<? extends UserWithWorld> userList) {
		int listSize = userList.size();
		if(listSize > 0 && worldLimit > 0) {
			final Map<Integer, Integer> indexMap = new HashMap<Integer, Integer>();
			Integer[] userIds = new Integer[listSize];
			for(int i = 0; i < listSize; i++) {
				Integer userId = userList.get(i).getId();
				userIds[i] = userId;
				indexMap.put(userId, i);
			}
			worldDao.queryHTWorldThumbUserByLimit(userIds, worldLimit, new RowCallback<HTWorldThumbUser>() {

				@Override
				public void callback(HTWorldThumbUser thumb) {
					Integer uid = thumb.getUserId();
					Integer index = indexMap.get(uid);
					userList.get(index).getHtworld().add(thumb);
				}
			});
		}
	}

}
