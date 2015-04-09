package com.hts.web.ztworld.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import com.hts.web.base.database.HTS;
import com.hts.web.base.database.RowCallback;
import com.hts.web.base.database.SQLUtil;
import com.hts.web.common.dao.impl.BaseDaoImpl;
import com.hts.web.common.pojo.HTWorldChildWorldTypeDto2;
import com.hts.web.ztworld.dao.HTWorldChildWorldTypeDao;

@Repository("HTSHTWorldChildWorldTypeDao")
public class HTWorldChildWorldTypeDaoImpl extends BaseDaoImpl implements
		HTWorldChildWorldTypeDao {
	
	private static String table = HTS.HTWORLD_CHILD_WORLD_TYPE;
	
	/**
	 * 根据ids查询使用数量
	 */
	private static final String QUERY_USE_COUNT_BY_IDS = "select id,use_count from " + table
			+ " where id in ";
	
	/**
	 * 添加使用数量
	 */
	private static final String ADD_USE_COUNT = "update " + table
			+ " set use_count=use_count+1 where id=?";
			

	@Override
	public void queryUseCount(Integer[] ids,
			final RowCallback<HTWorldChildWorldTypeDto2> callback) {
		String inSelection = SQLUtil.buildInSelection(ids);
		String sql = QUERY_USE_COUNT_BY_IDS + inSelection;
		getJdbcTemplate().query(sql, (Object[])ids, new RowCallbackHandler() {
			
			@Override
			public void processRow(ResultSet rs) throws SQLException {
				callback.callback(new HTWorldChildWorldTypeDto2(
						rs.getInt("id"),
						rs.getInt("use_count")));
			}
		});
	}


	@Override
	public void addUseCount(Integer id) {
		getMasterJdbcTemplate().update(ADD_USE_COUNT, id);
	}

}
