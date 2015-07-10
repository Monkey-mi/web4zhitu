package com.hts.web.operations.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.BaseTest;
import com.hts.web.base.constant.OptResult;
import com.hts.web.common.pojo.OpUser;
import com.hts.web.common.pojo.OpUserVerifyDto;
import com.hts.web.common.util.Log;

public class UserOperationsServiceTest extends BaseTest {

	@Autowired
	private UserOperationsService service;
	
	@Test
	public void testBuildPlatformUserRecomend() throws Exception {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		service.buildPlatformUserRecomend(485, jsonMap);
		logObj(jsonMap);
	}
	
	@Test
	public void testBuildUserRecommend() throws Exception {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		service.buildUserRecommend(0, 1, 25, 116, jsonMap);
		logObj(jsonMap);
	}
	
	@Test
	public void testBuildLabelRecommendUser() throws Exception {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		service.buildLabelRecommendUser(1000, 1, 5, 3, 604, true, jsonMap);
		logObj(jsonMap);
	}
	
	@Test
	public void testSaveOrUpdateRecommend() throws Exception {
		service.saveOrUpdateRecommend((int)(500000 * Math.random()), 1);
	}
	
	@Test
	public void testUpdateRecommend() throws Exception {
		service.updateRecommendUserAccept(381, false);
	}
	
	@Test
	public void testGetRecommendState() throws Exception {
		service.getRecommendState(381);
	}
	
	@Test
	public void testBuildUserRecommendByActivity() throws Exception {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		service.buildSquareRecommendUserV2(3240, 2, 10, 485, 0, false, jsonMap);
		service.buildSquareRecommendUserV2(0, 1, 10, 1278, 0, false, jsonMap);
		List<OpUser> list = (List<OpUser>) jsonMap.get(OptResult.JSON_KEY_USER_INFO);
//		logObj(jsonMap);
		for(OpUser u : list) {
//			Log.debug(u.getId() + " : " + u.getActivity() + " : " + u.getCurrPos() + " : " + u.getLastPos());
			Log.debug(u.getId() + " : " + u.getVerifyId() + " : " + u.getVerifyName() + " : " + u.getVerifyIcon());
		}
	}
	
	@Test
	public void testBuildVerifyIndex() throws Exception {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		service.buildVerifyIndex(485, 4,jsonMap);
		List<OpUserVerifyDto> list = (List<OpUserVerifyDto>) jsonMap.get(OptResult.JSON_KEY_INDEX);
		for(OpUserVerifyDto dto : list) {
			Log.debug(dto.getId() + " : " + dto.getUserInfo().size());
		}
//		logObj(jsonMap);
	}
	
	@Test
	public void testBuildVerifyRecommendUser() throws Exception {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		service.buildVerifyRecommendUser(0, 1, 10, 485, 4, 0, jsonMap);
		service.buildVerifyRecommendUser(1441, 2, 10, 485, 4, 4, jsonMap);
		List<OpUser> list = (List<OpUser>) jsonMap.get(OptResult.JSON_KEY_USER_INFO);
//		logObj(jsonMap);
		for(OpUser u : list) {
//			Log.debug(u.getId() + " : " + u.getActivity() + " : " + u.getCurrPos() + " : " + u.getLastPos());
			Log.debug(u.getRecommendId() + " : " + u.getId() + " : " + u.getActivity());
		}
//		logObj(jsonMap);
	}
	
	@Test
	public void getRandomZombieIdTest() throws Exception {
		Integer id = service.getRandomZombieId();
		Log.debug(id);
	}
	
}
