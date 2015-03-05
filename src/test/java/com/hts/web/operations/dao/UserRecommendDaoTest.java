package com.hts.web.operations.dao;

import java.util.List;

import net.sf.json.JSONArray;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.BaseTest;
import com.hts.web.base.database.RowCallback;
import com.hts.web.base.database.RowSelection;
import com.hts.web.common.pojo.OpUserLabelRecommend;
import com.hts.web.common.pojo.UserVerifyDto;
import com.hts.web.common.util.Log;

/**
 * <p>
 * 用户推荐数据访问对象单元测试
 * </p>
 * 
 * 创建时间：2014-1-13
 * @author ztj
 *
 */
public class UserRecommendDaoTest extends BaseTest {

	@Autowired
	private UserRecommendDao dao;
	
	@Test
	public void testQueryRecommendUser() {
		dao.queryRecommendUser(109, new RowSelection(1, 10));
	}
	
	@Test
	public void testQueryRecommendUserByMaxId() {
		dao.queryRecommendUserByMaxId(109,1001,new RowSelection(1, 10));
	}
	
	@Test
	public void testQueryRecommendUserByUID() {
		dao.queryOpUserByUID(109);
	}
	
	@Test
	public void testQueryPlatformRecommendUser() {
		dao.queryPlatformRecommendUser("1,2", 381);
	}
	
	@Test
	public void testQueryLabelRecommendUser() {
		dao.queryLabelRecommendUser(527, new RowSelection(1, 10));
	}
	
	@Test
	public void testQueryLabelRecommendUserByMaxId() {
		List<OpUserLabelRecommend> list = dao.queryLabelRecommendUser(100, 574, new RowSelection(2, 10));
		JSONArray jsArray = JSONArray.fromObject(list);
		Log.debug(jsArray);
	}
	
	@Test
	public void testQueryUserVerifyDto() {
		dao.queryVerifyUser(485, new Integer[]{2,3,4,5}, 4, new RowCallback<UserVerifyDto>() {

			@Override
			public void callback(UserVerifyDto t) {
				
			}
		});
	}
	
	@Test
	public void testQueryVerifyRecommendUserOrderByAct() {
//		dao.queryVerifyRecommendUserOrderByAct(485,2, new RowSelection(1, 10));
		dao.queryVerifyRecommendUserOrderByAct(1000,485, 4, new RowSelection(1, 10));
	}
}
