package com.hts.web.userinfo.dao;

import com.hts.web.common.dao.BaseDao;

/**
 * 用户标签数据访问接口
 * 
 * @author lynch
 *
 */
public interface LabelDao extends BaseDao {

	public Integer queryIdByName(String labelName);
}
