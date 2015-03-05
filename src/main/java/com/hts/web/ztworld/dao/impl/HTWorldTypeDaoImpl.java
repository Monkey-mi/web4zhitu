package com.hts.web.ztworld.dao.impl;

import org.springframework.stereotype.Repository;

import com.hts.web.base.database.HTS;
import com.hts.web.common.dao.impl.BaseDaoImpl;
import com.hts.web.common.pojo.HTWorldTypeWorld;
import com.hts.web.ztworld.dao.HTWorldTypeDao;

@Repository("HTSHTWorldTypeDao")
public class HTWorldTypeDaoImpl extends BaseDaoImpl implements HTWorldTypeDao {
	
	private static String table = HTS.HTWORLD_TYPE_WORLD;
	
	/**
	 * 保存分类织图
	 */
	private static final String SAVE_TYPE_WORLD = "insert into " + table
			+ " (id, world_id, type_id, superb, valid, serial, weight, recommender_id) values (?,?,?,?,?,?,?,?)";

	@Override
	public void saveTypeWorld(HTWorldTypeWorld typeWorld) {
		getJdbcTemplate().update(SAVE_TYPE_WORLD, new Object[]{
				typeWorld.getId(),
				typeWorld.getWorldId(),
				typeWorld.getTypeId(),
				typeWorld.getSuperb(),
				typeWorld.getValid(),
				typeWorld.getSerial(),
				typeWorld.getWeight(),
				typeWorld.getRecommenderId()
			});
	}

}
