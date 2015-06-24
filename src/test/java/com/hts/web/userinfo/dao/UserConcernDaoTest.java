package com.hts.web.userinfo.dao;

import java.util.List;

import junit.framework.Assert;
import net.sf.json.JSONArray;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.BaseTest;
import com.hts.web.base.database.RowSelection;
import com.hts.web.common.pojo.UserConcernDto;
import com.hts.web.common.util.Log;

/**
 * <p>
 * 用户关注数据访问对象单元测试
 * </p>
 * 
 * 创建时间：2013-8-4
 * @author ztj
 *
 */
public class UserConcernDaoTest extends BaseTest {

	@Autowired
	private UserConcernDao dao;
	
	@Test
	public void testQueryConcerns() {
		List<UserConcernDto> list = dao.queryConcerns(116, new RowSelection(1, 2));
		JSONArray jsObj = JSONArray.fromObject(list);
		Log.debug(jsObj);
	}
	
	@Test
	public void testQueryValidConcernId() {
		Integer id = dao.queryValidConcernId(100, 10100);
		Log.debug(id);
	}
	
	@Test
	public void testQueryIsMututal() {
		Integer id = dao.queryIsMututal(485, 189037);
		Log.debug(id);
		
	}
}
