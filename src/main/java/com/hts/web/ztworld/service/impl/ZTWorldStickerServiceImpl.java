package com.hts.web.ztworld.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.hts.web.base.HTSException;
import com.hts.web.base.constant.OptResult;
import com.hts.web.base.constant.Tag;
import com.hts.web.base.database.RowCallback;
import com.hts.web.base.database.RowSelection;
import com.hts.web.common.SerializableListAdapter;
import com.hts.web.common.pojo.HTWorldStickerDto;
import com.hts.web.common.pojo.HTWorldStickerSetDto;
import com.hts.web.common.pojo.HTWorldStickerTop;
import com.hts.web.common.pojo.HTWorldStickerTypeDto;
import com.hts.web.common.pojo.HTWorldStickerUsed;
import com.hts.web.common.pojo.StickerWithLock;
import com.hts.web.common.service.impl.BaseServiceImpl;
import com.hts.web.common.util.Log;
import com.hts.web.common.util.TimeUtil;
import com.hts.web.ztworld.dao.HTWorldStickerCacheDao;
import com.hts.web.ztworld.dao.HTWorldStickerDao;
import com.hts.web.ztworld.dao.HTWorldStickerTypeCacheDao;
import com.hts.web.ztworld.dao.HTWorldStickerUnlockDao;
import com.hts.web.ztworld.dao.HTWorldStickerUsedDao;
import com.hts.web.ztworld.dao.StickerSetDtoCacheDao;
import com.hts.web.ztworld.dao.StickerTopCacheDao;
import com.hts.web.ztworld.service.ZTWorldStickerService;

@Service("HTSZTWorldStickerService")
public class ZTWorldStickerServiceImpl extends BaseServiceImpl implements
		ZTWorldStickerService {
	
	private static Logger log = Logger.getLogger(ZTWorldStickerServiceImpl.class);
	
	@Autowired
	private HTWorldStickerCacheDao stickerCacheDao;
	
	@Autowired
	private StickerTopCacheDao stickerTopCacheDao;
	
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
	
	@Autowired
	private StickerSetDtoCacheDao stickerSetDtoCacheDao;
	
	public String getSharePath() {
		return sharePath;
	}

	public void setSharePath(String sharePath) {
		this.sharePath = sharePath;
	}

	@Override
	public void buildTopSticker(Integer userId, Map<String, Object> jsonMap) throws Exception {
		List<HTWorldStickerTop> list = stickerTopCacheDao.queryTopSticker();
		extractTopUnlock(userId, list);
		jsonMap.put(OptResult.JSON_KEY_STICKER, list);
	}

	@Override
	public void buildRecommendSticker(Integer userId, Map<String, Object> jsonMap)
			throws Exception {
		List<HTWorldStickerDto> list = stickerCacheDao.queryRecommendSticker();
		extractUnlock(userId, list);
		List<HTWorldStickerTypeDto> typeList = stickerTypeCacheDao.queryStickerType();
		if(typeList != null && typeList.get(0).getId() == -1) {
			typeList.remove(0);
		}
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
		jsonMap.put(OptResult.JSON_KEY_STICKER, dto);
	}

	@Override
	public void extractUnlock(Integer userId, final List<? extends StickerWithLock> list) {
		if(list.size() > 0) {
			final Map<Integer, Integer> indexMap = new HashMap<Integer, Integer>();
			List<Integer> idlist = new ArrayList<Integer>();
			for(int i = 0; i < list.size(); i++) {
				StickerWithLock dto = list.get(i);
				dto.setSharePath(sharePath + "?stid=" + dto.getStickerId());
				if(dto.getHasLock().equals(Tag.TRUE)) {
					indexMap.put(dto.getStickerId(), i);
					idlist.add(dto.getStickerId());
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
	
	public void extractTopUnlock(Integer userId, final List<HTWorldStickerTop> list) {
		if(list.size() > 0) {
			final Map<Integer, Integer[]> indexMap = new HashMap<Integer, Integer[]>();
			List<Integer> idlist = new ArrayList<Integer>();
			for(int i = 0; i < list.size(); i++) {
				List<HTWorldStickerDto> sets = list.get(i).getSets();
				
				if(sets == null) {
					StickerWithLock dto = list.get(i);
					dto.setSharePath(sharePath + "?stid=" + dto.getStickerId());
					indexMap.put(dto.getStickerId(), new Integer[]{i,-1});
					idlist.add(dto.getStickerId());
				} else {
					for(int k = 0; k < sets.size(); k++) {
						StickerWithLock dto = list.get(i).getSets().get(k);
						dto.setSharePath(sharePath + "?stid=" + dto.getStickerId());
						if(dto.getHasLock().equals(Tag.TRUE)) {
							indexMap.put(dto.getStickerId(), new Integer[]{i,k});
							idlist.add(dto.getStickerId());
							if(k == 0) {
								HTWorldStickerTop top = list.get(i);
								top.setSharePath(sharePath + "?stid=" + top.getStickerId());
							}
							
						}
					}
					
				}
				
			}
			if(idlist.size() > 0) {
				Integer[] stickerIds = new Integer[idlist.size()];
				unlockDao.queryUnlock(userId, idlist.toArray(stickerIds), new RowCallback<Integer>() {

					@Override
					public void callback(Integer t) {
						Integer[] idx = indexMap.get(t);
						int i = idx[0];
						int k = idx[1];
						if(k == -1) {
							list.get(i).setUnlock(Tag.TRUE);
						} else {
							list.get(i).getSets().get(k).setUnlock(Tag.TRUE);
							if(k == 0) {
								list.get(i).setUnlock(Tag.TRUE);
							}
						}
						
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

	@Override
	public void buildLib(Integer typeId, Integer start, Integer limit,
			Map<String, Object> jsonMap) throws Exception {
		int firstRow = 0;
		int maxRow = -1;
		List<HTWorldStickerSetDto> list = null;
		if(typeId == null || typeId == 0 || typeId == -1) {
			typeId = -1;
			jsonMap.put(OptResult.JSON_KEY_TYPE, stickerTypeCacheDao.queryStickerType());
		}
		firstRow = (start - 1) * limit;
		maxRow = firstRow + limit - 1;
		list = stickerSetDtoCacheDao.querySet(typeId, firstRow, maxRow);
		jsonMap.put(OptResult.JSON_KEY_STICKER, list);
	}
	
}
