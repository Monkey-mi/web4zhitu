package com.hts.web.operations.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.BaseTest;
import com.hts.web.base.constant.OptResult;
import com.hts.web.common.pojo.OpWorldTypeDto;
import com.hts.web.common.util.Log;

/**
 * <p>
 * 织图运营业务逻辑访问接口单元测试
 * </p>
 * 
 * 创建时间：2013-8-8
 * @author ztj
 *
 */
public class ZTWorldOperationsServiceTest extends BaseTest {
	
	@Autowired
	private ZTWorldOperationsService service;

	@Test
	public void testBuildSquarePush2() throws Exception {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		service.buildSquarePush(false, 0, 99999, 1, 10, jsonMap);
		logObj(jsonMap);
	}
	
	@Test
	public void testBuildRandomSquarePush() throws Exception {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		service.buildSquarePush(false, 10, jsonMap);
		logObj(jsonMap);
	}
	
	@Test
	public void testBuildSquarePush() throws Exception {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		service.buildSquarePush(100, 1, 10, 1, 527, jsonMap);
		logObj(jsonMap);
	}
	
	@Test
	public void testGetSquarePushs() throws Exception {
		String[] labels = new String[]{"学生", "自拍", "朋友", "科技"};
		List<OpWorldTypeDto> list = service.getSquarePushs(0, 0, 1, 10, labels);
		JSONArray array = JSONArray.fromObject(list);
		Log.debug(array);
	}
	
	@Test
	public void testGetMaxSquarePushId() throws Exception {
		int id = service.getMaxSquarePushId();
		Log.debug("最大广场id : " + id);
	}
	
	@Test
	public void testBuildLatestSquarePushTopic() throws Exception {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		service.buildLatestSquarePushTopic(5, jsonMap);
		logObj(jsonMap);
	}
	
	@Test
	public void testBuildSquarePushLabelIndex() throws Exception {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		service.buildSquarePushLabelIndex(18,6,jsonMap);
		logObj(jsonMap);
	}
	
	@Test
	public void testbuildRandomLabelPush() throws Exception {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		service.buildRandomLabelPush(3,381, jsonMap);
		logObj(jsonMap);
	}
	
	@Test
	public void testBuildActivity() throws Exception {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
//		service.buildActivity(false, jsonMap);
		service.buildActivity(1, 527, true, 9, jsonMap);
//		List<OpActivity> list = (List<OpActivity>) jsonMap.get(OptResult.JSON_KEY_ACTIVITY);
//		for(OpActivity act : list) {
//			Log.debug(act.getActivityName() + ">>>>>>>>>>>>>>>>>>>>>>>" + act.getWorldCount());
//			List<HTWorldLabelWorldAuthor> ulist = act.getAuthors();
//			for(HTWorldLabelWorldAuthor u : ulist) {
//				Log.debug(u.getUserId() + " : " + u.getUserName());
//			}
//		}
		logObj(jsonMap);
	}
	
	@Test
	public void testBuildTypeIndex() throws Exception {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		service.buildTypeIndex(381, 3, jsonMap);
		logObj(jsonMap);
	}
	
	@Test
	public void testBuildTypeSquare() throws Exception {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		service.buildTypeSquare(600, 1, 10, 3, 6, 7, 527, jsonMap);
		logObj(jsonMap);
	}
	
	@Test
	public void testBuildSuperbTypeSquare() throws Exception {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		service.buildSuperbTypeSquare(10000, 1, 10, 3, 6, 3, false, true, true, 527, jsonMap);
		service.buildSuperbTypeSquare(0, 1, 10, 3, 6, 3, false, true, true, 527, jsonMap);
		service.buildSuperbTypeSquare(10000, 1, 10, 3, 6, 3, false, false, false, 527, jsonMap);
		service.buildSuperbTypeSquare(0, 1, 10, 3, 6, 3, false, false, false, 527, jsonMap);
		logObj(jsonMap);
	}
	
	
	@Test
	public void testGetMaxlActivity() throws Exception {
		Object act = service.getMaxActivity(62);
		logObj(act);
	}
	
	@Test
	public void testBuildCommercialActivityLogo() throws Exception {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		service.buildCommercialActivityLogo(jsonMap);
		logObj(jsonMap);
	}
	
	@Test
	public void testBuildActivityWinner() throws Exception {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		service.buildActivityWinner(false, 2, 116, 1000, 1, 10, jsonMap, false, 3, 12);
		service.buildActivityWinner(false, 2, 116, 0, 1, 10, jsonMap, false, 3, 12);
		service.buildActivityWinner(true, 132, 116, 3002, 1, 10, jsonMap, false, 3, 12);
		service.buildActivityWinner(true, 132, 116, 0, 1, 10, jsonMap, false, 3, 12);
		logObj(jsonMap);
	}
	
	
	@Test
	public void testBuildSuperbTypeSquareList() throws Exception {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
//		service.buildSuperbTypeSquareList(0, 1, 10, 0, true, 3, 6, 3, false, 527, jsonMap);
//		service.buildSuperbTypeSquareList(2231, 2, 10, 1, false, 3, 6, 3, false, 527, jsonMap);
		service.buildSuperbTypeSquareList(2231, 3, 10, 2, false, 3, 6, 3, false, 527, jsonMap);
//		logObj(jsonMap);
		List<OpWorldTypeDto> list = (List<OpWorldTypeDto>) jsonMap.get(OptResult.JSON_KEY_HTWORLD);
		for(OpWorldTypeDto dto : list) {
			Log.debug(dto.getConcerned());
		}
		Log.debug(jsonMap.get(OptResult.JSON_KEY_NEXT_START));
	}
	
	@Test
	public void testBuildActivityLikeRank() throws Exception {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		service.buildActivityLikeRank(527, 11, jsonMap);
		logObj(jsonMap);
	}
	
	@Test
	public void testBuildTutorial() throws Exception {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		service.buildTutorial(3, 6, 3, false, 485, jsonMap);
//		logObj(jsonMap);
		List<OpWorldTypeDto> list = (List<OpWorldTypeDto>) jsonMap.get(OptResult.JSON_KEY_HTWORLD);
		for(OpWorldTypeDto dto : list) {
			Log.debug(dto.getId());
		}
	}
	
	@Test
	public void testBuildSuperbTypeSquareListV2() throws Exception {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
//		service.buildSuperbTypeSquareListV2(0,2231, 1, 10, 3, 6, 3, false, 485, jsonMap);
		service.buildSuperbTypeSquareListV2(0, 0, 1, 10, 3, 6, 3, false, 485, jsonMap);
		logObj(jsonMap);
	}
	
	@Test
	public void testBuildSuperbTypeSquareListV3() throws Exception {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		service.buildSuperbTypeSquareListV3(485, 0, 0, 1, 10, jsonMap);
		logObj(jsonMap);
	}
	
}
