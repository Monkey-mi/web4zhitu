package com.hts.web.operations.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.hts.web.common.dao.impl.BaseDaoImpl;
import com.hts.web.common.pojo.OpNearLabelWorldDto;
import com.hts.web.common.util.JSONUtil;
import com.hts.web.operations.dao.NearLabelWorldUserDao;

@Repository("NearLabelWorldUserDao")
public class NearLabelWorldUserDaoImpl extends BaseDaoImpl implements NearLabelWorldUserDao{
	
	private static final String QUERY_NEAR_LABEL_WORLD_USER_BY_MAX_SERIAL = "select h.*, l.serial "
			+ " from hts.operations_near_label_world l, hts.htworld_htworld h"
			+ " where l.world_id=h.id and l.near_label_id=?"
			+ " order by l.serial desc limit ?";

	@Override
	public List<OpNearLabelWorldDto> queryNearLabelWorldUser(Integer labelId,
			Integer maxSerial, Integer userId, int limit) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void insertNearLabelWorldUser(Integer id, Integer worldId,
			Integer worldAuthorId, Integer nearLabelId, Integer serial) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteNearLabelWorldUserById(Integer id) {
		// TODO Auto-generated method stub
		
	}
	
	private OpNearLabelWorldDto buildOpNearLabelWorldDto(ResultSet rs) throws SQLException{
		OpNearLabelWorldDto dto =  new OpNearLabelWorldDto(rs.getInt("id"), 
				rs.getString("short_link"), 
				rs.getInt("author_id"), 
				rs.getString("world_name"),
				rs.getString("world_desc"),
				rs.getString("world_label"),
				rs.getString("world_type"),
				rs.getInt("type_id"),
				(Date)rs.getObject("date_added"),
				(Date)rs.getObject("date_modified"),
				rs.getInt("click_count"),
				rs.getInt("like_count"),
				rs.getInt("comment_count"),
				rs.getInt("keep_count"),
				rs.getString("cover_path"),
				rs.getString("title_path"),
				rs.getString("bg_path"),
				rs.getString("title_thumb_path"),
				null, 
				rs.getDouble("longitude"),
				rs.getDouble("latitude"), 
				rs.getString("location_desc"),
				rs.getString("location_addr"),
				rs.getInt("phone_code"),
				rs.getString("province"),
				rs.getString("city"),
				rs.getInt("size"),
				rs.getInt("child_count"),
				rs.getInt("ver"),
				rs.getInt("tp"),
				rs.getInt("valid"),
				rs.getInt("shield"),
				JSONUtil.getJSObjectFromText(rs.getString("text_style")),
				urlPrefix+rs.getString("short_link"));
		dto.setRecommendId(rs.getInt("serial"));
		return dto;
	}
	
}
