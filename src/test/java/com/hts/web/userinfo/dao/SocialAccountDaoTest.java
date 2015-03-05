package com.hts.web.userinfo.dao;

import java.util.List;

import net.sf.json.JSONArray;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.BaseTest;
import com.hts.web.base.constant.PlatFormCode;
import com.hts.web.common.pojo.UserSocialAccount;
import com.hts.web.common.util.Log;

public class SocialAccountDaoTest extends BaseTest {

	@Autowired
	private SocialAccountDao dao;
	
	@Test
	public void testQuerySocialAccountByPlatformCodes() {
		List<UserSocialAccount> list = dao.querySocialAccountByPlatformCodes(new Integer[]{PlatFormCode.SINA}, 119);
		JSONArray array = JSONArray.fromObject(list);
		Log.debug(array.toString());
	}
}
