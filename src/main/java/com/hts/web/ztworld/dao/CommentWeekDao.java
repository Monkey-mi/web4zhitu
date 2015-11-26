package com.hts.web.ztworld.dao;

import com.hts.web.common.dao.BaseDao;
import com.hts.web.common.pojo.HTWorldComment;

/**
 * <p>
 * 每周最新评论数据访问接口
 * </p>
 * 
 * @author lynch　2015-11-20
 *
 */
public interface CommentWeekDao extends BaseDao {

	
	/**
	 * 保存评论
	 * 
	 * @param worldComment
	 */
	public void saveComment(HTWorldComment worldComment);
	
	/**
	 * 删除评论
	 * 
	 * @param id
	 * @param worldId
	 */
	public void delComment(Integer id, Integer worldId);
	
}
