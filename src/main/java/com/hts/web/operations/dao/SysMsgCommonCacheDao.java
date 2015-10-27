package com.hts.web.operations.dao;

import java.util.List;

import com.hts.web.common.dao.BaseCacheDao;
import com.hts.web.common.pojo.OpSysMsgDto;

/**
 * 公用系统消息缓存数据访问接口
 * 
 * @author lynch 2015-10-25
 *
 */
public interface SysMsgCommonCacheDao extends BaseCacheDao {

	public List<OpSysMsgDto> queryMsg(Integer limit);
	
	public List<OpSysMsgDto> queryMsg(Integer maxId, Integer limit);
	
	/**
	 * 查询比指定id更大的消息数量
	 * 
	 * @param id
	 * @return
	 */
	public Integer higherCount(Integer id);
	

}
