package com.hts.web.userinfo.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.BaseTest;
import com.hts.web.base.constant.OptResult;
import com.hts.web.base.constant.Tag;
import com.hts.web.common.pojo.HTWorldInteract;
import com.hts.web.common.pojo.UserMsgStatus;
import com.hts.web.common.util.Log;

/**
 * <p>
 * 用户私信业务逻辑访问接口单元测试
 * </p>
 * 
 * 创建时间：2013-11-30
 * @author ztj
 *
 */
public class UserMsgServiceTest extends BaseTest {
	
	private static Logger log = Logger.getLogger(UserMsgServiceTest.class);
	
	@Autowired
	private UserMsgService service;

	@Test
	public void testBuildCommentMsg() throws Exception {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		service.buildCommentMsg(109, 0, 0, 1, 10, jsonMap);
		logObj(jsonMap);
	}
	
	@Test
	public void testBuildLikedMsg() throws Exception {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		service.buildLikedMsg(109, 0, 0, 1, 10, jsonMap);
		logObj(jsonMap);
	}
	
	@Test
	public void testBuildInteractMsg() throws Exception {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		service.buildInteractMsg(1278, "2014-07-09 12:00:00", 1, 10, jsonMap);
		service.buildInteractMsg(1278, null, 1, 10, jsonMap);
		List<HTWorldInteract> list = (List<HTWorldInteract>) jsonMap.get(OptResult.JSON_KEY_INTERACT);
		for(HTWorldInteract in : list) {
			Log.debug(in.getUserInfo().getVerifyId() + " : " + in.getUserInfo().getVerifyName() + " : " + in.getUserInfo().getVerifyIcon());
		}
//		logObj(jsonMap);
		
	}
	
	@Test
	public void buildUnreadMsgCount() throws Exception {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		service.buildUnreadSysMsgCount(485, jsonMap);
		logObj(jsonMap);
	}
	
	@Test
	public void testBuildSysMsg() throws Exception {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		service.buildSysMsg(801, 0, 1, 10, false, false, true, jsonMap);
		logObj(jsonMap);
	}
	
	
	@Test
	public void testBuildUnreadMsgCount() throws Exception {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		service.buildUnreadSysMsgCountV2(527, jsonMap);
		logObj(jsonMap);
	}
	
	@Test
	public void testSaveUserMsg() throws Exception {
		service.saveUserMsg(400, 114, "怎么", Tag.USER_MSG_NORMAL);
		Thread.sleep(10000);
	}
	
	@Test
	public void testBuildUserMsgCount() throws Exception {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		service.buildUserConcernMsgIndex(116, 0, 1, 10, jsonMap);
		logObj(jsonMap);
	}
	
	@Test
	public void testBuildUserUnConcernMsg() throws Exception {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		service.buildUserUnConcernMsgIndex(485, 0, 1, 10, jsonMap);
		logObj(jsonMap);
	}
	
	@Test
	public void testBuildUserMsg() throws Exception {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		service.buildUserMsg(527, 114, 0, 1, 10, jsonMap);
		logObj(jsonMap);
	}
	
	@Test
	public void testUpdateChatValid() throws Exception {
		service.updateChatValid(660, 485, 410);
	}
	
	@Test
	public void buildUnReadRecipientMsg() throws Exception {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		service.buildReceiveMsg(660, 440, 550, jsonMap);
		logObj(jsonMap);
	}
	
	@Test
	public void testSaveSquareRuleMsg() throws Exception {
		service.saveSquareRuleMsg(390);
	}
	
	@Test
	public void testGetMsgStatus() throws Exception {
		UserMsgStatus status = service.getMsgStatus(1000, 1611);
		logObj(status);
	}
	
	@Test
	public void testBuildUserLikedMsg2() throws Exception {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		service.buildLikedMsg2(0, 1611, 1549, 1, 10, jsonMap);
//		service.buildLikedMsg(2481, 485, 116, 1, 10, jsonMap);
		logObj(jsonMap);
	}
	
	@Test
	public void buildILikeOtherMsgTest() throws Exception {
		logNumberList(log, new NumberListAdapter() {

			@Override
			public void buildNumberList(Map<String, Object> jsonMap)
					throws Exception {
				service.buildILikeOtherMsg(0, 1611, 1549, 1, 10, jsonMap);
			}
		});
	}
	
	@Test
	public void buildOtherLikeMeTest() throws Exception {
		logNumberList(log, new NumberListAdapter() {

			@Override
			public void buildNumberList(Map<String, Object> jsonMap)
					throws Exception {
				service.buildOtherLikeMeMsg(0, 1611, 1549, 1, 10, jsonMap);
			}
		});
	}
	
	@Test
	public void testBuilUThumbnail() throws Exception {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		service.buildThumbnail("1595,1591,1549 ", "", 1611, null, null, jsonMap);
		logObj(jsonMap);
	}
	
	@Test
	public void buildLikeMeTest() throws Exception {
		logNumberList(log, new NumberListAdapter() {

			@Override
			public void buildNumberList(Map<String, Object> jsonMap)
					throws Exception {
//				service.buildLikeMeMsg(1000, 485, 1, 10, jsonMap);
//				service.buildLikeMeMsg(0, 485, 1, 10, jsonMap);
				service.buildLikeMeMsg(0, 485, 10, jsonMap);
//				service.buildLikeMeMsg(1000, 485, 10, jsonMap);
			}
		});
	}
	
	@Test
	public void buildLikeMeWithoutGroupTest() throws Exception {
		logNumberList(log, new NumberListAdapter() {

			@Override
			public void buildNumberList(Map<String, Object> jsonMap)
					throws Exception {
				service.buildLikeMeMsgWithoutGroup(0, 485, 10, jsonMap);
				service.buildLikeMeMsgWithoutGroup(1000, 485, 10, jsonMap);
			}
		});
	}
}
