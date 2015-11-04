package com.hts.web.ztworld.dao.impl;

import org.springframework.stereotype.Repository;

import com.hts.web.base.constant.CacheKeies;
import com.hts.web.common.dao.impl.BaseCacheDaoImpl;
import com.hts.web.common.pojo.HTWorldComment;
import com.hts.web.ztworld.dao.CommentBroadcastCacheDao;

@Repository("HTSCommentBroadcastCacheDao")
public class CommentBroadcastCacheDaoImpl extends BaseCacheDaoImpl<HTWorldComment> 
	implements CommentBroadcastCacheDao {

	@Override
	public void saveComment(HTWorldComment comment) {
		getRedisTemplate().boundListOps(
				CacheKeies.ZTWORLD_COMMENT_BROADCAST).rightPush(comment);
	}

	@Override
	public HTWorldComment popComment() {
		return getRedisTemplate().boundListOps(
				CacheKeies.ZTWORLD_COMMENT_BROADCAST).leftPop();
	}

}
