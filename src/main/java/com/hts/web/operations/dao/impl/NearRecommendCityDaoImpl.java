package com.hts.web.operations.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.hts.web.common.dao.impl.BaseDaoImpl;
import com.hts.web.common.pojo.AddrCity;
import com.hts.web.common.pojo.OpNearCityGroupDto;
import com.hts.web.operations.dao.NearRecommendCityDao;
@Repository("NearRecommendCityDao")
public class NearRecommendCityDaoImpl extends BaseDaoImpl implements NearRecommendCityDao{

	private static final String QUERY_RECOMMEND_CITY_GROUP = "select ac.* from hts.operations_near_recommend_city rc left join hts.addr_city ac on rc.city_id=ac.id where rc.city_group_id=? order by serial desc";
	private static final String QUERY_CITY_GROUP = "select * from hts.operations_near_city_group order by serial desc";
	@Override
	public List<AddrCity> queryNearRecommendCityByGroupId(Integer cityGroupId) {
		return getJdbcTemplate().query(QUERY_RECOMMEND_CITY_GROUP, new Object[]{cityGroupId}, new RowMapper<AddrCity>(){
			@Override
			public AddrCity mapRow(ResultSet rs,int rowNum)throws SQLException{
				return buildAddrCity(rs);
			}
		});
	}
	
	private AddrCity buildAddrCity(ResultSet rs)throws SQLException{
		AddrCity city = new AddrCity();
		city.setId(rs.getInt("id"));
		city.setName(rs.getString("name"));
		city.setLatitude(rs.getDouble("latitude"));
		city.setLongitude(rs.getDouble("longitude"));
		city.setShortName(rs.getString("short_name"));
		city.setRadius(rs.getFloat("radius"));
		return city;
	}

	@Override
	public List<OpNearCityGroupDto> queryNearCityGroup() {
		
		return getJdbcTemplate().query(QUERY_CITY_GROUP, new RowMapper<OpNearCityGroupDto>(){
			@Override
			public OpNearCityGroupDto mapRow(ResultSet rs , int rowNum)throws SQLException{
				OpNearCityGroupDto dto = new OpNearCityGroupDto();
				dto.setId(rs.getInt("id"));
				dto.setDescription(rs.getString("description"));
				dto.setSerial(rs.getInt("serial"));
				return dto;
			}
		});
	}

}
