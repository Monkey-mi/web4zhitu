package com.hts.web.ztworld.dao;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.BaseTest;
import com.hts.web.common.pojo.HTWorldComment;

public class CommentBroadcastCacheDaoTest extends BaseTest {

	@Autowired
	private CommentBroadcastCacheDao dao;
	
	@Test
	public void popCommentTest() throws Exception {
		HTWorldComment comment = dao.popComment();
		logObj(comment);
	}
	
}
