package com.hts.web.push;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.BaseTest;
import com.hts.web.base.HTSException;
import com.hts.web.base.constant.Tag;
import com.hts.web.common.pojo.PushIM;
import com.hts.web.push.service.YunbaPushService;

public class YunbaPushServiceTest extends BaseTest {

	@Autowired
	private YunbaPushService imPushService;
	
	@Test
	public void testYunbaIMPush() throws HTSException {
//		imPushService.pushIMMsg(1549, new PushIM(Tag.PUSH_ACTION_LIKED, "小小莫", "呵呵",  486),
//				"title", Tag.IOS, Tag.TRUE, Tag.FALSE);
	}
}
