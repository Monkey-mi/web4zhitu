package com.hts.web.trade.item.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import com.hts.web.base.database.HTS;
import com.hts.web.common.dao.impl.BaseDaoImpl;
import com.hts.web.trade.item.dao.ItemSetDao;
import com.hts.web.trade.item.dto.ItemSetDTO;

/**
 * 商品集合数据接口实现类
 * 
 * @author zhangbo	2015年12月12日
 *
 */
@Service("com.hts.web.trade.item.dao.impl.ItemSetDaoImpl")
public class ItemSetDaoImpl extends BaseDaoImpl implements ItemSetDao {
	
	private static final String table = HTS.ITEM_SET;
	
	/**
	 * 查询商品集合列表
	 * 注：根据serial序号倒序，根据maxId（即serial的值），确定查询范围
	 * 
	 * @author zhangbo	2015年12月13日
	 */
	private static final String QUERY_ITEM_SET = "select * from " + table + " where serial<=? order by serial desc limit ?";
	
	/**
	 * 得到商品集合对象
	 * 
	 * @author zhangbo	2015年12月13日
	 */
	private static final String GET_ITEM_SET = "select * from " + table + " where id = ?";

	@Override
	public List<ItemSetDTO> queryItemSetList(Integer maxId, Integer limit) throws Exception {
		
		return getJdbcTemplate().query(QUERY_ITEM_SET, 
				new Object[]{maxId,limit}, new RowMapper<ItemSetDTO>(){

			@Override
			public ItemSetDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				ItemSetDTO dto = new ItemSetDTO();
				dto.setId(rs.getInt("id"));
				dto.setDescription(rs.getString("description"));
				dto.setPath(rs.getString("path"));
				dto.setThumb(rs.getString("thumb"));
				dto.setType(rs.getInt("type"));
				dto.setLink(rs.getString("link"));
				dto.setSerial(rs.getInt("serial"));
				return dto;
			}
	
		});
	}

	@Override
	public ItemSetDTO getItemSet(Integer id) throws Exception {
		
		return getJdbcTemplate().queryForObject(GET_ITEM_SET, 
				new Object[]{id}, new RowMapper<ItemSetDTO>(){

			@Override
			public ItemSetDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				ItemSetDTO dto = new ItemSetDTO();
				dto.setId(rs.getInt("id"));
				dto.setTitle(rs.getString("title"));
				dto.setDescription(rs.getString("description"));
				dto.setPath(rs.getString("path"));
				dto.setThumb(rs.getString("thumb"));
				dto.setType(rs.getInt("type"));
				dto.setLink(rs.getString("link"));
				dto.setSerial(rs.getInt("serial"));
				return dto;
			}
	
		});
	}

}
