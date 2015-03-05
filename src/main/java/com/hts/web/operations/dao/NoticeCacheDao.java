package com.hts.web.operations.dao;

import com.hts.web.common.dao.BaseCacheDao;
import com.hts.web.common.pojo.OpNotice;

public interface NoticeCacheDao extends BaseCacheDao {

	/**
	 * 查询公告
	 * 
	 * @param phoneCode 手机平台
	 * @return
	 */
	public OpNotice queryNotice(Integer phoneCode);
}
