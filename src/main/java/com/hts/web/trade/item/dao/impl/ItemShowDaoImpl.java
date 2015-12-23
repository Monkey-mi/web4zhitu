package com.hts.web.trade.item.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import com.hts.web.common.dao.impl.BaseDaoImpl;
import com.hts.web.trade.item.dto.ItemShow;
import com.hts.web.trade.item.dao.ItemShowDao;

@Service
public class ItemShowDaoImpl extends BaseDaoImpl implements ItemShowDao {
	
	private static final String QUERY_ITEM_SHOW = "select * from hts.item_show where item_set_id = ? order by serial desc";

	@Override
	public List<ItemShow> queryItemShow(Integer itemSetId) throws Exception {
		
		return getJdbcTemplate().query(QUERY_ITEM_SHOW, 
				new Object[]{itemSetId},new RowMapper<ItemShow>(){

					@Override
					public ItemShow mapRow(ResultSet rs, int rowNum) throws SQLException {
						ItemShow itemShow = new ItemShow();
						itemShow.setId(rs.getInt("id"));
						itemShow.setWorldId(rs.getInt("world_id"));
						itemShow.setItemSetId(rs.getInt("item_set_id"));
						itemShow.setSerial(rs.getInt("serial"));
						return itemShow;
					}
			
		});
	}

}
