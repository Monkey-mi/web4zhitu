package com.hts.web.operations.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.hts.web.base.constant.Tag;
import com.hts.web.base.database.HTS;
import com.hts.web.base.database.RowSelection;
import com.hts.web.common.dao.impl.BaseDaoImpl;
import com.hts.web.common.pojo.OpWorldTypeDto;
import com.hts.web.operations.dao.SquarePushDao;
import com.hts.web.operations.dao.XiaoMiShuWorldDao;
import com.hts.web.userinfo.dao.impl.UserInfoDaoImpl;

@Repository("HTSXiaoMiShuWorldDao")
public class XiaoMiShuWorldDaoImpl extends BaseDaoImpl implements
		XiaoMiShuWorldDao {

	private static final String QUERY_WORLD = "select '0' as serial, h.id as world_id, '1' as superb, '1' as type_id,"
			+ " h.*,1-ISNULL(hl.user_id) as liked, 1-ISNULL(hk.user_id) as keep from"
			+ " (select h0.*," + U0_INFO + " from " + HTS.HTWORLD_HTWORLD + " as h0, " + HTS.USER_INFO + " as u0" 
			+ " where h0.author_id=u0.id and h0.author_id=? and h0.valid=1 order by h0.id desc LIMIT ?,?) as h"
			+ " left join (select * from " + HTS.HTWORLD_LIKED + " hl0 where hl0.valid=1 and hl0.user_id=?) as hl"
			+ " on h.id = hl.world_id"
			+ " left join (select * from " + HTS.HTWORLD_KEEP + " hk0 where hk0.valid=1 and hk0.user_id=?) as hk"
			+ " on h.id = hk.world_id";
	
	@Autowired
	private SquarePushDao squarePushDao;

	@Override
	public List<OpWorldTypeDto> queryWorldTypeDto(Integer userId, Integer joinId,
			RowSelection rowSelection) {
		return getJdbcTemplate().query(QUERY_WORLD, 
				new Object[]{userId, rowSelection.getFirstRow(), rowSelection.getLimit(), joinId, joinId},
				new RowMapper<OpWorldTypeDto>() {

			@Override
			public OpWorldTypeDto mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				OpWorldTypeDto dto =  squarePushDao.buildSquareDto(rs);
				dto.setLiked(rs.getObject("liked"));
				dto.setKeep(rs.getObject("keep"));
				if(dto.getAuthorId() != 0) {
					dto.setUserInfo(UserInfoDaoImpl.buildUserInfoDtoByResult(dto.getAuthorId(), rs));
				}
				dto.setIsTutorial(Tag.TRUE);
				return dto;
			}
			
		});
	}

}
