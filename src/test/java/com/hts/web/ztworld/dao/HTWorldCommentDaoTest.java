package com.hts.web.ztworld.dao;

import java.util.List;

import net.sf.json.JSONObject;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.BaseTest;
import com.hts.web.base.database.RowCallback;
import com.hts.web.base.database.RowSelection;
import com.hts.web.common.pojo.HTWorldCommentDto;
import com.hts.web.common.pojo.HTWorldCommentUser;
import com.hts.web.common.util.Log;

public class HTWorldCommentDaoTest extends BaseTest {

	@Autowired
	private HTWorldCommentDao dao;
	
	@Test
	public void testQueryCommentDtoById() {
		HTWorldCommentDto dto = dao.queryCommentDtoById(302);
		JSONObject jsObj = JSONObject.fromObject(dto);
		Log.debug(jsObj);
	}
	
	@Test
	public void testQueryUserComment() {
		List<HTWorldCommentDto> list = dao.queryUserComment(109, new RowSelection(1, 10));
	}
	
	@Test
	public void testQueryCommentUserByLimit() {
		Integer[] worldIds = new Integer[]{10879,10880};
		dao.queryCommentUserByLimit(worldIds, 3, new RowCallback<HTWorldCommentUser>() {

			@Override
			public void callback(HTWorldCommentUser comment) {
				Log.debug(comment.getId() + comment.getContent());
			}
			
		});
	}
	
	@Test
	public void testQueryCommentUserByLimit2() {
		dao.queryCommentUserByLimit(10879, 3, new RowCallback<HTWorldCommentUser>(){

			@Override
			public void callback(HTWorldCommentUser comment) {
				Log.debug(comment.getId() + comment.getContent());
			}
			
		});
	}
	
}
