package com.hts.web.ztworld.service;

import java.util.List;
import java.util.Map;

import com.hts.web.common.pojo.HTWorldStickerDto;
import com.hts.web.common.pojo.StickerWithLock;
import com.hts.web.common.service.BaseService;

/**
 * <p>
 * 贴纸业务逻辑访问接口
 * </p>
 * 
 * 创建时间：2014-12-26
 * @author lynch
 *
 */
public interface ZTWorldStickerService extends BaseService {

	/**
	 * 构建置顶贴纸列表
	 * 
	 * @param jsonMap
	 * @throws Exception
	 */
	public void buildTopSticker(Integer userId, Map<String, Object> jsonMap) throws Exception;
	
	/**
	 * 构建推荐贴纸列表
	 * 
	 * @param jsonMap
	 * @throws Exception
	 */
	public void buildRecommendSticker(Integer userId, Map<String, Object> jsonMap) throws Exception;
	
	/**
	 * 构建贴纸列表
	 * 
	 * @param jsonMap
	 * @throws Exception
	 */
	public void buildSticker(Integer userId, Integer typeId, Integer maxId, Integer start, Integer limit, 
			Map<String, Object> jsonMap) throws Exception;
	
	/**
	 * 根据id获取贴纸
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public HTWorldStickerDto getStickerDtoById(Integer id) throws Exception;
	
	/**
	 * 构建最大贴纸id
	 * 
	 * @param jsonMap
	 * @throws Exception
	 */
	public void buildMaxStickerId(Map<String, Object> jsonMap) throws Exception;
	
	/**
	 * 解锁贴纸
	 * 
	 * @param userId
	 * @param stickerId
	 * @throws Exception
	 */
	public void unlock(Integer userId, Integer stickerId) throws Exception;
	
	/**
	 * 提取解锁标记
	 * 
	 * @param userId
	 * @param list
	 * @throws Exception
	 */
	public void extractUnlock(Integer userId, 
			List<? extends StickerWithLock> list) throws Exception;
	
	/**
	 * 保存贴纸使用记录
	 * 
	 * @param userId
	 * @param stickerId
	 * @throws Exception
	 */
	public void saveStickerUsed(Integer userId, 
			Integer stickerId) throws Exception;
	
	
}
