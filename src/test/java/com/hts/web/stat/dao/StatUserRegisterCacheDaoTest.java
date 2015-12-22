package com.hts.web.stat.dao;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.BaseTest;

/**
 * 用户注册数据统计接口
 * 
 * @author lynch 2015-08-28
 *
 */
public class StatUserRegisterCacheDaoTest extends BaseTest {

	@Autowired
	private StatUserRegisterCacheDao dao;
	
	@Test
	public void saveRegisterTest() {
//		dao.saveRegisterStat(1, 0, new Date());
	}
}
