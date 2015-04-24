package com.hts.web.plat.dao;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.BaseTest;
import com.hts.web.base.constant.PlatFormCode;
import com.hts.web.common.pojo.PlatConcern;
import com.hts.web.common.util.Log;

public class PlatConcernCacheDaoTest extends BaseTest {

	@Autowired
	private PlatConcernCacheDao dao;
	
	@Test
	public void saveConcernTest() throws Exception {
//		dao.saveConcern(new PlatConcern("1234", "123", null, PlatFormCode.SINA));
	}
	
	@Test
	public void saveBeConcernTest() throws Exception {
//		dao.saveBeConcern(new PlatConcern(null, null, "搞笑热门微博集锦", PlatFormCode.SINA));
	}
	
	@Test
	public void queryAllBeConcernTest() throws Exception {
//		List<PlatConcern> list = dao.queryAllBeConcern();
//		Log.debug(list);
	}
	
	@Test
	public void deleteBeConcernTest() throws Exception {
//		dao.deleteBeConcern(0);
	}
}
