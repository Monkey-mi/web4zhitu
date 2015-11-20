package com.hts.web.ztworld.dao;

import com.hts.web.common.dao.BaseDao;
import com.hts.web.common.pojo.HTWorldComment;

/**
 * 屏蔽评论数据访问接口
 * 
 * @author lynch　2015-11-20
 *
 */
public interface CommentShieldDao extends BaseDao {

	/**
	 * 保存评论删除数据
	 * 
	 * @param comm
	 * @author lynch 2015-11-20
	 */
	public void saveComment(HTWorldComment	comm);
	
}
