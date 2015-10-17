package com.hts.web.push;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.BaseTest;
import com.hts.web.base.constant.Tag;
import com.hts.web.common.util.NumberUtil;
import com.hts.web.push.service.PushService;

/**
 * <p>
 * android推送服务业务逻辑单元测试
 * </p>
 * 
 * 创建时间：2013-6-236
 * @author ztj
 *
 */
public class PushServiceTest extends BaseTest {

	@Autowired
	private PushService service;
	
	@Test
	public void apnsPushTest() throws Exception {
//		service.apnsPushTest("581344d5fa048084e6acaefd54e12e7f014b0085eced6c4491a7d76285ca4a4c");
//		while(true) {}
//		service.pushAtMsg(4, 79129, "哈哈", Tag.TRUE, Tag.FALSE);
//		while(true){}
	}
	
}
