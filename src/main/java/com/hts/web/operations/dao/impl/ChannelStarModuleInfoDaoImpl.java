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

	private String QUERY_STARMODULEINFO_BY_TOPICID = "SELECT smi.title1,smi.title2,smi.pics,smi.intro,ui.user_avatar_l,ui.user_name  "
																									+ "FROM hts.operations_channel_topic_module smi , hts.user_info ui "
																									+ "WHERE smi.user_id = ui.id and smi.topic_id = ?;";
	
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
		starModuleInfo.setTitle1(rs.getString("title1"));
		starModuleInfo.setTitle2(rs.getString("title2"));
		starModuleInfo.setPic(rs.getString("pics"));
		starModuleInfo.setIntro2(rs.getString("intro"));
		starModuleInfo.setFacePic(rs.getString("user_avatar_l"));
		starModuleInfo.setUserName(rs.getString("user_name"));
		return starModuleInfo;
	}
}