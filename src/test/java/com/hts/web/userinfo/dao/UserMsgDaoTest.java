package com.hts.web.userinfo.dao;

import java.util.List;

import net.sf.json.JSONArray;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.BaseTest;
import com.hts.web.base.database.RowSelection;
import com.hts.web.common.pojo.UserMsgIndex;
import com.hts.web.common.pojo.UserMsgRecipientDto;
import com.hts.web.common.util.Log;

/**
 * <p>
 * 私信数据访问接口单元测试
 * </p>
 * 
 * 创建时间：2013-11-29
 * @author ztj
 *
 */
public class UserMsgDaoTest extends BaseTest {

	@Autowired
	private UserMsgDao dao;
	
	@Test
	public void queryConcernMsgIndex(){
		List<UserMsgIndex> list = dao.queryConcernMsgIndex(485, new RowSelection(1,10));
		JSONArray array = JSONArray.fromObject(list);
		Log.debug(array.toString());
	}
	
	@Test
	public void queryUnConcernMsgIndex() {
		List<UserMsgIndex> list = dao.queryUnConcernMsgIndex(485, new RowSelection(1,10));
		JSONArray array = JSONArray.fromObject(list);
		Log.debug(array.toString());
	}
	
	@Test
	public void testQueryRecipientMsg() {
		List<UserMsgRecipientDto> list = dao.queryRecipientMsg(660, 430, 333);
		JSONArray array = JSONArray.fromObject(list);
		Log.debug(array.toString());
	}
	
	@Test
	public void test() {
		List<UserMsgIndex> list = dao.queryConcernMsgIndex(527, new RowSelection(1, 10));
		JSONArray array = JSONArray.fromObject(list);
		Log.debug(array.toString());
	}
	
}
