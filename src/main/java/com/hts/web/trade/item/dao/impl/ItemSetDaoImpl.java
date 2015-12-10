package com.hts.web.trade.item.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.hts.web.base.database.HTS;
import com.hts.web.common.dao.impl.BaseDaoImpl;
import com.hts.web.trade.item.dao.ItemSetDao;
import com.hts.web.trade.item.dto.ItemDTO;

/**
 * 商品集合数据实现类
 * 
 * @author zhangbo	2015年12月10日
 *
 */
public class ItemSetDaoImpl extends BaseDaoImpl implements ItemSetDao {

	/**
	 * 商品集合表
	 * @author zhangbo	2015年12月10日
	 */
	private static final String item_set_table = HTS.ITEM_SET;
	
	/**
	 * 商品表
	 * @author zhangbo	2015年12月10日
	 */
	private static final String item_table = HTS.ITEM;
	
	/**
	 * 商品与商品集合关联关系 表
	 * @author zhangbo	2015年12月10日
	 */
	private static final String item_set_r_table = HTS.ITEM_SET_R;
	
	private static final String QUERY_ITEM_BY_SETID = "select i.* from " + item_set_table + " s, " + item_table + " i, " + item_set_r_table + "isr "
			+ "where isr.item_id = i.id and isr.item_set_id = s.id";
	
	@Override
	public List<ItemDTO> queryItemListBySetId(Integer itemSetId) throws Exception {
		return getJdbcTemplate().query(QUERY_ITEM_BY_SETID, new Object[]{itemSetId},
				new RowMapper<ItemDTO>() {

					@Override
					public ItemDTO mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						ItemDTO dto = new ItemDTO();
						dto.setId(rs.getInt("id"));
						dto.setName(rs.getString("name"));
						dto.setSummary(rs.getString("summary"));
						dto.setDescription(rs.getString("description"));
						dto.setWorldId(rs.getInt("world_id"));
						dto.setImgPath(rs.getString("img_path"));
						dto.setImgThumb(rs.getString("img_thumb"));
						dto.setPrice(rs.getBigDecimal("price"));
						dto.setSale(rs.getBigDecimal("sale"));
						dto.setSales(rs.getInt("sales"));
						dto.setStock(rs.getInt("stock"));
						dto.setItemId(rs.getInt("item_id"));
						dto.setItemType(rs.getInt("item_type"));
						dto.setLink(rs.getString("link"));
						dto.setLike(rs.getInt("like"));
						
						return dto;
					}
				});
	}
	
}
