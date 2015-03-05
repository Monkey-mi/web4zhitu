package com.hts.web.ztworld.dao;

import com.hts.web.common.dao.BaseDao;
import com.hts.web.common.pojo.HTWorldTypeWorld;

/**
 * <p>
 * 织图分类数据访问对象
 * </p>
 * 
 * 创建时间：2014-1-22
 * @author lynch
 *
 */
public interface HTWorldTypeDao extends BaseDao {

	/**
	 * 保存分类织图
	 * 
	 * @param typeWorld
	 */
	public void saveTypeWorld(HTWorldTypeWorld typeWorld);
}
