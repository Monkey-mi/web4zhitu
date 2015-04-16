package com.hts.web.ztworld.dao.impl;

import org.springframework.stereotype.Repository;

import com.hts.web.base.database.HTS;
import com.hts.web.common.dao.impl.BaseDaoImpl;
import com.hts.web.common.pojo.HTWorldStickerUsed;
import com.hts.web.ztworld.dao.HTWorldStickerUsedDao;

@Repository("HTSStickerUsedDao")
public class HTWorldStickerUsedDaoImpl extends BaseDaoImpl implements
		HTWorldStickerUsedDao {

	private static String table = HTS.HTWORLD_STICKER_USED;
	
	private static final String SAVE_USED = "insert into " + table
			+ " (user_id,sticker_id,used_time) values (?,?,?)";
	
	@Override
	public void saveUsed(HTWorldStickerUsed used) {
		getJdbcTemplate().update(SAVE_USED, new Object[]{
				used.getUserId(),
				used.getStickerId(),
				used.getUsedTime()});
	}

}
