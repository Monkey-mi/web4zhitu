package com.hts.web.operations.dao;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.BaseTest;
import com.hts.web.common.pojo.AddrDistrictDto;
import com.hts.web.operations.dao.mongo.NearDistrictMongoDao;

public class NearDistrictMongoDaoTest  extends BaseTest{
	@Autowired
	NearDistrictMongoDao dao;
	Logger log = Logger.getLogger(NearDistrictMongoDaoTest.class);
	
//	@Test
	public void insertDistrictTest()throws Exception{
		AddrDistrictDto dto = new AddrDistrictDto();
		dto.setId(1987);
		dto.setGbtId(440305);
		dto.setCityId(21760);
		dto.setCityName("深圳");
		dto.setDistictName("福田区");
		dto.setLongitude(114.047688);
		dto.setLatitude(22.543447);
		dao.insertDistrict(dto);
	}
	@Test
	public void queryDistrictTest()throws Exception{
		log.info(dao.queryDistrict(21760));
	}
}
