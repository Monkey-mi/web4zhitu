package com.hts.web.operations.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hts.web.base.HTSException;
import com.hts.web.base.constant.OptResult;
import com.hts.web.base.constant.Tag;
import com.hts.web.base.database.RowCallback;
import com.hts.web.base.database.RowSelection;
import com.hts.web.common.SerializableListAdapter;
import com.hts.web.common.pojo.HTWorldLabel;
import com.hts.web.common.pojo.HTWorldLabelWorld;
import com.hts.web.common.pojo.OpActivity;
import com.hts.web.common.pojo.OpChannel;
import com.hts.web.common.pojo.OpChannelCount;
import com.hts.web.common.pojo.OpChannelCover;
import com.hts.web.common.pojo.OpChannelDetail;
import com.hts.web.common.pojo.OpChannelMember;
import com.hts.web.common.pojo.OpChannelMemberThumb;
import com.hts.web.common.pojo.OpChannelName;
import com.hts.web.common.pojo.OpChannelStar;
import com.hts.web.common.pojo.OpChannelSub;
import com.hts.web.common.pojo.OpChannelTopOne;
import com.hts.web.common.pojo.OpChannelTopOneTitle;
import com.hts.web.common.pojo.OpChannelWorld;
import com.hts.web.common.pojo.OpChannelWorldDto;
import com.hts.web.common.service.KeyGenService;
import com.hts.web.common.service.impl.BaseServiceImpl;
import com.hts.web.common.service.impl.KeyGenServiceImpl;
import com.hts.web.common.util.StringUtil;
import com.hts.web.operations.dao.ActivityCacheDao;
import com.hts.web.operations.dao.ChannelCacheDao;
import com.hts.web.operations.dao.ChannelCoverCacheDao;
import com.hts.web.operations.dao.ChannelDao;
import com.hts.web.operations.dao.ChannelMemberDao;
import com.hts.web.operations.dao.ChannelStarCacheDao;
import com.hts.web.operations.dao.ChannelTopOneCacheDao;
import com.hts.web.operations.dao.ChannelTopOneTitleCacheDao;
import com.hts.web.operations.dao.ChannelWorldDao;
import com.hts.web.operations.service.ChannelService;
import com.hts.web.userinfo.service.UserConcernService;
import com.hts.web.userinfo.service.UserInfoService;
import com.hts.web.userinfo.service.UserInteractService;
import com.hts.web.ztworld.dao.HTWorldLabelDao;
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
	
	@Autowired
	private ChannelDao channelDao;
	
	@Autowired
	private ChannelMemberDao memberDao;
	
	@Autowired
	private KeyGenService keyGenService;
	
	@Autowired
	private HTWorldLabelDao labelDao;
	
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
				new SerializableListAdapter<OpChannelWorldDto>() {

					@Override
					public List<OpChannelWorldDto> getSerializables(RowSelection rowSelection) {
						List<OpChannelWorldDto> worldList = channelWorldDao.queryChannelWorld(channelId, rowSelection);
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
					public List<OpChannelWorldDto> getSerializableByMaxId(int maxId, RowSelection rowSelection) {
						List<OpChannelWorldDto> worldList = channelWorldDao.queryChannelWorld(maxId, channelId, rowSelection);
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
	public void buildSuperbChannelWorld(final Integer channelId, final Integer userId, Integer maxId,
			Integer start, Integer limit, final Boolean trimExtras, final Integer commentLimit,
			final Integer likedLimit, final Integer completeLimit, final Map<String, Object> jsonMap)
			throws Exception {
		buildSerializables("getRecommendId", maxId, start, limit, jsonMap, 
				new SerializableListAdapter<OpChannelWorldDto>() {

					@Override
					public List<OpChannelWorldDto> getSerializables(RowSelection rowSelection) {
						List<OpChannelWorldDto> worldList = channelWorldDao.querySuperbChannelWorld(channelId, rowSelection);
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
					public List<OpChannelWorldDto> getSerializableByMaxId(int maxId, RowSelection rowSelection) {
						List<OpChannelWorldDto> worldList = channelWorldDao.querySuperbChannelWorld(maxId, channelId, rowSelection);
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

	@Override
	public void buildSubscribedChannel(final Boolean nameOnly, final Integer userId,
			Integer maxId, Integer start, Integer limit, Map<String, Object> jsonMap)
			throws Exception {
		if(nameOnly) {
			buildSubscribedName(userId, maxId, start, limit, jsonMap);
			return;
		}
		
		buildSerializables(maxId, start, limit, jsonMap, 
				new SerializableListAdapter<OpChannel>() {

					@Override
					public List<OpChannel> getSerializables(RowSelection rowSelection) {
						return channelDao.querySubscribedChannel(userId, rowSelection);
					}

					@Override
					public List<OpChannel> getSerializableByMaxId(int maxId, 
							RowSelection rowSelection) {
						return channelDao.querySubscribedChannel(maxId, userId, rowSelection);
					}

					@Override
					public long getTotalByMaxId(int maxId) {
						return 0;
					}
					
		}, OptResult.JSON_KEY_CHANNELS, null);
	}
	
	private void buildSubscribedName(final Integer userId,
			Integer maxId, Integer start, Integer limit, Map<String, Object> jsonMap) throws Exception {
		buildSerializables(maxId, start, limit, jsonMap, 
				new SerializableListAdapter<OpChannelName>() {

					@Override
					public List<OpChannelName> getSerializables(RowSelection rowSelection) {
						return channelDao.querySubscribedName(userId, rowSelection);
					}

					@Override
					public List<OpChannelName> getSerializableByMaxId(int maxId, 
							RowSelection rowSelection) {
						return channelDao.querySubscribedName(maxId, userId, rowSelection);
					}

					@Override
					public long getTotalByMaxId(int maxId) {
						return 0;
					}
					
		}, OptResult.JSON_KEY_CHANNELS, null);
	}
	
	@Override
	public void buildChannelAbstract(Integer channelId, Integer userId,
			Map<String, Object> jsonMap) throws Exception {
		OpChannel channel = channelDao.queryChannel(channelId);
		Integer subscribed = memberDao.queryId(channelId, userId) == 0 ? Tag.FALSE : Tag.TRUE;
		channel.setSubscribed(subscribed);
		jsonMap.put(OptResult.JSON_KEY_CHANNEL, channel);
	}

	@Override
	public void buildChannelDetail(Integer channelId, Integer memberLimit,
			Map<String, Object> jsonMap) throws Exception {
		OpChannelDetail detail = channelDao.queryChannelDetail(channelId);
		userInfoService.extractVerify(detail.getOwner());
		if(memberLimit != null && memberLimit > 0) {
			List<OpChannelMemberThumb> members = 
					memberDao.queryMemberThumb(channelId, new RowSelection(1, memberLimit));
			detail.setMembers(members);
		}
		jsonMap.put(OptResult.JSON_KEY_CHANNEL, detail);
	}

	@Override
	public void buildHot(Integer start, Integer limit, Integer userId,
			Map<String, Object> jsonMap) throws Exception {
		final List<OpChannel> list = channelCacheDao.queryChannel(
				new RowSelection(start, limit));
		if(list != null && list.size() > 0) {
			final Map<Integer, Integer> idxMap = new HashMap<Integer, Integer>();
			Integer[] ids = new Integer[list.size()];
			for(int i = 0; i < list.size(); i++) {
				Integer id = list.get(i).getId();
				ids[i] = id;
				idxMap.put(id, i);
			}
			channelDao.queryChannelCount(ids, new RowCallback<OpChannelCount>() {

						@Override
						public void callback(OpChannelCount t) {
							int id = t.getId();
							int idx = idxMap.get(id);
							list.get(idx).setWorldCount(t.getWorldCount());
							list.get(idx).setChildCount(t.getChildCount());
							list.get(idx).setMemberCount(t.getMemberCount());
							list.get(idx).setSuperbCount(t.getSuperbCount());
						}
			});
			memberDao.queryChannelSub(userId, ids, new RowCallback<OpChannelSub>() {

				@Override
				public void callback(OpChannelSub t) {
					int id = t.getChannelId();
					int idx = idxMap.get(id);
					list.get(idx).setSubscribed(Tag.TRUE);
				}
				
			});
		}
		jsonMap.put(OptResult.JSON_KEY_CHANNELS, list);
	}

	@Override
	public void saveMember(Integer channelId, Integer userId) throws Exception {
		if(channelId == null && userId == null) {
			throw new HTSException("channelId or userId can not be null");
		}
		memberDao.saveMember(new OpChannelMember(channelId, userId, 
				Tag.CHANNEL_MEMBER_NORMAL, new Date()));
	}

	@Override
	public void deleteMember(Integer channelId, Integer userId)
			throws Exception {
		memberDao.deleteMember(channelId, userId);
	}

	@Override
	public void saveChannelWorld(Integer channelId, Integer worldId,
			Integer authorId, Integer addChildCount) {
		Integer id = keyGenService.generateId(KeyGenServiceImpl.OP_CHANNEL_WORLD_ID);
		OpChannelWorld world = new OpChannelWorld(id, channelId,
				worldId, authorId, new Date(), Tag.TRUE, Tag.TRUE, Tag.FALSE, 0);
		channelWorldDao.saveChannelWorld(world);
		Long worldCount = channelWorldDao.queryWorldCount(channelId);
		channelDao.updateWorldAddChildCount(channelId, worldCount.intValue(), addChildCount);
	}

	@Override
	public void saveChannel(Integer ownerId, String channelName,
			String channelTitle, String subtitle, String channelDesc,
			String channelIcon, String subIcon, Integer channelType,
			String channelLabel, Integer danmu, Integer mood, Integer world) {

		Set<Integer> labeIdSet = new LinkedHashSet<Integer>();
		
		OpChannel channel = new OpChannel();
		channel.setOwnerId(ownerId);
		channel.setChannelName(channelName);
		channel.setChannelTitle(channelTitle);
		channel.setSubtitle(subtitle);
		channel.setChannelDesc(channelDesc);
		channel.setChannelIcon(channelIcon);
		channel.setSubIcon(subIcon);
		channel.setChannelType(channelType);
		channel.setChannelLabel(channelLabel);
		channel.setDanmu(danmu);
		channel.setMood(mood);
		channel.setWorld(world);
		
		if(!StringUtil.checkIsNULL(channelLabel)) {
			String[] names = channelLabel.split(",");
			Set<String> nameSet = new LinkedHashSet<String>();
			Map<String, HTWorldLabel> labelMap = labelDao.queryLabelByNames(names);
			for(String name : names) {
				name = StringUtil.filterXSS(name);
				if(StringUtil.checkIsNULL(name) || name.trim().equals("") || nameSet.contains(name)) {
					continue;
				}
				nameSet.add(name);
				HTWorldLabel label = labelMap.get(name);
				Integer labelId = 0;
				if(label == null) {
					labelId = keyGenService.generateId(KeyGenServiceImpl.HTWORLD_LABEL_ID);
					String pinyin = StringUtil.getPinYin(name);
					label = new HTWorldLabel(labelId, name, pinyin, 0, new Date(), Tag.FALSE, Tag.TRUE, 0, 0);
					labelDao.saveLabel(label);
				} else {
					labelId = label.getId();
				}
				labeIdSet.add(labelId);
			}
			String[] labelArray = new String[nameSet.size()];
			String[] labelIdArray = new String[labeIdSet.size()];
			channel.setChannelLabel(StringUtil.convertArrayToString(nameSet.toArray(labelArray)));
			channel.setLabelIds(StringUtil.convertArrayToString(labeIdSet.toArray(labelIdArray)));
		}
		
		Integer id = keyGenService.generateId(KeyGenServiceImpl.OP_CHANNEL_ID);
		channel.setId(id);
		Date date = new Date();
		channel.setCreateTime(date);
		channel.setLastModified(date);
		channelDao.saveChannel(channel);
		
		// TODO save channel and label link
		
	}
	

}
