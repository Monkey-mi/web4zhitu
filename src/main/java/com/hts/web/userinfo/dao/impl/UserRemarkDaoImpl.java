package com.hts.web.userinfo.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import com.hts.web.base.database.HTS;
import com.hts.web.base.database.SQLUtil;
import com.hts.web.common.dao.impl.BaseDaoImpl;
import com.hts.web.common.pojo.UserRemark;
import com.hts.web.userinfo.dao.UserRemarkDao;

@Repository("HTSUserRemarkDao")
public class UserRemarkDaoImpl extends BaseDaoImpl implements UserRemarkDao {

	private static String table = HTS.USER_REMARK;
	
	/**
	 * 查询备注id和名字列表
	 */
	private static final String QUERY_REMARK_ID_AND_NAME = "select remark_id, remark from " + table
			+ " where user_id=? and remark_id in ";
	
	/**
	 * 查询备注名字
	 */
	private static final String QUERY_REMARK_NAME = "select remark from " + table
			+ " where user_id=? and remark_id=?";
	
	/**
	 * 保存备注
	 */
	private static final String SAVE_REMARK = "insert into " + table 
			+ " (user_id, remark_id, remark) values (?,?,?)";
	
	/**
	 * 更新备注
	 */
	private static final String UPDATE_REMARK = "update " + table
			+ " set remark=? where user_id=? and remark_id=?";

	/**
	 * 删除备注
	 */
	private static final String DELETE_REMARK = "delete from " + table
			+ " where user_id=? and remark_id=?";
	
	@Override
	public Map<Integer, String> queryRemark(Integer userId, Integer[] remarkIds) {
		String inSelection = SQLUtil.buildInSelection(remarkIds);
		String sql = QUERY_REMARK_ID_AND_NAME + inSelection;
		Object[] args = SQLUtil.getArgsByInCondition(remarkIds, new Object[]{userId}, true);
		final Map<Integer, String> map = new HashMap<Integer, String>();
		getJdbcTemplate().query(sql, args, new RowCallbackHandler() {

			@Override
			public void processRow(ResultSet rs) throws SQLException {
				map.put(rs.getInt("remark_id"), 
						rs.getString("remark"));
			}
		});
		return map;
	}
	
	@Override
	public String queryRemark(Integer userId, Integer remarkId) {
		return queryForObjectWithNULL(QUERY_REMARK_NAME, 
				new Object[]{userId, remarkId}, 
				String.class);
	}

	@Override
	public void saveRemark(UserRemark remark) {
		getJdbcTemplate().update(SAVE_REMARK, new Object[]{
			remark.getUserId(),
			remark.getRemarkId(),
			remark.getRemark()
		});
	}

	@Override
	public void updateRemark(UserRemark remark) {
		getJdbcTemplate().update(UPDATE_REMARK, new Object[]{
			remark.getRemark(),
			remark.getUserId(),
			remark.getRemarkId()
		});
	}

	@Override
	public void deleteRemark(Integer userId, Integer remarkId) {
		getJdbcTemplate().update(DELETE_REMARK, new Object[]{
			userId, remarkId
		});
	}

}
