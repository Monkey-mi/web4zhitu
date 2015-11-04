package com.hts.web.ztworld.dao;

import com.hts.web.common.dao.BaseCacheDao;
import com.hts.web.common.pojo.HTWorldComment;

/**
 * <p>
 * 评论广播缓存数据访问接口
 * </p>
 * 
 * @author lynch　2015-11-03
 *
 */
public interface CommentBroadcastCacheDao extends BaseCacheDao {

	/**
	 * 保存评论
	 * 
	 * @param comment
	 */
	public void saveComment(HTWorldComment comment);

	
	/**
	 * 评论出栈
	 * 
	 * @return　队列为空时返回null
	 */
	public HTWorldComment popComment();
	
	
}
