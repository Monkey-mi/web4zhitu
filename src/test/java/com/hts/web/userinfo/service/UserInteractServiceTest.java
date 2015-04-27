package com.hts.web.userinfo.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.BaseTest;
import com.hts.web.base.constant.OptResult;
import com.hts.web.common.pojo.PushStatus;
import com.hts.web.common.pojo.UserConcernDto;
import com.hts.web.common.util.Log;

/**
 * <p>
 * 用户互动子模块业务逻辑单元测试
 * </p>
 * 
 * 创建时间：2013-7-2
 * @author ztj
 *
 */
public class UserInteractServiceTest extends BaseTest {
	
	private static Logger logger = Logger.getLogger(UserInteractServiceTest.class);
	
	@Autowired
	private UserInteractService service;
	
	/**
	 * 保存关注
	 * @throws Exception 
	 */
	@Test
	public void testSaveConcern() throws Exception {
		PushStatus status = service.saveConcern(false, 486, 485);
		JSONObject jsObj = JSONObject.fromObject(status);
		logger.debug(jsObj.toString());
		service.cancelConcern(486, 485);
	}
	
	/**
	 * 批量保存关注
	 * @throws Exception 
	 */
	@Test
	public void testBatchSaveConcern() throws Exception {
		List<PushStatus> list = service.batchSaveConcern(true, 1716 , "1393,1381,759,801,748,116");
		JSONArray jsArray = JSONArray.fromObject(list);
		logger.debug(jsArray.toString());
	}
	
	/**
	 * 取消关注
	 * @throws Exception 
	 */
	@Test
	public void testCancelConcern() throws Exception {
//		service.cancelConcern(486, 485);
	}
	
	/**
	 * 批量取消关注
	 * @throws Exception 
	 */
	@Test
	public void testBatchCancelConcern() throws Exception {
		service.batchCancelConcern(448, "388,389");
	}
	
	/**
	 * 构建关注列表
	 * @throws Exception 
	 */
	@Test
	public void testBuildConcerns() throws Exception {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		service.buildConcerns(2136, 485,  0, 0, 1,10, 0, jsonMap);
		service.buildConcerns(2136, 485,  0, 277227, 1,10, 10, jsonMap);
		List<UserConcernDto> list = (List<UserConcernDto>)jsonMap.get(OptResult.JSON_KEY_CONCERNS);
		Log.debug("列表大小：" + list.size());
		for(UserConcernDto dto : list) {
			Log.debug(dto.getId());
		}
		
//		JSONObject jsObj = JSONObject.fromObject(jsonMap);
//		Log.debug(jsObj);
	}
	
	@Test
	public void testBuildConcernsByName() throws Exception {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		service.buildConcerns("楠", 485, 1000, 1, 10, jsonMap);
		service.buildConcerns("楠", 485, 0, 1, 10, jsonMap);
		logObj(jsonMap);
	};
	
	/**
	 * 构建粉丝列表
	 * @throws Exception
	 */
	@Test
	public void testBuildFollows() throws Exception {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		service.buildFollows(true, 109, 114,  0, 0, 1,10, jsonMap);
		JSONObject jsObj = JSONObject.fromObject(jsonMap);
		Log.debug(jsObj);
	}
	
	@Test
	public void testBuildUserSearchInfo() throws Exception {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		service.buildUserSearchInfo("天杰", 109, 0, 1, 10, jsonMap);
		logObj(jsonMap);
	}
	
	@Test
	public void testSaveConcernType() throws Exception {
		service.saveConcernType(418, 1);
	}
	
	@Test
	public void testCancelConcernType() throws Exception {
		service.cancelConcernType(418, 1);
	}
	
	@Test
	public void testBuildNotConcernInfo() throws Exception {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		String loginCode = "2097226533, 1843214120, 3207414353, 2116015591, 1654334371, 2077355295, 1834357313, 1637169175, 2217223393, 1687213153, 1875458053, 1995988043";
		service.buildNotConcern(527, loginCode, jsonMap);
		logObj(jsonMap);
	}
	
	@Test
	public void testBuildRegister() throws Exception {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		String loginCode = "aa@aa.aaa";
		StringBuilder builder = new StringBuilder(loginCode);
		for(int i = 0; i < 500; i++) {
			builder.append("," + loginCode+i);
		}
		service.buildRegister(527, builder.toString(), jsonMap);
		logObj(jsonMap);
	}
	
	@Test
	public void testBuildShieldUser() throws Exception {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		service.buildShieldUser(1649, 0, 1, 10, jsonMap);
//		service.buildShieldUser(1549, 100, 1, 10, jsonMap);
		logObj(jsonMap);
	}
	
	@Test
	public void testSaveShield() throws Exception {
		service.saveShield(485, 486);
	}
	
	@Test
	public void testDeleteShield() throws Exception {
		service.deleteShield(485, 486);
	}
	
	@Test
	public void testSaveReport() throws Exception {
		service.saveReport(485, 486);
	}
	
	@Test
	public void testUpdateRemark() throws Exception {
		service.updateRemark(527, 1557, "呵呵");
	}
	
	@Test
	public void testDeleteRemark() throws Exception {
		service.deleteRemark(527, 1557);
	}
	
	@Test
	public void buildNewFollowTest() throws Exception {
		logNumberList(logger, new NumberListAdapter() {

			@Override
			public void buildNumberList(Map<String, Object> jsonMap)
					throws Exception {
				service.buildNewFollow(true, 1549, 0, 1, 10, jsonMap);
//				service.buildNewFollow(true, 485, 200, 1, 10, jsonMap);
			}
		});
	}
	
	@Test
	public void updateNewFollowTest() throws Exception {
		service.updateNewFollow(485);
	}
	
	@Test
	public void buildIsMututalTest() throws Exception {
		logNumberList(logger, new NumberListAdapter() {

			@Override
			public void buildNumberList(Map<String, Object> jsonMap)
					throws Exception {
				service.buildIsMututal(2035, "1240", jsonMap);
			}
		});
	}
	
	@Test
	public void buildShieldIdTest() throws Exception {
		logNumberList(logger, new NumberListAdapter() {

			@Override
			public void buildNumberList(Map<String, Object> jsonMap)
					throws Exception {
				service.buildShieldId(2000, jsonMap);
			}
		});
	}
}
