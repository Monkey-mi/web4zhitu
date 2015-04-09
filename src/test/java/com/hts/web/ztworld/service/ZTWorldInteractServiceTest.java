package com.hts.web.ztworld.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.BaseTest;
import com.hts.web.common.pojo.HTWorldGeo;
import com.hts.web.common.pojo.HTWorldInteractDto;
import com.hts.web.common.pojo.PushStatus;
import com.hts.web.common.util.Log;

/**
 * <p>
 * 织图世界评论业务逻辑访问对象单元测试
 * </p>
 *
 * 创建时间：2013-7-4
 * @author ztj
 *
 */
public class ZTWorldInteractServiceTest extends BaseTest {

	private static Logger logger = Logger.getLogger(ZTWorldInteractServiceTest.class);
	
	@Autowired
	private ZTWorldInteractService service;
	
	@Test
	public void testSaveComment() throws Exception {
		PushStatus status = service.saveComment(false, 14316, 1550, 1549, "评论测试" + new Date().getTime());
		JSONObject jsObj = JSONObject.fromObject(status);
		logger.debug(jsObj);
		Thread.sleep(5000);
	}
	
	@Test
	public void testDeleteComment() throws Exception {
//		Map<String, Object> jsonMap = new HashMap<String, Object>();
//		service.saveComment(false, 12098, 116, null, "评论测试", jsonMap);
//		service.deleteComment(dto.getId(), 116);
	}
	
	@Test
	public void saveReply() throws Exception {
		PushStatus status = service.saveReply(true, 14467, null, 1591, "@朱天杰 : 回复测试", 7952,485);
		JSONObject jsObj = JSONObject.fromObject(status);
		logger.debug(jsObj);
	}
	
	@Test
	public void testBuildComments() throws Exception {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		service.buildComments(527, 9896, 0,0,1, 10, jsonMap);
		JSONObject jsObj = JSONObject.fromObject(jsonMap);
		Log.debug(jsObj.toString());
	}
	
	@Test
	public void testSaveLiked() throws Exception {
		PushStatus status = service.saveLiked(false, 189037, 14527, 1549);
		logObj(status);
	}
	
	@Test
	public void testCancelLiked() throws Exception {
		service.cancelLiked(189037, 14527);
	}
	
	@Test
	public void testSaveKeep() throws Exception {
		service.saveKeep(116, 12098);
	}
	
	@Test
	public void testCancelKeep() throws Exception {
		service.cancelKeep(116, 12098);
	}
	
	@Test
	public void testSaveReport() throws Exception {
		service.saveReport(20, 11, null);
	}
	
	@Test
	public void testCancelReport() throws Exception {
		service.cancelReport(20, 11);
	}
	
	@Test
	public void testGetWorldGeo() throws Exception {
		List<HTWorldGeo> list = service.getWorldGeo(371);
		JSONArray array = JSONArray.fromObject(list);
		Log.debug(array);
	}
	
	@Test
	public void testGetWorldInteract() throws Exception {
		HTWorldInteractDto dto = service.getWorldInteract(10962, 527, true, false, 3, 12);
		Log.debug(dto.toString());
		JSONObject jsObj = JSONObject.fromObject(dto);
		Log.debug(jsObj.toString());
	}
	
	@Test
	public void testBuildLikedUser() throws Exception {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		service.buildLikedUser(641, 10976, 527, 1, 10, jsonMap);
		logObj(jsonMap);
	}
	
	@Test
	public void testBuildLikedWorld() throws Exception {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		service.buildLikeOthersWorldThumbnail(3, 1550, 1549, jsonMap);
		logObj(jsonMap);
	}
	
	@Test
	public void testSaveCommentReport() throws Exception {
		service.saveCommentReport(485, 2, "");
	}
}
