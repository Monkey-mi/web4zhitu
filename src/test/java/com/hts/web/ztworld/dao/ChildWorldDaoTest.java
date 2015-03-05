package com.hts.web.ztworld.dao;

import java.sql.SQLException;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.BaseTest;
import com.hts.web.base.database.RowSelection;
import com.hts.web.common.pojo.HTWorldChildWorldDto;
import com.hts.web.common.util.Log;

/**
 * <p>
 * 子世界数据访问对象单元测试
 * </p>
 * 
 * 创建时间：2012-11-02
 * @author ztj
 *
 */
public class ChildWorldDaoTest extends BaseTest {

	@Autowired
	private HTWorldChildWorldDao dao;
	
	@Test
	public void testQueryShowChildWorldDtosByWorldId() throws SQLException {
		RowSelection selection = new RowSelection(1, 2);
		List<HTWorldChildWorldDto> dtoList = dao.queryShowChildWorldDtosByWorldId(1,selection);
		Log.debug(dtoList);
	}
	
	@Test
	public void testQueryChildWorldCountByWorldId() throws SQLException {
		Long count = dao.queryChildWorldCountByWorldId(1);
		Log.debug("total:" + count);
	}
}
