package com.hts.web.ztworld.dao.impl;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

import com.hts.web.base.database.HTS;
import com.hts.web.common.dao.impl.BaseDaoImpl;
import com.hts.web.common.pojo.HTWorldComment;
import com.hts.web.ztworld.dao.CommentWeekDao;

@Repository("HTSCommentWeekDao")
public class CommentWeekDaoImpl extends BaseDaoImpl implements CommentWeekDao {

	
	private static final String table = HTS.HTWORLD_COMMENT_WEEK;
	
	private static final String SAVE_COMMENT = "insert into " + table 
			+ "(id, author_id, content, comment_date, world_id," 
			+ "world_author_id,re_author_id) values (?,?,?,?,?,?,?)";
	
	private static final String DELETE_BY_ID = "delete from " + table
			+ " where id=?";
	
	private static final String QUERY_WORLD_ID = "select world_id from " + table
			+ " where id=?";
	
	@Override
	public void saveComment(HTWorldComment comm) {
		getMasterJdbcTemplate().update(SAVE_COMMENT, new Object[]{
				comm.getId(),
				comm.getAuthorId(),
				comm.getContent(),
				comm.getCommentDate(),
				comm.getWorldId(),
				comm.getWorldAuthorId(),
				comm.getReAuthorId(),
		});
	}
	
	@Override
	public void delComment(Integer id) {
		getMasterJdbcTemplate().update(DELETE_BY_ID, id);
	}

	@Override
	public Integer queryWorldId(Integer id) {
		try {
			return getJdbcTemplate().queryForInt(QUERY_WORLD_ID, id);
		} catch(EmptyResultDataAccessException e) {
			return -1;
		}
	}
}
