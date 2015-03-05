package com.hts.web.userinfo.dao.impl;

import org.springframework.stereotype.Repository;

import com.hts.web.base.database.HTS;
import com.hts.web.base.database.SQLUtil;
import com.hts.web.common.dao.impl.BaseDaoImpl;
import com.hts.web.userinfo.dao.UserLabelDao;

/**
 * <p>
 * 用户标签数据访问对象
 * </p>
 * 
 * 创建时间：2014-1-11
 * 
 * @author ztj
 * 
 */
@Repository("HTSUserLabelDao")
public class UserLabelDaoImpl extends BaseDaoImpl implements UserLabelDao {

	private static String table = HTS.USER_LABEL;
	
	/** 保存用户-标签关联 */
	private static final String SAVE_USER_LABEL = "insert into " + HTS.USER_INFO_LABEL
			+ " (user_id, label_id) values (?,?)";

	
	/** 根据标签ids删除用户标签 */
	private static final String DELETE_USER_LABEL_BY_LABELIDS = "delete from " + HTS.USER_INFO_LABEL 
			+ " where user_id=? and label_id in";
	
	/** 删除用户标签 */
	private static final String DELETE_USER_LABEL = "delete from " + HTS.USER_INFO_LABEL
			+ " where user_id=?";
	
	
	@Override
	public void deleteUserLabelByLabelIds(Integer userId, Integer[] labelIds) {
		String inSelection = SQLUtil.buildInSelection(labelIds);
		String sql = DELETE_USER_LABEL_BY_LABELIDS + inSelection;
		Object[] args = SQLUtil.getArgsByInCondition(labelIds, new Object[]{userId}, true);
		getJdbcTemplate().update(sql, args);
	}

	@Override
	public void deleteUserLabel(Integer userId) {
		getJdbcTemplate().update(DELETE_USER_LABEL, userId);
	}

	@Override
	public void saveUserLabel(Integer userId, Integer labelId) {
		getJdbcTemplate().update(SAVE_USER_LABEL, new Object[]{userId, labelId});
	}

}
