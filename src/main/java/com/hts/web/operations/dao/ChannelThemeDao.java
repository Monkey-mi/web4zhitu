package com.hts.web.operations.dao;

import java.util.List;

import com.hts.web.common.dao.BaseDao;
import com.hts.web.common.pojo.OpChannelTheme;

/**
 * <p>
 * 频道专题数据访问接口
 * </p>
 * 
 * 创建时间: 2015-05-27
 * @author lynch
 *
 */
public interface ChannelThemeDao extends BaseDao {

	/**
	 * 查询所有专题
	 * 
	 * @return
	 */
	public List<OpChannelTheme> queryAllTheme();
	
}