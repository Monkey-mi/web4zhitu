package com.hts.web.operations.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.hts.web.common.dao.impl.BaseDaoImpl;
import com.hts.web.common.pojo.OpStarRecommendDto;
import com.hts.web.operations.dao.OpStarRecommendDao;

@Repository("HTSOpStarRecommendDao")
public class OpStarRecommendDaoImpl extends BaseDaoImpl implements OpStarRecommendDao{
	
	/**
	 * 推荐用户信息 
	 */
	private static final String U_INFO = " u.id as user_id,u.platform_sign, "
			+ "u.platform_verify,u.platform_reason,u.user_name,u.user_avatar,u.user_avatar_l," 
			+ "u.signature,u.user_label,"
			+ "u.shield,u.star,u.trust,u.activity ";

	/**
	 * 查询置顶的达人推荐用户
	 */
	private static String QUERY_TOP_STAR_RECOMMEND =  "select " + U_INFO + " from "
			+ "(select user_id from hts.operations_star_recommend osr where osr.valid=1 and osr.top=1 order by osr.activity limit ?,?) t "
			+ " left join hts.user_info u on t.user_id=u.id";
	
	/**
	 * 查询置顶的达人推荐用户 by maxId
	 */
	private static String QUERY_TOP_STAR_RECOMMEND_BY_MAXID =  "select " + U_INFO + " from "
			+ "(select user_id from hts.operations_star_recommend osr where osr.valid=1 and osr.top=1 and osr.id<? order by osr.activity limit ?,?) t "
			+ " left join hts.user_info u on t.user_id=u.id";
	
	/**
	 * 查询非置顶的达人推荐用户
	 */
	private static String QUERY_STAR_RECOMMEND =  "select " + U_INFO + " from "
			+ "(select user_id from hts.operations_star_recommend osr where osr.valid=1 and osr.top=0 order by osr.activity limit ?,?) t "
			+ " left join hts.user_info u on t.user_id=u.id";
	
	/**
	 * 查询非置顶的达人推荐用户 by maxId
	 */
	private static String QUERY_STAR_RECOMMEND_BY_MAXID =  "select " + U_INFO + " from "
			+ "(select user_id from hts.operations_star_recommend osr where osr.valid=1 and osr.top=0 and osr.id<? order by osr.activity limit ?,?) t "
			+ " left join hts.user_info u on t.user_id=u.id";
	
	/**
	 * 更新达人推荐用户的activity值
	 */
	public static String UPDATE_STAR_RECOMMEND_ACTIVITY = "update hts.operations_star_recommend set activity=activity+? where user_id=?";
	
	public static String QUERY_TOP_STAR_RECOMMEND_TOTAL_COUNT = "select count(*) from hts.operations_star_recommend osr where top=1";
	
	@Override
	public List<OpStarRecommendDto> queryStarRecommend(Integer maxId,
			Integer start,Integer limit) {
		// TODO Auto-generated method stub
		List<OpStarRecommendDto> list = null;
		long topStarRecommendTotal = getJdbcTemplate().queryForLong(QUERY_TOP_STAR_RECOMMEND_TOTAL_COUNT);
		int first = start*limit;
		
		RowMapper<OpStarRecommendDto> rowMapper = new RowMapper<OpStarRecommendDto>(){
			@Override
			public OpStarRecommendDto mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				
				return buildOpStarRecommendDto(rs);
			}
			
		};
		
		if(first < topStarRecommendTotal){//需要查询置顶的
			String sql = null;
			Object[] agrs = null;
			if(maxId != null && maxId > 0){
				sql = QUERY_TOP_STAR_RECOMMEND_BY_MAXID;
				agrs = new Object[]{maxId,start,limit};
			}else{
				sql = QUERY_TOP_STAR_RECOMMEND;
				agrs = new Object[]{start,limit};
			}
			
			//置顶的
			list = getMasterJdbcTemplate().query(sql, agrs, rowMapper);
			
			if(list.size() < limit){//置顶的不够，需要查询非置顶的
				List<OpStarRecommendDto>tmpList = getMasterJdbcTemplate().query(QUERY_STAR_RECOMMEND, new Object[]{0,limit-list.size()},rowMapper);
				list.addAll(tmpList);
			}
			
			
		}else{//不需要查询置顶的
			String sql = null;
			Object[] agrs = null;
			if(maxId != null && maxId > 0){
				sql = QUERY_STAR_RECOMMEND_BY_MAXID;
				agrs = new Object[]{maxId,start,limit};
			}else{
				sql = QUERY_STAR_RECOMMEND;
				agrs = new Object[]{start,limit};
			}
			list = getMasterJdbcTemplate().query(sql, agrs, rowMapper);
		}
		
		return list;
	}

	@Override
	public void updateStarRecommendActivity(Integer userId, Integer activity) {
		// TODO Auto-generated method stub
		getMasterJdbcTemplate().update(UPDATE_STAR_RECOMMEND_ACTIVITY, activity,userId);
	}
	
	
	/**
	 * 构建 OpStarRecommendDto
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	private OpStarRecommendDto buildOpStarRecommendDto(ResultSet rs) throws SQLException{
		OpStarRecommendDto dto = new OpStarRecommendDto();
		dto.setId(rs.getInt("user_id"));
		dto.setActivity(rs.getInt("activity"));
		dto.setPlatformReason(rs.getString("platform_reason"));
		dto.setStar(rs.getInt("star"));
		dto.setPlatformSign(rs.getString("platform_sign"));
		dto.setSignature(rs.getString("signature"));
		dto.setUserAvatar(rs.getString("user_avatar"));
		dto.setUserAvatarL(rs.getString("user_avatar_l"));
		dto.setUserLabel(rs.getString("user_label"));
		dto.setUserName(rs.getString("user_name"));
		dto.setPlatformVerify(rs.getInt("platform_verify"));
		return dto;
	}

}
