package com.hts.web.ztworld.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import uk.ltd.getahead.dwr.util.Logger;

import com.hts.web.base.HTSException;
import com.hts.web.base.constant.OptResult;
import com.hts.web.base.constant.Tag;
import com.hts.web.base.database.RowCallback;
import com.hts.web.base.database.RowSelection;
import com.hts.web.common.SerializableListAdapter;
import com.hts.web.common.pojo.HTWorldStickerDto;
import com.hts.web.common.pojo.HTWorldStickerTypeDto;
import com.hts.web.common.pojo.HTWorldStickerUsed;
import com.hts.web.common.service.impl.BaseServiceImpl;
import com.hts.web.common.util.TimeUtil;
import com.hts.web.ztworld.dao.HTWorldStickerCacheDao;
import com.hts.web.ztworld.dao.HTWorldStickerDao;
import com.hts.web.ztworld.dao.HTWorldStickerTypeCacheDao;
import com.hts.web.ztworld.dao.HTWorldStickerUnlockDao;
import com.hts.web.ztworld.dao.HTWorldStickerUsedDao;
import com.hts.web.ztworld.service.ZTWorldStickerService;

@Service("HTSZTWorldStickerService")
public class ZTWorldStickerServiceImpl extends BaseServiceImpl implements
		ZTWorldStickerService {
	
	private static Logger log = Logger.getLogger(ZTWorldStickerServiceImpl.class);
	
	@Autowired
	private HTWorldStickerCacheDao stickerCacheDao;
	
	@Autowired
	private HTWorldStickerTypeCacheDao stickerTypeCacheDao;
	
	@Autowired
	private HTWorldStickerDao stickerDao;
	
	@Autowired
	private HTWorldStickerUnlockDao unlockDao;

	@Autowired
	private HTWorldStickerUsedDao usedDao;
	
	@Value("${op.stickerIntro}")
	private String sharePath;
	
	public String getSharePath() {
		return sharePath;
	}

	public void setSharePath(String sharePath) {
		this.sharePath = sharePath;
	}

	@Override
	public void buildTopSticker(Map<String, Object> jsonMap) throws Exception {
		List<HTWorldStickerDto> list = stickerCacheDao.queryTopSticker();
		jsonMap.put(OptResult.JSON_KEY_STICKER, list);
	}

	@Override
	public void buildRecommendSticker(Integer userId, Map<String, Object> jsonMap)
			throws Exception {
		List<HTWorldStickerDto> list = stickerCacheDao.queryRecommendSticker();
		extractUnlock(userId, list);
		List<HTWorldStickerTypeDto> typeList = stickerTypeCacheDao.queryStickerType();
		List<HTWorldStickerTypeDto> recType = stickerTypeCacheDao.queryRecommendType();
		jsonMap.put(OptResult.JSON_KEY_TYPE, typeList);
		jsonMap.put(OptResult.JSON_KEY_STICKER, list);
		jsonMap.put(OptResult.JSON_KEY_RECOMMEND_TYPE, recType);
	}

	@Override
	public void buildSticker(final Integer userId, final Integer typeId, Integer maxId, Integer start, Integer limit, 
			Map<String, Object> jsonMap) throws Exception {
		buildSerializables("getRecommendId", maxId, start, limit,jsonMap,
				new SerializableListAdapter<HTWorldStickerDto>(){

					@Override
					public List<HTWorldStickerDto> getSerializables(
							RowSelection rowSelection) {
						List<HTWorldStickerDto> list = stickerDao.querySticker(typeId, rowSelection);
						extractUnlock(userId, list);
						return list;
					}

					@Override
					public List<HTWorldStickerDto> getSerializableByMaxId(
							int maxId, RowSelection rowSelection) {
						List<HTWorldStickerDto> list = stickerDao.querySticker(maxId, typeId, rowSelection);
						extractUnlock(userId, list);
						return list;
					}

					@Override
					public long getTotalByMaxId(int maxId) {
						return 0;
					}
			
		},OptResult.JSON_KEY_STICKER, null);
	}

	@Override
	public void buildMaxStickerId(Map<String, Object> jsonMap) throws Exception {
		Integer id = 0;
		HTWorldStickerDto dto = stickerCacheDao.queryFirstRecommendSticker();
		if(dto != null) {
			id = dto.getId();
		}
		jsonMap.put(OptResult.JSON_KEY_MAX_ID, id);
	}

	@Override
	public void extractUnlock(Integer userId, final List<HTWorldStickerDto> list) {
		if(list.size() > 0) {
			final Map<Integer, Integer> indexMap = new HashMap<Integer, Integer>();
			List<Integer> idlist = new ArrayList<Integer>();
			for(int i = 0; i < list.size(); i++) {
				HTWorldStickerDto dto = list.get(i);
				dto.setSharePath(sharePath + "?stid=" + dto.getId());
				if(dto.getHasLock().equals(Tag.TRUE)) {
					indexMap.put(dto.getId(), i);
					idlist.add(dto.getId());
				}
			}
			if(idlist.size() > 0) {
				Integer[] stickerIds = new Integer[idlist.size()];
				unlockDao.queryUnlock(userId, idlist.toArray(stickerIds), new RowCallback<Integer>() {

					@Override
					public void callback(Integer t) {
						Integer idx = indexMap.get(t);
						list.get(idx).setUnlock(Tag.TRUE);
					}
				});
			}
		}
	}

	@Override
	public void unlock(Integer userId, Integer stickerId) throws Exception {
		unlockDao.saveUnlock(stickerId, userId, new Date());
	}

	@Override
	public HTWorldStickerDto getStickerDtoById(Integer id) throws Exception {
		HTWorldStickerDto dto = stickerDao.queryStickerById(id);
		if(dto == null) {
			throw new HTSException("sticker not found");
		}
		return dto;
	}

	@Override
	public void saveStickerUsed(Integer userId, Integer stickerId)
			throws Exception {
		try {
			usedDao.saveUsed(new HTWorldStickerUsed(userId, stickerId, TimeUtil.getTimeLONG()));
		} catch(Exception e) {
			log.warn("save sticker used error", e);
		}
	}

}
