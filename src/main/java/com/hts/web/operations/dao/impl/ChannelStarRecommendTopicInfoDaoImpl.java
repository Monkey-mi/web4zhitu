package com.hts.web.operations.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import com.hts.web.common.dao.impl.BaseDaoImpl;
import com.hts.web.common.pojo.OpStarRecommendPastTopicInfo;
import com.hts.web.common.pojo.OpStarRecommendTopicInfo;
import com.hts.web.operations.dao.ChannelStarRecommendTopicInfoDao;


@Service
public class ChannelStarRecommendTopicInfoDaoImpl extends BaseDaoImpl implements ChannelStarRecommendTopicInfoDao {
	
	private static String table  = "hts.operations_channel_topic";
	private String QUERY_STARTOPICINFO_BY_TOPICID = "select * from "+table+" where id = ?";
	private String QUERY_PAST_TOPICINFO_BY_TOPICID = "SELECT * FROM " +table+ " WHERE id < ? and valid=1 order by id desc limit 3; ";

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
		starRecommendTopicInfo.setTopicId(rs.getInt("id"));
		starRecommendTopicInfo.setTitle(rs.getString("title"));
		starRecommendTopicInfo.setTopicType(rs.getInt("topic_type"));
		starRecommendTopicInfo.setIsWorld(rs.getInt("is_world"));
		starRecommendTopicInfo.setShareBanner(rs.getString("share_banner"));
		starRecommendTopicInfo.setBannerPic(rs.getString("banner_pic"));
		starRecommendTopicInfo.setHeadIntro(rs.getString("introduce_head"));
		starRecommendTopicInfo.setFootIntro(rs.getString("introduce_foot"));
		starRecommendTopicInfo.setStickerButton(rs.getString("sticker_button"));
		starRecommendTopicInfo.setShareButton(rs.getString("share_button"));
		return starRecommendTopicInfo;
	}

	@Override
	public List<OpStarRecommendPastTopicInfo> getPastTopicInfo(Integer topicId) {
		return	  getJdbcTemplate().query(QUERY_PAST_TOPICINFO_BY_TOPICID, 
				new Object[]{topicId}, 
				new RowMapper<OpStarRecommendPastTopicInfo>() {

			@Override
			public OpStarRecommendPastTopicInfo mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return buildOpStarRecommendPastTopicInfo(rs);
			}
		}
		);
	}
	
	public OpStarRecommendPastTopicInfo buildOpStarRecommendPastTopicInfo(ResultSet rs) throws SQLException{
		OpStarRecommendPastTopicInfo starRecommendPastTopicInfo = new OpStarRecommendPastTopicInfo();
		starRecommendPastTopicInfo.setTopicId(rs.getInt("id"));
		starRecommendPastTopicInfo.setTitle(rs.getString("title"));
		starRecommendPastTopicInfo.setTopicType(rs.getInt("topic_type"));
		starRecommendPastTopicInfo.setIsWorld(rs.getInt("is_world"));
		starRecommendPastTopicInfo.setBannerPic(rs.getString("banner_pic"));
		starRecommendPastTopicInfo.setHeadIntro(rs.getString("introduce_head"));
		starRecommendPastTopicInfo.setFootIntro(rs.getString("introduce_foot"));
		starRecommendPastTopicInfo.setStickerButton(rs.getString("sticker_button"));
		starRecommendPastTopicInfo.setShareButton(rs.getString("share_button"));
		return starRecommendPastTopicInfo;
	}
}
