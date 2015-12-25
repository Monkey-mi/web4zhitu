package com.hts.web.operations.dao.mongo;

import java.util.List;

import com.hts.web.common.dao.BaseMongoDao;
import com.hts.web.common.pojo.AddrDistrictDto;

/**
 * 附近区域
 * @author zxx 2015-12-25 14:57:34
 *
 */
public interface NearDistrictMongoDao  extends BaseMongoDao{
	/**
	 * 增加
	 * @param dto
	 * @author zxx 2015-12-25 14:59:55
	 */
	public void insertDistrict(AddrDistrictDto dto);
	
	/**
	 * 删除
	 * @param id
	 * @author zxx 2015-12-25 14:59:55
	 */
	public void deleteDistrict(Integer id);
	
	/**
	 * 查询
	 * @param cityId
	 * @return
	 * @author zxx 2015-12-25 14:59:55
	 */
	public List<AddrDistrictDto> queryDistrict(Integer cityId);
	
	/**
	 * 根据id查询区域
	 * @param id
	 * @return
	 * @author zxx 2015-12-25 14:59:55
	 */
	public AddrDistrictDto queryDistrictById(Integer id);
	
}
