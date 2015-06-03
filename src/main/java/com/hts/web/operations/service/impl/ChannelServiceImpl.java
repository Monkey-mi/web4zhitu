package com.hts.web.operations.service.impl;

import java.util.ArrayList;
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
import com.hts.web.common.pojo.OpActivity;
import com.hts.web.common.pojo.OpChannel;
import com.hts.web.common.pojo.OpChannelCount;
import com.hts.web.common.pojo.OpChannelCover;
import com.hts.web.common.pojo.OpChannelDetail;
import com.hts.web.common.pojo.OpChannelLink;
import com.hts.web.common.pojo.OpChannelMember;
import com.hts.web.common.pojo.OpChannelMemberThumb;
import com.hts.web.common.pojo.OpChannelName;
import com.hts.web.common.pojo.OpChannelStar;
import com.hts.web.common.pojo.OpChannelSub;
import com.hts.web.common.pojo.OpChannelSysDanmuDto;
import com.hts.web.common.pojo.OpChannelTheme;
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
import com.hts.web.operations.dao.ChannelCountBaseDao;
import com.hts.web.operations.dao.ChannelCoverCacheDao;
import com.hts.web.operations.dao.ChannelDanmuReadDao;
import com.hts.web.operations.dao.ChannelDao;
import com.hts.web.operations.dao.ChannelLinkDao;
import com.hts.web.operations.dao.ChannelMemberDao;
import com.hts.web.operations.dao.ChannelStarCacheDao;
import com.hts.web.operations.dao.ChannelSysDanmuDao;
import com.hts.web.operations.dao.ChannelThemeCacheDao;
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
	private ChannelThemeCacheDao themeCacheDao;
	
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
	
	@Autowired
	private ChannelSysDanmuDao sysDanumuDao;
	
	@Autowired
	private ChannelDanmuReadDao danmuReadDao;

	@Autowired
	private ChannelCountBaseDao countBaseDao;
	
	@Autowired
	private ChannelLinkDao linkDao;
	
	/**
	 * 2.9.89版本热门频道限定条数
	 */
	private static final int CHANNEL_CACHE_LIMIT_2_9_89 = 8;
	
	private Integer officialId = 45162;
	
	@Override
	public void buildChannel(Map<String, Object> jsonMap) throws Exception {
		List<OpChannel> list = channelCacheDao.queryChannel(
				new RowSelection(1, CHANNEL_CACHE_LIMIT_2_9_89));
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
						// 查询出所有置顶织图
						List<OpChannelWorldDto> worldList = 
								channelWorldDao.queryWeightChannelWorld(channelId, rowSelection);
						// 查询未置顶的织图
						List<OpChannelWorldDto> normalList = channelWorldDao.queryChannelWorld(channelId, rowSelection);
						worldList.addAll(normalList);
						
						worldService.extractExtraInfo(true, true, userId, trimExtras, commentLimit, 
								likedLimit, worldList.size(), worldList);
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
						worldService.extractExtraInfo(true, true, userId, trimExtras, commentLimit, 
								likedLimit, worldList.size(), worldList);
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
		
		buildSerializables("getRecommendId", maxId, start, limit, jsonMap, 
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
						final List<OpChannelName> list = new ArrayList<OpChannelName>();
						channelDao.querySubscribedName(userId, rowSelection, new RowCallback<OpChannelName>() {

							@Override
							public void callback(OpChannelName t) {
								if(!t.getId().equals(officialId)) {
									list.add(t);
								}
							}
						});
						return list;
					}

					@Override
					public List<OpChannelName> getSerializableByMaxId(int maxId, 
							RowSelection rowSelection) {
						final List<OpChannelName> list = new ArrayList<OpChannelName>();
						channelDao.querySubscribedName(maxId, userId, rowSelection, new RowCallback<OpChannelName>() {

							@Override
							public void callback(OpChannelName t) {
								if(!t.getId().equals(officialId)) {
									list.add(t);
								}
							}
							
						});
						return list;
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
	public void buildChannelDetail(Integer channelId, Integer userId, Integer memberLimit,
			Map<String, Object> jsonMap) throws Exception {
		OpChannelDetail detail = channelDao.queryChannelDetail(channelId);
		Integer subscribed = memberDao.queryId(channelId, userId) == 0 ? Tag.FALSE : Tag.TRUE;
		detail.setSubscribed(subscribed);
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
		final List<OpChannel> list = channelCacheDao.queryChannel();
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
		
		List<OpChannelTheme> themes = themeCacheDao.queryTheme();
		
		jsonMap.put(OptResult.JSON_KEY_CHANNELS, list);
		jsonMap.put(OptResult.JSON_KEY_THEMES, themes);
	}

	@Override
	public void saveMember(Integer channelId, Integer userId) throws Exception {
		if(channelId == null && userId == null) {
			throw new HTSException("channelId or userId can not be null");
		}
		memberDao.saveMember(new OpChannelMember(channelId, userId, 
				Tag.CHANNEL_MEMBER_NORMAL, new Date()));
		updateMemberCount(channelId);
	}

	@Override
	public void deleteMember(Integer channelId, Integer userId)
			throws Exception {
		memberDao.deleteMember(channelId, userId);
		updateMemberCount(channelId);
	}

	@Override
	public void saveChannelWorld(Integer channelId, Integer worldId,
			Integer authorId, Integer addChildCount) {
		Integer id = keyGenService.generateId(KeyGenServiceImpl.OP_CHANNEL_WORLD_ID);
		OpChannelWorld world = new OpChannelWorld(id, channelId,
				worldId, authorId, new Date(), Tag.TRUE, Tag.TRUE, Tag.FALSE, id);
		channelWorldDao.saveChannelWorld(world);
		addWorldCountAndChildCount(channelId, addChildCount);
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

	@Override
	public void buildSysDanmu(final Integer channelId, final Integer userId, Integer maxId,
			Integer start, final Integer limit, Map<String, Object> jsonMap) throws Exception {
		
		buildSerializables("getRecommendId", maxId, start, limit, jsonMap, 
				new SerializableListAdapter<OpChannelSysDanmuDto>() {

					@Override
					public List<OpChannelSysDanmuDto> getSerializables(RowSelection rowSelection) {
						List<OpChannelSysDanmuDto> list = getSysDanmuList(channelId, userId, rowSelection);
						
						//　检测到列表为空，表示已经将该频道下的弹幕看完，从头开始获取弹幕
						if(list == null || list.size() < limit) {
							list = sysDanumuDao.querySysDanmu(channelId, rowSelection);
							if(list.size() > 0) {
								Integer maxSerial = list.get(list.size() - 1).getRecommendId();
								danmuReadDao.updateDanmuSerial(channelId, userId, maxSerial);
							}
						}
						return list;
						
					}

					@Override
					public List<OpChannelSysDanmuDto> getSerializableByMaxId(int maxId, 
							RowSelection rowSelection) {
						List<OpChannelSysDanmuDto> list = getSysDanmuList(maxId, channelId, userId, rowSelection);
						
						//　检测到列表为空，表示已经将该频道下的弹幕看完，从头开始获取弹幕
						if(list == null || list.size() < limit) {
							danmuReadDao.updateDanmuSerial(channelId, userId, 0);
							list = sysDanumuDao.querySysDanmu(channelId, rowSelection);
							if(list.size() > 0) {
								Integer maxSerial = list.get(list.size() - 1).getRecommendId();
								danmuReadDao.updateDanmuSerial(channelId, userId, maxSerial);
							}
						}
						return list;
					}

					@Override
					public long getTotalByMaxId(int maxId) {
						return 0;
					}
					
		}, OptResult.JSON_KEY_MSG, null);
	}
	
	/**
	 * 获取系统弹幕列表
	 * 
	 * @param channelId
	 * @param userId
	 * @param rowSelection
	 * @return
	 */
	private List<OpChannelSysDanmuDto> getSysDanmuList(Integer channelId, Integer userId,
			RowSelection rowSelection) {
		List<OpChannelSysDanmuDto> list = null;
		Integer maxSerial = danmuReadDao.queryDanmuSerial(channelId, userId);
		if(maxSerial == 0) { // 未看过改频道的系统弹幕
			list = sysDanumuDao.querySysDanmu(channelId, rowSelection);
			if(list.size() > 0) {
				maxSerial = list.get(list.size() - 1).getRecommendId();
				danmuReadDao.saveDanmuSerial(channelId, userId, maxSerial);
			}
		} else { // 曾经看过改频道的系统弹幕，从历史记录开始查看
			list = sysDanumuDao.querySysDanmu(maxSerial - 1, channelId, rowSelection);
			if(list.size() > 0) {
				maxSerial = list.get(list.size() - 1).getRecommendId();
				danmuReadDao.updateDanmuSerial(channelId, userId, maxSerial);
			}
		}
		return list;
	}
	
	/**
	 * 根据最大id获取系统弹幕列表
	 * 
	 * @param maxId
	 * @param channelId
	 * @param userId
	 * @param rowSelection
	 * @return
	 */
	private List<OpChannelSysDanmuDto> getSysDanmuList(Integer maxId, Integer channelId, Integer userId, 
			RowSelection rowSelection) {
		List<OpChannelSysDanmuDto> list = 
				sysDanumuDao.querySysDanmu(maxId, channelId, rowSelection);
		Integer maxSerial = 0;
		if(list != null && list.size() > 0) {
			maxSerial = list.get(list.size() - 1).getRecommendId();
			danmuReadDao.updateDanmuSerial(channelId, userId, maxSerial);
		}
		return list;
	}

	@Override
	public void addWorldCountAndChildCount(Integer channelId,
			Integer addChildCount) {
		channelDao.addWorldAndChildCount(channelId, 1, addChildCount);
	}

	@Override
	public void updateWorldAndChildCount(Integer channelId) {
		Integer worldCount = channelWorldDao.queryWorldCount(channelId).intValue();
		Integer childCount = channelWorldDao.queryChildCount(channelId);
		Integer[] countBase = countBaseDao.queryWorldAndChildCount(channelId);
		worldCount = countBase[0] + worldCount;
		childCount = countBase[1] + childCount;
		channelDao.updateWorldAndChildCount(channelId, worldCount, childCount);
	}

	@Override
	public void updateMemberCount(Integer channelId) {
		Integer memberCount = memberDao.queryMemberCount(channelId).intValue();
		Integer countBase = countBaseDao.queryMemberCount(channelId);
		memberCount = memberCount + countBase;
		channelDao.updateMemberCount(channelId, memberCount);
	}

	@Override
	public void updateSuperbCount(Integer channelId) {
		Integer superbCount = channelWorldDao.querySuperbCount(channelId).intValue();
		Integer countBase = countBaseDao.querySuperbCount(channelId);
		superbCount = superbCount + countBase;
		channelDao.updateSuperbCount(channelId, superbCount);
	}

	@Override
	public void buildLinkChannel(Integer channelId, Map<String, Object> jsonMap) {
		List<OpChannelLink> list = linkDao.queryLink(channelId);
		jsonMap.put(OptResult.JSON_KEY_CHANNEL, list);
	}

	@Override
	public void buildThemeChannel(final Integer themeId, Integer maxId, Integer start, 
			Integer limit, Map<String, Object> jsonMap) throws Exception {
		buildSerializables("getRecommendId", maxId, start, limit, jsonMap, 
				new SerializableListAdapter<OpChannel>() {

					@Override
					public List<OpChannel> getSerializables(RowSelection rowSelection) {
						return channelDao.queryThemeChannel(themeId, rowSelection);
						
					}

					@Override
					public List<OpChannel> getSerializableByMaxId(int maxId, 
							RowSelection rowSelection) {
						return channelDao.queryThemeChannel(maxId, themeId, rowSelection);
					}

					@Override
					public long getTotalByMaxId(int maxId) {
						return 0;
					}
					
		}, OptResult.JSON_KEY_CHANNELS, null);
	}
}
