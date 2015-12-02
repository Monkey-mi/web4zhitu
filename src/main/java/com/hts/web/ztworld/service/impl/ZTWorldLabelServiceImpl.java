package com.hts.web.ztworld.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hts.web.base.HTSErrorCode;
import com.hts.web.base.HTSException;
import com.hts.web.base.constant.OptResult;
import com.hts.web.base.constant.Tag;
import com.hts.web.base.database.RowCallback;
import com.hts.web.base.database.RowSelection;
import com.hts.web.common.SerializableListAdapter;
import com.hts.web.common.pojo.HTWorldInteractDto;
import com.hts.web.common.pojo.HTWorldLabel;
import com.hts.web.common.pojo.HTWorldLabelWorldAuthor;
import com.hts.web.common.pojo.HTWorldStickerDto;
import com.hts.web.common.pojo.OpActivity;
import com.hts.web.common.pojo.OpActivityAward;
import com.hts.web.common.pojo.OpActivitySponsor;
import com.hts.web.common.service.KeyGenService;
import com.hts.web.common.service.impl.BaseServiceImpl;
import com.hts.web.common.service.impl.KeyGenServiceImpl;
import com.hts.web.common.util.StringUtil;
import com.hts.web.operations.dao.ActivityAwardDao;
import com.hts.web.operations.dao.ActivityCacheDao;
import com.hts.web.operations.dao.ActivityDao;
import com.hts.web.operations.dao.ActivitySponsorDao;
import com.hts.web.operations.dao.ActivityWinnerDao;
import com.hts.web.operations.service.ZTWorldOperationsService;
import com.hts.web.userinfo.service.UserConcernService;
import com.hts.web.userinfo.service.UserInfoService;
import com.hts.web.userinfo.service.UserInteractService;
import com.hts.web.ztworld.dao.HTWorldLabelCacheDao;
import com.hts.web.ztworld.dao.HTWorldLabelDao;
import com.hts.web.ztworld.dao.HTWorldLabelWorldDao;
import com.hts.web.ztworld.dao.HTWorldStickerDao;
import com.hts.web.ztworld.service.ZTWorldLabelService;
import com.hts.web.ztworld.service.ZTWorldService;

/**
 * <p>
 * 织图标签业务逻辑访问对象
 * </p>
 * 
 * 创建时间：2014-5-5
 * @author tianjie
 *
 */
@Service("HTSZTWorldLabelService")
public class ZTWorldLabelServiceImpl extends BaseServiceImpl implements
		ZTWorldLabelService {


	@Autowired
	private KeyGenService keyGenService;
	
	@Autowired
	private HTWorldLabelDao worldLabelDao;
	
	@Autowired
	private HTWorldLabelCacheDao worldLabelCacheDao;
	
	@Autowired
	private HTWorldLabelWorldDao worldLabelWorldDao;
	
	@Autowired
	private ActivityDao activityDao;
	
	@Autowired
	private ActivityCacheDao activityCacheDao;
	
	@Autowired
	private ActivitySponsorDao activitySponsorDao;
	
	@Autowired
	private ActivityAwardDao activityAwardDao;
	
	@Autowired
	private ActivityWinnerDao activityWinnerDao;
	
	@Autowired
	private ZTWorldOperationsService worldOperationsService;
	
	@Autowired
	private ZTWorldService worldService;
	
	@Autowired
	private UserInfoService userInfoService;
	
	@Autowired
	private UserInteractService userInteractService;
	
	@Autowired
	private HTWorldStickerDao stickerDao;
	
	@Autowired
	private UserConcernService userConcernService;
	
	private Integer hotLabelLimit = 10;
	
	public Integer getHotLabelLimit() {
		return hotLabelLimit;
	}

	public void setHotLabelLimit(Integer hotLabelLimit) {
		this.hotLabelLimit = hotLabelLimit;
	}

	@Override
	public HTWorldLabel saveLabel(String name) throws Exception {
		HTWorldLabel label = worldLabelDao.queryLabelByName(name);
		if(label == null) {
			Integer labelId = keyGenService.generateId(KeyGenServiceImpl.HTWORLD_LABEL_ID);
			String pinyin = StringUtil.getPinYin(name);
			label = new HTWorldLabel(labelId, name, pinyin, 0, 0, new Date(), Tag.FALSE, Tag.TRUE, 0, 0);
			worldLabelDao.saveLabel(label);
		}
		return label;
	}

	@Override
	public void buildFuzzyLabel(String name, int limit,
			Map<String, Object> jsonMap) {
		List<HTWorldLabel> list = worldLabelDao.queryFuzzyLabelByName(name, new RowSelection(1, limit));
		jsonMap.put(OptResult.JSON_KEY_LABEL_INFO, list);
	}

	@Override
	public void buildHotLabel(Map<String, Object> jsonMap) {
		List<HTWorldLabel> list = worldLabelCacheDao.queryHotLabel(
				new RowSelection(1, hotLabelLimit));
		jsonMap.put(OptResult.JSON_KEY_LABEL_INFO, list);
	}

	
	@Override
	public void buildActivityLabel(Map<String, Object> jsonMap) {
		List<HTWorldLabel> list = worldLabelCacheDao.queryActivityLabel(
				new RowSelection(1, hotLabelLimit));
		jsonMap.put(OptResult.JSON_KEY_LABEL_INFO, list);
	}

	@Override
	public void buildLabelWorld(boolean isOrderBySerial, String labelName, boolean trimValid, Integer joinId, int maxId,
			int start, int limit, Map<String, Object> jsonMap, boolean trimTotal, 
			int commentLimit, int likedLimit) throws Exception {
		labelName = labelName.trim();
		final HTWorldLabel label = worldLabelDao.queryLabelByName(labelName);
		if(label == null) {
			throw new HTSException(HTSErrorCode.INVALID_LABEL);
		}

		Integer state = label.getLabelState();
		switch (state) {
		case Tag.WORLD_LABEL_ACTIVITY:
//			if(!trimValid) {
//				OpActivity activity = activityDao.queryActivityById(label.getId());
//				if(activity.getValid().equals(Tag.FALSE)) { // 活动已经失效
//					worldOperationsService.buildActivityWinner(isOrderBySerial, label.getId(), joinId, maxId,
//							start, limit, jsonMap, trimTotal, trimExtras, commentLimit, likedLimit);
//					break;
//				}
//			}
			buildLabelWorld(isOrderBySerial, label.getId(), joinId, maxId, start, limit, jsonMap, 
					trimTotal, commentLimit, likedLimit);
			if(maxId == 0)
				worldOperationsService.buildActivityStar(joinId, label.getId(), jsonMap); // 添加明星列表
			break;
		default:
			buildLabelWorld(isOrderBySerial, label.getId(), joinId, maxId, start, limit, jsonMap, 
					trimTotal, commentLimit, likedLimit);
			break;
		}
	}

	@Override
	public void buildLabelWorld(final boolean isOrderBySerial, final Integer labelId, final Integer joinId, int maxId,
			int start, int limit, Map<String, Object> jsonMap,
			final boolean trimTotal, final int commentLimit,
			final int likedLimit) throws Exception {
		String totalKey = trimTotal ? null : OptResult.JSON_KEY_TOTAL_COUNT;
		String getIdMethod = "getId";
		if(isOrderBySerial) {
			getIdMethod = "getInteractId";
		}
		buildSerializables(getIdMethod, maxId, start, limit, jsonMap,
				new SerializableListAdapter<HTWorldInteractDto>() {

					@Override
					public List<HTWorldInteractDto> getSerializables(
							RowSelection rowSelection) {
						List<HTWorldInteractDto> worldList = null;
						if(!isOrderBySerial) {
							worldList = worldLabelWorldDao.queryLabelWorld(joinId, labelId, rowSelection);
						} else {
							worldList = worldLabelWorldDao.queryLabelWorldV2(joinId, labelId, rowSelection);
						}
						worldService.extractLikeComment(joinId, commentLimit, likedLimit, worldList);
						userInfoService.extractVerify(worldList);
						userInteractService.extractRemark(joinId, worldList);
						userConcernService.extractConcernStatus(joinId, worldList);
						return worldList;
					}

					@Override
					public List<HTWorldInteractDto> getSerializableByMaxId(
							int maxId, RowSelection rowSelection) {
						List<HTWorldInteractDto> worldList = null;
						if(!isOrderBySerial) {
							worldList = worldLabelWorldDao.queryLabelWorld(maxId, joinId, labelId, rowSelection);
						} else {
							worldList = worldLabelWorldDao.queryLabelWorldV2(maxId, joinId, labelId, rowSelection);
						}
						worldService.extractLikeComment(joinId, commentLimit, likedLimit, worldList);
						userInfoService.extractVerify(worldList);
						userInteractService.extractRemark(joinId, worldList);
						userConcernService.extractConcernStatus(joinId, worldList);
						return worldList;
					}

					@Override
					public long getTotalByMaxId(int maxId) {
						return 0l;
					}

				}, OptResult.JSON_KEY_HTWORLD, totalKey);
	}
	
	@Override
	public void buildLabelSuperbWorld(String labelName, final Integer joinId, int maxId,
			int start, int limit, Map<String, Object> jsonMap,
			final boolean trimTotal, final int commentLimit,
			final int likedLimit) throws Exception {
		String name = labelName.trim();
		HTWorldLabel label = worldLabelDao.queryLabelByName(name);
		final Integer labelId = label.getId();
		buildSerializables("getInteractId", maxId, start, limit, jsonMap,
				new SerializableListAdapter<HTWorldInteractDto>() {

					@Override
					public List<HTWorldInteractDto> getSerializables(
							RowSelection rowSelection) {
						List<HTWorldInteractDto> worldList = 
								worldLabelWorldDao.queryLabelSuperbWorld(labelId, rowSelection);
						worldService.extractLikeComment(joinId, commentLimit, likedLimit, worldList);
						userInfoService.extractVerify(worldList);
						userInteractService.extractRemark(joinId, worldList);
						userConcernService.extractConcernStatus(joinId, worldList);
						return worldList;
					}

					@Override
					public List<HTWorldInteractDto> getSerializableByMaxId(
							int maxId, RowSelection rowSelection) {
						List<HTWorldInteractDto> worldList = 
								worldLabelWorldDao.queryLabelSuperbWorld(maxId, labelId, rowSelection);
						worldService.extractLikeComment(joinId, commentLimit, likedLimit, worldList);
						userInfoService.extractVerify(worldList);
						userInteractService.extractRemark(joinId, worldList);
						userConcernService.extractConcernStatus(joinId, worldList);
						return worldList;
					}

					@Override
					public long getTotalByMaxId(int maxId) {
						return 0l;
					}

				}, OptResult.JSON_KEY_HTWORLD, null);
	}

	@Override
	public void buildLabel(String labelName, Map<String, Object> jsonMap)
			throws Exception {
		labelName = labelName.trim();
		final HTWorldLabel label = worldLabelDao.queryLabelByName(labelName);
		if(label == null) {
			throw new HTSException(HTSErrorCode.INVALID_LABEL);
		}
		
		jsonMap.put(OptResult.JSON_KEY_LABEL_INFO, label);
		if(label.getLabelState().equals(Tag.WORLD_LABEL_ACTIVITY)) {
			OpActivity activity = activityCacheDao.queryActivity(labelName);
			if(activity == null) {
				activity = activityDao.queryActivityById(label.getId());
				final List<OpActivitySponsor> sponsors = activity.getSponsors();
				activitySponsorDao.querySponsor(activity.getId(),
					new RowCallback<OpActivitySponsor>() {
						
						@Override
						public void callback(OpActivitySponsor t) {
							sponsors.add(t);
						}
					});
				final List<OpActivityAward> awards = activity.getAwards();
				activityAwardDao.queryAward(label.getId(), new RowCallback<OpActivityAward>() {

					@Override
					public void callback(OpActivityAward t) {
						awards.add(t);
					}
				});
				HTWorldStickerDto sticker = stickerDao.queryStickerByLabelId(label.getId());
				activity.setSticker(sticker);
				activity.setActivityName(labelName);
			}
			activity.setWorldCount(label.getWorldCount());
			activity.setSuperbCount(label.getSuperbCount());
			activity.setWinnerId(activityWinnerDao.queryMaxWinnerId(activity.getId()));
			jsonMap.put(OptResult.JSON_KEY_ACTIVITY, activity);
		}
	}

	@Override
	public void buildLabelWorldAuthor(final Integer labelId, final Integer joinId, int maxId,
			int start, int limit, Map<String, Object> jsonMap) throws Exception {
		buildSerializables(maxId, start, limit, jsonMap,
				new SerializableListAdapter<HTWorldLabelWorldAuthor>() {

					@Override
					public List<HTWorldLabelWorldAuthor> getSerializables(
							RowSelection rowSelection) {
						List<HTWorldLabelWorldAuthor> list = worldLabelWorldDao.queryLabelWorldAuthor(labelId, 
								joinId, rowSelection);
						userInfoService.extractVerify(list);
						userInteractService.extractRemark(joinId, list);
						return list;
					}

					@Override
					public List<HTWorldLabelWorldAuthor> getSerializableByMaxId(
							int maxId, RowSelection rowSelection) {
						rowSelection.setStart(1);
						List<HTWorldLabelWorldAuthor> list = worldLabelWorldDao.queryLabelWorldAuthor(maxId, 
								labelId, joinId, rowSelection);
						userInfoService.extractVerify(list);
						userInteractService.extractRemark(joinId, list);
						return list;
					}

					@Override
					public long getTotalByMaxId(int maxId) {
						return 0l;
					}

				}, OptResult.JSON_KEY_USER_INFO, null);
	}

}
