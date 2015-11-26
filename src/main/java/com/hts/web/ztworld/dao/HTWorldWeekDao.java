package com.hts.web.ztworld.dao;

import com.hts.web.common.dao.BaseDao;
import com.hts.web.common.pojo.HTWorld;

/**
 * 每周最新织图数据访问接口
 * 
 * @author lynch　2015-11-20
 *
 */
public interface HTWorldWeekDao extends BaseDao{

	/**
	 * 保存世界
	 * 
	 * @param htworld
	 */
	public void saveWorld(HTWorld htworld);
	
}
