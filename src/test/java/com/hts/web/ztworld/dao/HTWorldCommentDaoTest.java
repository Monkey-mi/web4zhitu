package com.hts.web.ztworld.dao;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.BaseTest;
import com.hts.web.base.database.RowCallback;
import com.hts.web.base.database.RowSelection;
import com.hts.web.common.pojo.HTWorldComment;
import com.hts.web.common.pojo.HTWorldCommentDto;
import com.hts.web.common.pojo.HTWorldCommentInline;
import com.hts.web.common.util.Log;

/**
 * 织图评论数据访问接口
 * 
 * @author lynch
 *
 */
public class HTWorldCommentDaoTest extends BaseTest {
	
	@Autowired
	private HTWorldCommentDao dao;
	
	@Test
	public void queryCommentByIdTest() {
		HTWorldComment c = dao.queryCommentByWID(24779, 14316);
		logObj(c);
	}
	
	@Test
	public void queryCommentDtoByIdTest() {
		HTWorldCommentDto dto = dao.queryCommentDtoById(24779, 14316);
		logObj(dto);
	}
	
	@Test
	public void queryCommentCountTest() {
		Long l = dao.queryCommentCount(14316);
		logObj(l);
	}
	
	@Test
	public void queryCommentTest() {
		List<HTWorldCommentDto> list = dao.queryCommentByMaxId(14316, 1000, new RowSelection(1, 10));
		list = dao.queryComment(14316, new RowSelection(1, 10));
		logList(list);
	}
	
	@Test
	public void queryInlineCommentTest() {
		dao.queryInlineComment(new Integer[]{14316,14315}, 
				10, new RowCallback<HTWorldCommentInline>() {

			@Override
			public void callback(HTWorldCommentInline t) {
				
			}
		});
	}
	
	@Test
	public void queryAuthorIdTest() {
		Integer aid = dao.queryAuthorId(24779, 14316);
		Log.debug(aid);
	}

	@Test
	public void isCommentExistTest() {
		boolean exists = dao.isCommentExist(24779, 14316);
		Log.debug(exists);
	}
	
	@Test
	public void queryAllAuthorIdTest() {
		List<Integer> list = dao.queryAllAuthorId(14316, 20);
		logList(list);
	}
}
