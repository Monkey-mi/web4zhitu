package com.hts.web.ztworld.dao;

import java.util.List;

import com.hts.web.base.database.RowSelection;
import com.hts.web.common.dao.BaseDao;
import com.hts.web.common.pojo.HTWorldSubtitleDto;

/**
 * <p>
 * 字幕数据访问接口
 * </p>
 * 
 * 创建时间:2015-03-12
 * @author lynch
 *
 */
public interface SubtitleDao extends BaseDao {

	/**
	 * 查询字幕列表
	 * 
	 * @param rowSelection
	 * @return
	 */
	public List<HTWorldSubtitleDto> querySubtitleDto(RowSelection rowSelection);
	
	/**
	 * 查询字幕列表
	 * 
	 * @param maxId
	 * @param rowSelection
	 * @return
	 */
	public List<HTWorldSubtitleDto> querySubtitleDto(Integer maxId, RowSelection rowSelection);
}
