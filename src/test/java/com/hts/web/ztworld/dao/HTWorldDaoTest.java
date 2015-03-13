package com.hts.web.ztworld.dao;

import java.util.Date;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.BaseTest;
import com.hts.web.common.pojo.HTWorldDto;
import com.hts.web.common.pojo.HTWorldLatest;
import com.hts.web.common.pojo.HTWorldLatestIndex;
import com.hts.web.common.util.Log;
import com.hts.web.ztworld.service.ZTWorldInteractServiceTest;

/**
 * <p>
 * 织图世界访问接口单元测试
 * </p>
 * 
 * 创建时间：2013-8-6
 * @author ztj
 *
 */
public class HTWorldDaoTest extends BaseTest {
	
	private static Logger logger = Logger.getLogger(HTWorldDaoTest.class);

	@Autowired
	private HTWorldDao dao;
	
	@Test
	public void testQueryHTWorldDtoById() {
		HTWorldDto worldDto = dao.queryHTWorldDtoById(9934);
		JSONObject jsObj = JSONObject.fromObject(worldDto);
		Log.debug(jsObj.toString());
	}
	
	@Test
	public void testQueryHTWorldDtoByShortLink() {
		HTWorldDto worldDto = dao.queryHTWorldDtoByShortLink("ruqiiy");
		JSONObject jsObj = JSONObject.fromObject(worldDto);
		Log.debug(jsObj.toString());
	}
	
	@Test
	public void testAddClickCount() {
		dao.addClickCount(10743, 1);
	}
	
	@Test
	public void queryLatest1() {
		List<HTWorldLatest> list = dao.queryLatest(485, new Date().getTime(), 0, new int[]{300000, 600000, 900000}, new int[]{6,9,6});
		for(HTWorldLatest l : list) {
			logger.debug(l.getInterval() + " : " + l.getId() + " : " + l.getTitleThumbPath());
		}
	}
	
	@Test
	public void queryLatestIndex()	 {
		List<HTWorldLatestIndex> list = dao.queryLatestIndex(485, new Date().getTime(), 0, new int[]{300000, 600000, 900000});
		for(HTWorldLatestIndex i : list) {
			logger.debug(i.getInterval() + " : " + i.getTotal());
		}
	}
	
	@Test
	public void queryLatest2() {
		long startTime = new Date().getTime();
		dao.queryLatest(1000, 485, startTime, startTime - 900000, 10);
	}
	
	@Test
	public void updateChildSumTest() {
		Integer i = dao.queryChildCount(-100);
		logger.debug(i);
	}
}
