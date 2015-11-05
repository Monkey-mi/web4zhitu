package com.hts.web.ztworld.dao;

import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.BaseTest;
import com.hts.web.base.database.RowCallback;
import com.hts.web.common.pojo.HTWorldLikedInline;
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
}
