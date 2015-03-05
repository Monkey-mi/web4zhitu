package com.hts.web.operations.dao;

import java.util.List;

import com.hts.web.common.dao.BaseCacheDao;
import com.hts.web.common.pojo.OpChannelTopOne;

/**
 * <p>
 * 频道TopOne人物缓存数据访问接口
 * </p>
 * 
 * 创建时间:2014-10-31
 * @author lynch
 *
 */
public interface ChannelTopOneCacheDao extends BaseCacheDao {

	public List<OpChannelTopOne> queryTopOne();
	
	public OpChannelTopOne queryMaxTopOne();
}
