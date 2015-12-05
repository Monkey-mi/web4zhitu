package com.hts.web.ztworld.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.BaseTest;
import com.hts.web.base.constant.OptResult;
import com.hts.web.base.constant.Tag;
import com.hts.web.common.pojo.HTWorld;
import com.hts.web.common.pojo.HTWorldDto;
import com.hts.web.common.pojo.HTWorldInteractDto;
import com.hts.web.common.pojo.HTWorldLatest;
import com.hts.web.common.util.Log;

public class ZTWorldServiceTest extends BaseTest {
	
	private static Logger logger = Logger.getLogger(ZTWorldServiceTest.class);
	@Autowired
	private ZTWorldService service;

	@Test
	public void testSaveWorld() throws Exception {
		Map<String, Object> jsonMap;
		Integer userId;
		String json = "{"
				+ " '1012805956' : ["
				+ "   	{"
				+ "   	'width' : 640,"
				+ "       'path' : 'http://static.imzhitu.com/ios/image/2014/3/26/21/c5a9e4e8b41011e3a9a712bf1fbc47a9_8.jpg',"
				+ "     'height' : 640,'angle':90,'type':1"
				+ "   	}"
				+ "   ]"
				+ "   }";
		
		userId = 485;
		jsonMap= new HashMap<String, Object>();
		
		service.saveWorld(json, 1012805956, Tag.ANDROID, 0, 485, "worldName",
				"织图描述...", "自拍情结", "1,2","自拍情节", 4, 
				"http://static.imzhitu.com/ios/image/2014/3/26/21/c5a9e4e8b41011e3a9a712bf1fbc47a9_8.jpg",
				"http://static.imzhitu.com/ios/image/2014/3/26/21/c5a9e4e8b41011e3a9a712bf1fbc47a9_8.jpg",
				null,
				"http://static.imzhitu.com/ios/image/2014/3/26/21/c5a9e4e8b41011e3a9a712bf1fbc47a9_8.jpg",
				113.567841, 22.167654, "locationDesc", "locationAddr", "province", "city", null, "18", 1, 
				null, Tag.WORLD_TYPE_DEFAULT, null, 0, null, null, jsonMap);
		HTWorld world = (HTWorld) jsonMap.get(OptResult.JSON_KEY_HTWORLD);
		service.deleteWorld(world.getId(), userId);
	}
	
	@Test
	public void testBuildConcernWorld() throws Exception {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		service.buildConcernWorld(Tag.BULLETIN_CHANNEL, 1,1549, 100, 1, 10, jsonMap, false, 3, 12,0f);
//		service.buildConcernWorld(485, 14474, 1, 10, jsonMap, true, false, 3, 12);
		List<HTWorldInteractDto> list = (List<HTWorldInteractDto>) jsonMap.get(OptResult.JSON_KEY_HTWORLD);
		for(HTWorldInteractDto dto : list) {
//			Log.debug(dto.getId());
			Log.debug(dto.getUserInfo().getRemark());
		}
	}
	
	@Test
	public void testBuildUserWorld() throws Exception {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
//		service.buildUserWorld(116, 114, 1000, 1, 10, jsonMap, false, false, 3, 12);
		service.buildUserWorld(1550, 485, 0, 1, 10, jsonMap, false, 3, 12);
		List<HTWorldInteractDto> list = (List<HTWorldInteractDto>) jsonMap.get(OptResult.JSON_KEY_HTWORLD);
//		JSONObject jsObj = JSONObject.fromObject(jsonMap);
//		Log.debug(jsObj.toString());
		for(HTWorldInteractDto dto : list) {
//			Log.debug(dto.getId());
			Log.debug(dto.getUserInfo().getRemark());
		}
		
	}
	
	@Test
	public void testBuildKeepWorld() throws Exception {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		service.buildKeepWorld(109, 1000, 1, 5, jsonMap, false, 3, 12);
		service.buildKeepWorld(109, 0, 1, 5, jsonMap, false, 3, 12);
		JSONObject jsObj = JSONObject.fromObject(jsonMap);
		Log.debug(jsObj.toString());
	}
	
	@Test
	public void testBuildLikedWorld() throws Exception {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		service.buildLikedWorld(116, 1000, 1, 10, jsonMap, false, false, 3, 12);
		service.buildLikedWorld(116, 0, 1, 10, jsonMap, false, false, 3, 12);
		JSONObject jsObj = JSONObject.fromObject(jsonMap);
		Log.debug(jsObj.toString());
	}
	
	@Test
	public void testBuildLatestWorldV2() throws Exception {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
//		service.buildLatestWorld(116, 1000, 1, 10, jsonMap, false, false, 3, 12);
		service.buildLatestWorld(116, 0, 1, 10, jsonMap, false, 3, 12);
//		JSONObject jsObj = JSONObject.fromObject(jsonMap);
//		Log.debug(jsObj.toString());
		List<HTWorldInteractDto> worldList = (List<HTWorldInteractDto>) jsonMap.get(OptResult.JSON_KEY_HTWORLD);
		for(HTWorldInteractDto dto : worldList) {
			Log.debug(dto.getId());
		}
	}
	
	@Test
	public void testBuildLatestAndUserWorld() throws Exception {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
//		service.buildLatestAndUserWorld(485, 12914, 1, 10, jsonMap, false, false, 3, 12);
		service.buildLatestAndUserWorld(485, 0, 1, 10, jsonMap, false, 3, 12);
//		logObj(jsonMap);
		List<HTWorldInteractDto> worldList = (List<HTWorldInteractDto>) jsonMap.get(OptResult.JSON_KEY_HTWORLD);
		for(HTWorldInteractDto dto : worldList) {
			Log.debug(dto.getId());
		}
	}
	
	@Test
	public void testDeleteWorld() throws Exception {
//		String json = "{"
//				+ " '1012805956' : ["
//				+ "   	{"
//				+ "   	'width' : 640,"
//				+ "       'path' : 'http://static.imzhitu.com/ios/image/2014/3/26/21/c5a9e4e8b41011e3a9a712bf1fbc47a9_8.jpg',"
//				+ "     'height' : 640,'angle':90,'type':1"
//				+ "   	}"
//				+ "   ]"
//				+ "   }";
//		HTWorld world = service.saveWorld(json, 1012805956, Tag.ANDROID, 0, 485, "worldName",
//				"worldDesc", "街景,路线", "1,2","故事", 4, 
//				"http://static.imzhitu.com/ios/image/2014/3/26/21/c5a9e4e8b41011e3a9a712bf1fbc47a9_8.jpg",
//				"http://static.imzhitu.com/ios/image/2014/3/26/21/c5a9e4e8b41011e3a9a712bf1fbc47a9_8.jpg",
//				null,
//				"http://static.imzhitu.com/ios/image/2014/3/26/21/c5a9e4e8b41011e3a9a712bf1fbc47a9_8.jpg",
//				0.1d, 0.1d, "locationDesc", "locationAddr", "province", "city", null, "18", 1, 
//				null, Tag.WORLD_TYPE_DEFAULT, null, 0, null, null, new HashMap<String, Object>());
//		logObj(world);
//		service.deleteWorld(world.getId(),485);
	}
	
	@Test
	public void testGetHTWorldDtoFromURL() throws Exception {
		HTWorldDto dto = service.getHTWorldDtoFromURL("http:127.0.0.1:8080/hts/NO9913", false);
		JSONObject jsObj = JSONObject.fromObject(dto);
		Log.debug(jsObj.toString());
	}
	
	@Test
	public void getTitleChildPageInfoTest() throws Exception {
		JSONObject jsonObj = service.getTitleChildPageInfo(147927, 10, true, true);
		Log.debug(jsonObj.toString());
		
	}
	
	@Test
	public void buildWorldWithTitleChild() throws Exception {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		service.buildWorldWithTitleChild(1596, 10, true, jsonMap);
		logObj(jsonMap);
	}
	
	@Test
	public void testGetChildWorldPageInfoById() throws Exception {
		JSONObject jsonObj = service.getChildWorldPageInfoById(10641, 49507, 10);
		Log.debug(jsonObj);
	}
	
	@Test
	public void testbuildWorldWithTitleChild() throws Exception {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		service.buildWorldWithTitleChild(10303, 1, true, jsonMap);
		logObj(jsonMap);
	}
	
	@Test
	public void testBuildRandomUserWorld() throws Exception {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		service.buildRandomUserWorld(109, 0, 4, jsonMap);
		logObj(jsonMap);
	}
	
	@Test
	public void testUpdateClickCount() throws Exception {
		service.addClickCount(10962, 50);
	}
	
	@Test
	public void testBuildLatestWorld() throws Exception {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		service.buildLatestWorld(4, jsonMap);
		logObj(jsonMap);
	}
	
	@Test
	public void testGetHTWorldDtoById() throws Exception {
		HTWorldDto dto = service.getHTWorldDtoById(26767, true);
		JSONObject jsobj = JSONObject.fromObject(dto.getTextStyle());
		System.out.println(jsobj.get("mask"));
	}
	
	@Test
	public void buildWorldInteractByDesc() throws Exception {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		service.buildHTWorld("喜欢", 1420, 400, 1, 10, jsonMap, false, 0, 9);
		logObj(jsonMap);
	}
	
	@Test
	public void testBuildLatestChildType() throws Exception {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		service.buildLatestChildType(jsonMap);
		logObj(jsonMap);
	}
	
	@Test
	public void testAddChildWorldUseCount() throws Exception {
		service.addChildTypeUseCount(19);
	}
	
	@Test
	public void testBuildWorldFilterLogo() throws Exception {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		service.buildFilterLogo(2.9060f, jsonMap);
		logObj(jsonMap);
	}
	
	@Test
	public void testBuildLatestIndex() throws Exception {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		service.buildLatestIndex(1024, 0L, 1, 4, jsonMap);
		service.buildLatestIndex(1024, 1412133273607L, 1, 4, jsonMap);
		List<HTWorldLatest> list = (List<HTWorldLatest>) jsonMap.get(OptResult.JSON_KEY_HTWORLD);
		for(HTWorldLatest l : list) {
			logger.debug(l.getInterval() + "," + l.getId());
		}
		logObj(jsonMap);
	}
	
	@Test
	public void testBuildLatest() throws Exception {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		service.buildLatest(1024, 1412133273607L-900000, 1412133273607L - 1200000, 372231, 9, jsonMap);
		service.buildLatest(1024, 1412133273607L-900000, 0L, 372231, 9, jsonMap);
		List<HTWorldLatest> list = (List<HTWorldLatest>) jsonMap.get(OptResult.JSON_KEY_HTWORLD);
		for(HTWorldLatest l : list) {
			logger.debug(l.getId());
		}
		logObj(jsonMap);
	}
	
	
	@Test
	public void testBuildLatestIndex2() throws Exception {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		service.buildLatestIndex2(0, 0L, 1, 0, jsonMap);
//		service.buildLatestIndex2(1024, 1412133273607L, 1, 4, jsonMap);
		List<HTWorldLatest> list = (List<HTWorldLatest>) jsonMap.get(OptResult.JSON_KEY_HTWORLD);
		for(HTWorldLatest l : list) {
			logger.debug(l.getInterval() + "," + l.getId());
		}
		logObj(jsonMap);
	}
	
	@Test
	public void testBuildLatest2() throws Exception {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
//		service.buildLatest2(1024, 1412133273607L-900000, 1412133273607L - 1200000, 372231, 9, jsonMap);
		service.buildLatest2(1024, 1412133273607L-900000, 0L, 372231, 9, jsonMap);
		List<HTWorldLatest> list = (List<HTWorldLatest>) jsonMap.get(OptResult.JSON_KEY_HTWORLD);
		for(HTWorldLatest l : list) {
			logger.debug(l.getId());
		}
		logObj(jsonMap);
	}
	
	@Test
	public void testBuildAllChild() throws Exception {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		service.buildAllChild(14870, jsonMap);
		logObj(jsonMap);
	}
	
	@Test
	public void testBuildLatestId() throws Exception {
		logNumberList(logger, new NumberListAdapter() {

			@Override
			public void buildNumberList(Map<String, Object> jsonMap)
					throws Exception {
				service.buildLatestId(0, 10000, 10, jsonMap);
				service.buildLatestId(10000, 0, 10, jsonMap);
				service.buildLatestId(10000, 10000, 10, jsonMap);
			}
		
		});
	}
	
}
