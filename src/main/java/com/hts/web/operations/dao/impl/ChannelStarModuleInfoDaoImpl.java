package com.hts.web.operations.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import com.hts.web.common.dao.impl.BaseDaoImpl;
import com.hts.web.common.pojo.OpStarModuleInfo;
import com.hts.web.operations.dao.ChannelStarModuleInfoDao;

@Service
public class ChannelStarModuleInfoDaoImpl  extends BaseDaoImpl implements ChannelStarModuleInfoDao {

	private String QUERY_STARMODULEINFO_BY_TOPICID = "SELECT smi.title,smi.subtitle,smi.pics,smi.pic02,smi.pic03,smi.pic04,smi.intro,ui.user_avatar_l,smi.user_id,ui.user_name  "
																									+ "FROM hts.operations_channel_topic_module smi , hts.user_info ui "
																									+ "WHERE smi.user_id = ui.id and smi.topic_id = ? order by smi.order_index asc;";
	
	private String QUERY_STARWORLDMODULEINFO_BY_TOPICID = "SELECT smi.title,smi.subtitle,smi.world_id,smi.intro,ui.user_avatar_l,smi.user_id,ui.user_name  "
			+ "FROM hts.operations_channel_topic_world_module smi , hts.user_info ui "
			+ "WHERE smi.user_id = ui.id and smi.topic_id = ? order by smi.order_index asc;";
	
	@Override
	public List<OpStarModuleInfo> getOpStarModuleInfo(Integer topicId) {
		return getJdbcTemplate().query(QUERY_STARMODULEINFO_BY_TOPICID, 
				new Object[]{topicId}, 
				new RowMapper<OpStarModuleInfo>() {

			@Override
			public OpStarModuleInfo mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return buildOpStarModuleInfo(rs);
			}
		});
	}
	
	public OpStarModuleInfo buildOpStarModuleInfo(ResultSet rs) throws SQLException{
		OpStarModuleInfo starModuleInfo = new OpStarModuleInfo();
		starModuleInfo.setTitle(rs.getString("title"));
		starModuleInfo.setSubtitle(rs.getString("subtitle"));
		starModuleInfo.setPic(rs.getString("pics"));
		starModuleInfo.setPic02(rs.getString("pic02"));
		starModuleInfo.setPic03(rs.getString("pic03"));
		starModuleInfo.setPic04(rs.getString("pic04"));
		starModuleInfo.setIntro2(rs.getString("intro"));
		starModuleInfo.setFacePic(rs.getString("user_avatar_l"));
		starModuleInfo.setUserId(rs.getInt("user_id"));
		starModuleInfo.setUserName(rs.getString("user_name"));
		return starModuleInfo;
	}
	
	@Override
	public List<OpStarModuleInfo> getOpStarWorldModuleInfo(Integer topicId) {
		return getJdbcTemplate().query(QUERY_STARWORLDMODULEINFO_BY_TOPICID, 
				new Object[]{topicId}, 
				new RowMapper<OpStarModuleInfo>() {

			@Override
			public OpStarModuleInfo mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return buildOpStarWorldModuleInfo(rs);
			}
		});
	}
	
	public OpStarModuleInfo buildOpStarWorldModuleInfo(ResultSet rs) throws SQLException{
		OpStarModuleInfo starWorldModuleInfo = new OpStarModuleInfo();
		starWorldModuleInfo .setTitle(rs.getString("title"));
		starWorldModuleInfo .setSubtitle(rs.getString("subtitle"));
		starWorldModuleInfo .setIntro2(rs.getString("intro"));
		starWorldModuleInfo .setFacePic(rs.getString("user_avatar_l"));
		starWorldModuleInfo .setUserId(rs.getInt("user_id"));
		starWorldModuleInfo .setWorldId(rs.getInt("world_id"));
		starWorldModuleInfo .setUserName(rs.getString("user_name"));
		return starWorldModuleInfo ;
	}
}
