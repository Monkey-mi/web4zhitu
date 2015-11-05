package com.hts.web.ztworld.dao;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.BaseTest;
import com.hts.web.base.database.RowCallback;
import com.hts.web.base.database.RowSelection;
import com.hts.web.common.pojo.HTWorldLikedInline;
import com.hts.web.common.util.Log;


/**
 * <p>
 * 织图喜欢单元测试
 * </p>
 * 
 * @author ztj 2013-7-31
 *
 */
public class HTWorldLikedDaoTest extends BaseTest {

	@Autowired
	private HTWorldLikedDao dao;
	
	@Test
	public void saveLikedTest() {
//		dao.saveLiked(new HTWorldLiked(1, 10, 10));
	}
	
	@Test
	public void delLikedTest() {
		dao.delLiked(10, 10);
	}
	
	@Test
	public void queryLikedCountTest() {
		dao.queryLikedCount(28575);
	}
	
	@Test
	public void testQueryLikedUserByLimit() {
		Integer[] worldIds = new Integer[]{10885,13154};
		dao.queryInlineLikedByLimit(worldIds, 2, new RowCallback<HTWorldLikedInline>() {

			@Override
			public void callback(HTWorldLikedInline likedUser) {
				Log.debug(likedUser.getUserId());
			}
			
		});
	}
	
	@Test
	public void testQueryLikedUserByLimit2() {
		dao.queryInlineLikedByLimit(9640, 2, new RowCallback<HTWorldLikedInline>() {

			@Override
			public void callback(HTWorldLikedInline likedUser) {
				Log.debug(likedUser.getUserId());
			}
			
		});
	}
	
	@Test
	public void queryLikedUserTest() {
		dao.queryLikedUser(28575, new RowSelection(1, 10));
		dao.queryLikedUser(1000, 28575, new RowSelection(1, 10));
	}
	
	@Test
	public void queryLikedTest() {
		dao.queryLiked(485, new Integer[]{10885,13154}, new RowCallback<Integer>() {

			@Override
			public void callback(Integer t) {
			}
		});
	}
	
	@Test
	public void isLikedTest() {
		dao.isLiked(485, 28575);
	}
}
