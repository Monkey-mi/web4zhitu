package com.hts.web.userinfo.dao;

import java.util.Map;

import com.hts.web.common.dao.BaseDao;
import com.hts.web.common.pojo.UserRemark;

/**
 * <p>
 * 用户备注数据访问接口
 * </p>
 * 
 * 创建时间：2014-12-15
 * @author lynch
 *
 */
public interface UserRemarkDao extends BaseDao {

	public Map<Integer, String> queryRemark(Integer userId, Integer[] remarkIds);
	
	public String queryRemark(Integer userId, Integer remarkId);
	
	public void saveRemark(UserRemark remark);
	
	public void updateRemark(UserRemark remark);
	
	public void deleteRemark(Integer userId, Integer remarkId);
	
}
