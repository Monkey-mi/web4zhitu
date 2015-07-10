package com.hts.web.userinfo.dao;

import com.hts.web.common.dao.BaseDao;

/**
 * 管理员账号信息数据访问接口
 * 
 * @author lynch
 *
 */
public interface UserAdminDao extends BaseDao {

	public Boolean queryShieldCommentPermission(Integer userId);
}
