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
	public void buildUnreadMsgCount() throws Exception {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		service.buildUnreadSysMsgCount(1549, jsonMap);
		logObj(jsonMap);
	}
	
	@Test
	public void testBuildSysMsg() throws Exception {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		service.buildSysMsg(4, 0, 1, 10, false, false, true, jsonMap);
		logObj(jsonMap);
	}
	
	@Test
	public void testSaveUserMsg() throws Exception {
		service.saveUserMsg(400, 114, "怎么");
	}
	
	@Test
	public void testBuildUserMsg() throws Exception {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		service.buildUserMsg(527, 114, 0, 1, 10, jsonMap);
		service.buildUserMsg(527, 114, 1000, 1, 10, jsonMap);
		logObj(jsonMap);
	}
	
	@Test
	public void saveUserWelcomeMsgTest() throws Exception {
		service.saveUserWelcomeMsg(390);
	}
	

	@Test
	public void delUserMsgTest() throws Exception {
		service.delUserMsg(1500, 390, 0);
	}
	
	@Test
	public void testGetMsgStatus() throws Exception {
		UserMsgStatus status = service.getMsgStatus(1000, 1611);
		logObj(status);
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
	
	@Test
	public void saveMsgsTest() throws Exception {
//		service.saveAtMsgs(485, Tag.AT_TYPE_WORLD, 28226,
//				28226, "哈哈哈你好@'''lynch @tom", "1939,1595", "'''lynch,tom");
	}
	
	@Test
	public void buildAtMsgTest() throws Exception {
		logNumberList(log, new NumberListAdapter() {
			
			@Override
			public void buildNumberList(Map<String, Object> jsonMap) throws Exception {
				service.buildAtMsg(1939, 100, 1, 10, jsonMap);
			}
		});
	}
	
	@Test
	public void buildCommentMsgTest() throws Exception {
		logNumberList(log, new NumberListAdapter() {
			
			@Override
			public void buildNumberList(Map<String, Object> jsonMap) throws Exception {
				/*service.buildCommentMsg(485, 0, 1, 10, jsonMap);*/
			}
		});
	}
	
}
