package com.hts.web.operations.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hts.web.base.constant.OptResult;
import com.hts.web.base.database.RowSelection;
import com.hts.web.common.SerializableListAdapter;
import com.hts.web.common.pojo.OpActivity;
import com.hts.web.common.pojo.OpChannel;
import com.hts.web.common.pojo.OpChannelCover;
import com.hts.web.common.pojo.OpChannelStar;
import com.hts.web.common.pojo.OpChannelTopOne;
import com.hts.web.common.pojo.OpChannelTopOneTitle;
import com.hts.web.common.pojo.OpChannelWorld;
import com.hts.web.common.service.impl.BaseServiceImpl;
import com.hts.web.operations.dao.ActivityCacheDao;
import com.hts.web.operations.dao.ChannelCacheDao;
import com.hts.web.operations.dao.ChannelCoverCacheDao;
import com.hts.web.operations.dao.ChannelStarCacheDao;
import com.hts.web.operations.dao.ChannelTopOneCacheDao;
import com.hts.web.operations.dao.ChannelTopOneTitleCacheDao;
import com.hts.web.operations.dao.ChannelWorldDao;
import com.hts.web.operations.service.ChannelService;
import com.hts.web.userinfo.service.UserConcernService;
import com.hts.web.userinfo.service.UserInfoService;
import com.hts.web.userinfo.service.UserInteractService;
import com.hts.web.ztworld.service.ZTWorldService;

@Service("HTSChannelService")
public class ChannelServiceImpl extends BaseServiceImpl implements
		ChannelService {

	@Autowired
	private ChannelCacheDao channelCacheDao;
	
	@Autowired
	private ChannelWorldDao channelWorldDao;
	
	@Autowired
	private ChannelStarCacheDao channelStarCacheDao;
	
	@Autowired
	private ChannelTopOneCacheDao channelTopOneCacheDao;
	
	@Autowired
	private ChannelTopOneTitleCacheDao channelTopOneTitleCacheDao;
	
	@Autowired
	private ZTWorldService worldService;
	
	@Autowired
	private UserInfoService userInfoService;
	
	@Autowired
	private UserConcernService userConcernService;
	
	@Autowired
	private ActivityCacheDao activityCacheDao;
	
	@Autowired
	private UserInteractService userInteractService;
	
	@Autowired
	private ChannelCoverCacheDao channelCoverCacheDao;
	
	@Override
	public void buildChannel(Map<String, Object> jsonMap) throws Exception {
		List<OpChannel> list = channelCacheDao.queryChannel();
		OpActivity maxAct = activityCacheDao.queryMaxActivity();
		OpChannelTopOne maxTopOne = channelTopOneCacheDao.queryMaxTopOne();
		Integer maxActId = maxAct != null ? maxAct.getId() : 0;
		Integer maxTopOneId = maxTopOne != null ? maxTopOne.getTopOneId() : 0;
		
		List<OpChannelCover> thumbs = channelCoverCacheDao.queryCacheCover();
		
		jsonMap.put(OptResult.JSON_KEY_MAX_ACTIVITY_ID, maxActId);
		jsonMap.put(OptResult.JSON_KEY_MAX_TOP_ONE_ID, maxTopOneId);
		jsonMap.put(OptResult.JSON_KEY_CHANNELS, list);
		jsonMap.put(OptResult.JSON_KEY_HTWORLD, thumbs);
	}
	
	@Override
	public void buildMaxChannelTopOne(Map<String, Object> jsonMap) throws Exception {
		OpActivity maxAct = activityCacheDao.queryMaxActivity();
		OpChannelTopOne maxTopOne = channelTopOneCacheDao.queryMaxTopOne();
		Integer maxActId = maxAct != null ? maxAct.getId() : 0;
		Integer maxTopOneId = maxTopOne != null ? maxTopOne.getTopOneId() : 0;
		
		jsonMap.put(OptResult.JSON_KEY_ACTIVITY, maxAct);
		jsonMap.put(OptResult.JSON_KEY_MAX_ACTIVITY_ID, maxActId);
		jsonMap.put(OptResult.JSON_KEY_MAX_TOP_ONE_ID, maxTopOneId);
	}
	
	@Override
	public void buildChannelWorld(final Integer channelId, final Integer userId, Integer maxId,
			Integer start, Integer limit, final Boolean trimExtras, final Integer commentLimit,
			final Integer likedLimit, final Integer completeLimit, final Map<String, Object> jsonMap)
			throws Exception {
		buildSerializables("getRecommendId", maxId, start, limit, jsonMap, 
				new SerializableListAdapter<OpChannelWorld>() {

					@Override
					public List<OpChannelWorld> getSerializables(RowSelection rowSelection) {
						List<OpChannelWorld> worldList = channelWorldDao.queryChannelWorld(channelId, rowSelection);
						int extrasLimit = 0;
						if(completeLimit > 0)
							extrasLimit = Math.min(completeLimit, worldList.size());
						else 
							extrasLimit = worldList.size();
						worldService.extractExtraInfo(true, true, userId, trimExtras, commentLimit, likedLimit, extrasLimit, worldList);
						userInfoService.extractVerify(worldList);
						userConcernService.extractConcernStatus(userId, worldList);
						userInteractService.extractRemark(userId, worldList);
						
						// 获取频道红人， 并将查询用户放在第一
						List<OpChannelStar> starList = channelStarCacheDao.queryStar(channelId);
						for(OpChannelStar star : starList) {
							if(star.getId().equals(userId)) {
								starList.remove(star);
								starList.add(0, star);
								break;
							}
						}
						jsonMap.put(OptResult.JSON_KEY_STARS, starList);
						
						return worldList;
					}

					@Override
					public List<OpChannelWorld> getSerializableByMaxId(int maxId, RowSelection rowSelection) {
						List<OpChannelWorld> worldList = channelWorldDao.queryChannelWorld(maxId, channelId, rowSelection);
						int extrasLimit = 0;
						if(completeLimit > 0)
							extrasLimit = Math.min(completeLimit, worldList.size());
						else 
							extrasLimit = worldList.size();
						worldService.extractExtraInfo(true, true, userId, trimExtras, commentLimit, likedLimit, extrasLimit, worldList);
						userInfoService.extractVerify(worldList);
						userInteractService.extractRemark(userId, worldList);
						userConcernService.extractConcernStatus(userId, worldList);
						return worldList;
					}

					@Override
					public long getTotalByMaxId(int maxId) {
						return 0;
					}
					
		}, OptResult.JSON_KEY_HTWORLD, null);
		
	}

	@Override
	public void buildChannelTopOne(Integer userId, Map<String, Object> jsonMap)
			throws Exception {
		List<OpChannelTopOne> toplist = channelTopOneCacheDao.queryTopOne();
		userConcernService.extractIsMututal(userId, toplist);
		userInteractService.extractRemark(userId, toplist);
		OpChannelTopOneTitle title = channelTopOneTitleCacheDao.queryTitle();
		jsonMap.put(OptResult.JSON_KEY_USER_INFO, toplist);
		jsonMap.put(OptResult.JSON_KEY_TITLE, title);
	}

}
