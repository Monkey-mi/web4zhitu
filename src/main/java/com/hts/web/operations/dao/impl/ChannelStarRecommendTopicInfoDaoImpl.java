package com.hts.web.operations.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import com.hts.web.common.dao.impl.BaseDaoImpl;
import com.hts.web.common.pojo.OpStarRecommendTopicInfo;
import com.hts.web.operations.dao.ChannelStarRecommendTopicInfoDao;


@Service
public class ChannelStarRecommendTopicInfoDaoImpl extends BaseDaoImpl implements ChannelStarRecommendTopicInfoDao {
	
	private static String table  = "hts.operations_channel_topic";
	private String QUERY_STARTOPICINFO_BY_TOPICID = "select * from "+table+" where id = ?";

	@Override
	public List<OpStarRecommendTopicInfo> getInfo(Integer topicId) {
		return	  getJdbcTemplate().query(QUERY_STARTOPICINFO_BY_TOPICID, 
				new Object[]{topicId}, 
				new RowMapper<OpStarRecommendTopicInfo>() {

			@Override
			public OpStarRecommendTopicInfo mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return buildOpStarRecommendTopicInfo(rs);
			}
		}
		);
	}

	public OpStarRecommendTopicInfo buildOpStarRecommendTopicInfo(ResultSet rs) throws SQLException{
		OpStarRecommendTopicInfo starRecommendTopicInfo = new OpStarRecommendTopicInfo();
		starRecommendTopicInfo.setTitle(rs.getString("title"));
		starRecommendTopicInfo.setFileName(rs.getString("file_name"));
		starRecommendTopicInfo.setHeadIntro(rs.getString("introduce_head"));
		starRecommendTopicInfo.setFootIntro(rs.getString("introduce_foot"));
		starRecommendTopicInfo.setStickerButton(rs.getString("sticker_button"));
		starRecommendTopicInfo.setShareButton(rs.getString("share_button"));
		starRecommendTopicInfo.setFoot(rs.getString("foot"));
		return starRecommendTopicInfo;
	}
}
