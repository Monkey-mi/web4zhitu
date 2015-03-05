package com.hts.web.userinfo.dao;

import com.hts.web.base.BaseTest;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

import com.hts.web.common.pojo.RetrievePasswordDto;
import com.hts.web.common.util.Log;

public class UserRetrievePWDDaoTest extends BaseTest{
	
	@Autowired
	private UserRetrievePWDDao dao;
	
//	@Test
	public void resetPWDTest()throws Exception{
		byte[] pwd = new byte[]{1,2,1};
		dao.resetPWD(962, pwd);
	}
//	@Test
	public void checkUserInfoIsExistTest()throws Exception{
		boolean r = dao.checkUserInfoIsExist(new String("349682268@qq.com"));
		Log.info("checkUserInfoIsExistTest==================================>>>>" + r);
	}
	@Test
	public void checkRPWDIsExistTest()throws Exception{
		boolean r = dao.checkRPWDIsExist(new String("349682268@qq.com"));
		Log.info("checkRPWDIsExistTest==================================>>>>" + r);
	}
	@Test
	public void deleteRPWDByEndTime()throws Exception{
		Date now = new Date();
		dao.deleteRPWDByTime(now);
	}
//	@Test
	public void saveRPWDTest()throws Exception{
		byte[] sid=new byte[]{1,2,1};
		RetrievePasswordDto rpwd = new RetrievePasswordDto(new String("349682268@qq.com"),sid,new Date());
		dao.saveRPWD(rpwd);
	}
	
	@Test
	public void getLoginCodeByUIDTest()throws Exception{
		String loginCode = dao.getLoginCodeByUID(962);
		Log.info("getLoginCodeByUIDTest==================================>>>>" + loginCode);
	}
	
	@Test
	public void getUserNameByLogincodeTest()throws Exception{
		String userName = dao.getUserNameByLoginCode("349682268@qq.com");
		Log.info("getUserNameByLogincodeTest==================================>>>>" + userName);
	}
}
