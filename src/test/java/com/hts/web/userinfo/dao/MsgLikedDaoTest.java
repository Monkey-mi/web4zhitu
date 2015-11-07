package com.hts.web.userinfo.dao;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.BaseTest;

/**
 * 喜欢消息数据访问接口
 * 
 * @author lynch　2015-11-05
 *
 */
public class MsgLikedDaoTest extends BaseTest {

	@Autowired
	private MsgLikedDao dao;
	
	@Test
	public void saveMsgTest() {
		dao.saveMsg(1, 10, 10, 10);
	}
	
	@Test
	public void queryMsgTest() {
		dao.queryMsg(10, 10);
		dao.queryMsg(100, 10, 10);
	}
	
}
