package com.hts.web.operations.dao;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.BaseTest;
import com.hts.web.common.pojo.OpMsgBulletin;
import com.hts.web.common.util.Log;

public class BulletinCacheDaoTest extends BaseTest {

	@Autowired
	private BulletinCacheDao dao;
	
	@Test
	public void queryBulletTest() {
		List<OpMsgBulletin> list = dao.queryBulletin();
		JSONArray array = JSONArray.fromObject(list);
		Log.debug(array);
	}
	
	@Test
	public void updateBulletinTest() {
		List<OpMsgBulletin> list = new ArrayList<OpMsgBulletin>();
		
		OpMsgBulletin msg1 = new OpMsgBulletin();
		msg1.setId(1);
		msg1.setBulletinType(1);
		msg1.setBulletinPath("http://imzhitu.qiniudn.com/op/activity/1433814281000.png");
		msg1.setLink("http://imzhitu.com/operations/2015021501.html");
		
		OpMsgBulletin msg2 = new OpMsgBulletin();
		msg2.setId(2);
		msg2.setBulletinType(2);
		msg2.setBulletinPath("http://imzhitu.qiniudn.com/op/activity/1433657248000.png");
		msg2.setLink("11834");
		
		OpMsgBulletin msg3 = new OpMsgBulletin();
		msg3.setId(3);
		msg3.setBulletinType(3);
		msg3.setBulletinPath("http://imzhitu.qiniudn.com/op/activity/1433484215000.jpg");
		msg3.setLink("自拍情结");
		
		list.add(msg1);
		list.add(msg2);
		list.add(msg3);
		
		dao.updateBulletin(list);
	}
}
