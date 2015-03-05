package com.hts.web.ztworld.dao;

import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.BaseTest;
import com.hts.web.base.database.RowCallback;
import com.hts.web.common.pojo.HTWorldLikedUser;
import com.hts.web.common.util.Log;


/**
 * <p>
 * 织图喜欢单元测试
 * </p>
 * 
 * 创建时间：2013-7-31
 * @author ztj
 *
 */
public class HTWorldLikedDaoTest extends BaseTest {

	@Autowired
	private HTWorldLikedDao dao;
	
	@Test
	public void testUpdateLiked() throws Exception {
		dao.updateLiked(20, 11, 1111, new Date());
	}
	
	@Test
	public void testQueryLikedUserByLimit() {
		Integer[] worldIds = new Integer[]{10885,13154};
		dao.queryLikedUserByLimit(worldIds, 2, new RowCallback<HTWorldLikedUser>() {

			@Override
			public void callback(HTWorldLikedUser likedUser) {
				Log.debug(likedUser.getUserId());
			}
			
		});
	}
	
	@Test
	public void testQueryLikedUserByLimit2() {
		dao.queryLikedUserByLimit(9640, 2, new RowCallback<HTWorldLikedUser>() {

			@Override
			public void callback(HTWorldLikedUser likedUser) {
				Log.debug(likedUser.getUserId());
			}
			
		});
	}
}
