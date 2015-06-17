package com.hts.web.operations.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.BaseTest;
import com.hts.web.base.constant.Tag;
import com.hts.web.common.pojo.OpMsgStartPage;

public class StartPageCacheDaoTest extends BaseTest {

	@Autowired
	private StartPageCacheDao dao;
	
	@Test
	public void testUpdateStartPage() throws Exception{
		Date beginDate = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date endDate = df.parse("2015-06-20 00:00:00");
		
//		OpMsgStartPage p1 = new OpMsgStartPage();
//		p1.setBeginDate(beginDate);
//		p1.setEndDate(endDate);
//		p1.setId(1);
//		p1.setLinkPath("http://imzhitu.qiniudn.com/op/notice/2015061602.jpg");
//		p1.setLinkType(Tag.BULLETIN_PAGE);
//		p1.setLink("http://imzhitu.com/operations/2015021501.html");
//		p1.setLastModified(new Date().getTime());
//		
//		OpMsgStartPage p2 = new OpMsgStartPage();
//		p2.setBeginDate(beginDate);
//		p2.setEndDate(endDate);
//		p2.setId(2);
//		p2.setLinkPath("http://imzhitu.qiniudn.com/op/notice/2015061603.jpg");
//		p2.setLinkType(Tag.BULLETIN_DEFAULT);
//		p2.setLink("");
//		p2.setLastModified(new Date().getTime());
//		
//		OpMsgStartPage p3 = new OpMsgStartPage();
//		p3.setBeginDate(beginDate);
//		p3.setEndDate(endDate);
//		p3.setId(3);
//		p3.setLinkPath("http://imzhitu.qiniudn.com/op/notice/2015061604.jpg");
//		p3.setLinkType(Tag.BULLETIN_CHANNEL);
//		p3.setLink("11834");
//		p3.setLastModified(new Date().getTime());
		
		OpMsgStartPage p4 = new OpMsgStartPage();
		p4.setBeginDate(beginDate);
		p4.setEndDate(endDate);
		p4.setId(4);
		p4.setLinkPath("http://imzhitu.qiniudn.com/op/notice/2015061604.jpg");
		p4.setLinkType(Tag.BULLETIN_CHANNEL);
		p4.setLink("11834");
		p4.setLastModified(new Date().getTime());
		
		List<OpMsgStartPage> plist = new ArrayList<OpMsgStartPage>();
//		plist.add(p1);
//		plist.add(p2);
//		plist.add(p3);
		plist.add(p4);
		
		dao.updateStartPage(plist);
		
//		service.updateStartPageCache(
//				"http://imzhitu.qiniudn.com/op/notice/2015061001.png",
//				3,"自拍情结",
//				beginDate , endDate, 10);
	}
}
