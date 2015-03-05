package com.hts.web.operations.dao;

import java.util.List;

import com.hts.web.base.database.RowSelection;
import com.hts.web.common.dao.BaseDao;
import com.hts.web.common.pojo.OpWorldTypeDto;

/**
 * <p>
 * 小秘书织图数据访问接口
 * </p>
 * 
 * 创建时间：2014-5-24
 * @author tianjie
 *
 */
public interface XiaoMiShuWorldDao extends BaseDao {

	public List<OpWorldTypeDto> queryWorldTypeDto(Integer userId, Integer joinId, RowSelection rowSelection);
	
}
