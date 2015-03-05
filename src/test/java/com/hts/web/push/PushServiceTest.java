package com.hts.web.push;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.BaseTest;
import com.hts.web.base.constant.Tag;
import com.hts.web.common.pojo.UserPushInfo;
import com.hts.web.push.service.PushService;
import com.hts.web.push.service.impl.PushServiceImpl.PushFailedCallback;

/**
 * <p>
 * android推送服务业务逻辑单元测试
 * </p>
 * 
 * 创建时间：2013-6-236
 * @author ztj
 *
 */
public class PushServiceTest extends BaseTest {

	@Autowired
	private PushService service;
	
	@Test
	public void testPushAppMessage() throws Exception {
//		service.pushAppMessage("app推送内容测试", Tag.PUSH_ACTION_WORLD_MSG, String.valueOf(12046), null);
//		Thread.sleep(5000);
	}
	
//	@Test
	public void testPushListMessage() throws Exception {
		List<UserPushInfo> list = new ArrayList<UserPushInfo>();
		UserPushInfo info1 = new UserPushInfo(
				801, "", 1, 0,
				"c24675ea6ce6984befde3b322732bfe3",
				2.0905f, 1, 1, 1, 1, 1, 1, 1, 1);
		
		UserPushInfo info2 = new UserPushInfo(
				116, "", 1, 0,
				"1dbbb87f43523fb98db5932b02753f53",
				2.0905f, 1, 1, 1, 1, 1, 1, 1, 1);
		list.add(info1);
//		list.add(info2);
		service.pushListMessage("批量推送内容测试", Tag.PUSH_ACTION_SYS_MSG, String.valueOf(12046),  list, new PushFailedCallback() {

			@Override
			public void onPushFailed(Exception e) {
				
			}
			
		});
//		Thread.sleep(10000);
	}
	
	@Test
	public void testPushLiked() throws Exception {
		UserPushInfo info2 = new UserPushInfo(
				485, "", 1, 0,
				"c66202b9e888a76b66fe63573a396121",
				2.0981f, 1, 1, 1, 1, 1, 1, 1, 1);
		service.pushLiked(1, 486, 1111, info2, Tag.FALSE);
//		Thread.sleep(5000);
	}

	@Test
	public void testPushComment() throws Exception {
		UserPushInfo info2 = new UserPushInfo(
				485, "", 1, 0,
				"c66202b9e888a76b66fe63573a396121",
				2.0971f, 1, 1, 1, 1, 1, 1, 1, 1);
		service.pushComment(1, 486, 1999, "评论呵呵呵", info2, Tag.FALSE);
//		Thread.sleep(5000);
	}
	
	@Test
	public void testPushReply() throws Exception {
		UserPushInfo info2 = new UserPushInfo(
				485, "", 1, 0,
				"c66202b9e888a76b66fe63573a396121",
				2.0981f, 1, 1, 1, 1, 1, 1, 1, 1);
		service.pushReply(111, 486, 1999, "回复呵呵呵", info2, Tag.FALSE);
//		Thread.sleep(5000);
	}
	
	@Test
	public void testPushConcern() throws Exception {
		UserPushInfo info2 = new UserPushInfo(
				485, "", 1, 0,
				"c66202b9e888a76b66fe63573a396121",
				2.0981f, 1, 1, 1, 1, 1, 1, 1, 1);
		service.pushConcern(1, 486, 485, info2, Tag.FALSE);
//		Thread.sleep(5000);
	}
	
	@Test
	public void testPushUserMessage() throws Exception {
		UserPushInfo info2 = new UserPushInfo(
				485, "", 1, 0,
				"c66202b9e888a76b66fe63573a396121",
				2.0980f, 1, 1, 1, 1, 1, 1, 1, 1);
		service.pushMiShuMessage(486, "消息", info2, new PushFailedCallback() {
			
			@Override
			public void onPushFailed(Exception e) {
			}
		});
//		Thread.sleep(5000);
	}
	
	@Test
	public void testPushSysMessage() throws Exception {
		UserPushInfo info2 = new UserPushInfo(
				485, "", 1, 0,
				"c66202b9e888a76b66fe63573a396121",
				2.0981f, 1, 1, 1, 1, 1, 1, 1, 1);
		service.pushSysMessage("呵呵呵", 123, "哈哈哈", info2, Tag.USER_MSG_SYS, new PushFailedCallback() {
			
			@Override
			public void onPushFailed(Exception e) {
				// TODO Auto-generated method stub
				
			}
		});
//		Thread.sleep(5000);
	}
	
//	@Test
//	public void testThread() throws Exception {
//		service.threadLog(1,1,1,1);
////		service.threadLog(2,2,2,2);
////		service.threadLog(3,3,3,3);
////		service.threadLog(4,4,4,4);
////		service.threadLog(5,5,5,5);
////		service.threadLog(6,6,6,6);
////		service.threadLog(7,7,7,7);
////		service.threadLog(8,8,8,8);
////		service.threadLog(9,9,9,9);
////		service.threadLog(10,10,10,10);
////		service.threadLog(11,11,11,11);
////		service.threadLog(12,12,12,12);
////		service.threadLog(13,13,13,13);
////		service.threadLog(14,14,14,14);
////		for(int i = 0; i < 30; i++) {
////			service.threadLog(i, i, i, i);
////		}
//	}
}
