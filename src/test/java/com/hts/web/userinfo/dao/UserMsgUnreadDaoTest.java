package com.hts.web.userinfo.dao;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.BaseTest;
import com.hts.web.userinfo.dao.MsgUnreadDao.UnreadType;

public class UserMsgUnreadDaoTest extends BaseTest {
	
	@Autowired
	private MsgUnreadDao dao;

	@Test
	public void test() throws Exception {
//		dao.saveUnRead(3);
		dao.queryCount(3);
//		dao.updateCount(3, 100, UnreadType.AT);
		dao.clearCount(3, 10, UnreadType.AT);
		dao.queryReadId(3, UnreadType.AT);
		dao.queryCount(3);
	}

}
