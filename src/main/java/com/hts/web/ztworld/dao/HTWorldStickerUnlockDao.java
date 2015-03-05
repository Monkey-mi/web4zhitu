package com.hts.web.ztworld.dao;

import java.util.Date;

import com.hts.web.base.database.RowCallback;
import com.hts.web.common.dao.BaseDao;

public interface HTWorldStickerUnlockDao extends BaseDao {

	/**
	 * 保存解锁
	 * 
	 * @param stickerId
	 * @param userId
	 */
	public void saveUnlock(Integer stickerId, Integer userId, Date date);
	
	/**
	 * 查询解锁列表
	 * 
	 * @param userId
	 * @param stickerIds
	 * @param callback
	 */
	public void queryUnlock(Integer userId, Integer[] stickerIds, 
			RowCallback<Integer> callback);
}
