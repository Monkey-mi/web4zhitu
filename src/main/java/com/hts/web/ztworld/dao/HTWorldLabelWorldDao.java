package com.hts.web.ztworld.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.hts.web.base.database.RowCallback;
import com.hts.web.base.database.RowSelection;
import com.hts.web.common.dao.BaseDao;
import com.hts.web.common.pojo.HTWorldInteractDto;
import com.hts.web.common.pojo.HTWorldLabelWorld;
import com.hts.web.common.pojo.HTWorldLabelWorldAuthor;

/**
 * <p>
 * 标签-织图数据访问接口
 * </p>
 * 
 * @author tianjie
 *
 */
public interface HTWorldLabelWorldDao extends BaseDao {

	/**
	 * 保存标签织图
	 * 
	 * @param labelWorld
	 */
	public void saveLabelWorld(HTWorldLabelWorld labelWorld);
	
	/**
	 * 根据标签id查询织图总数
	 * 
	 * @param labelId
	 * @return
	 */
	public long queryWorldCountByLabelId(Integer labelId);
	
	/**
	 * 查询标签织图列表
	 * 
	 * @param rowSelection
	 * @return
	 */
	public List<HTWorldInteractDto> queryLabelWorld(Integer joinId, Integer labelId,
			RowSelection rowSelection);
	
	/**
	 * 查询标签织图列表
	 * 
	 * @param joinId
	 * @param maxId
	 * @param rowSelection
	 * @return
	 */
	public List<HTWorldInteractDto> queryLabelWorld(int maxId, Integer joinId, Integer labelId,
			RowSelection rowSelection);
	
	/**
	 * 查询标签织图总数
	 * 
	 * @param maxId
	 * @param labelId
	 * @return
	 */
	public long queryLabelWorldCount(int maxId, Integer labelId);
	
	/**
	 * 查询标签织图，根据标签序号排序
	 * 
	 * @param joinId
	 * @param labelId
	 * @param rowSelection
	 * @return
	 */
	public List<HTWorldInteractDto> queryLabelWorldV2(Integer joinId,
			Integer labelId, RowSelection rowSelection);

	/**
	 * 查询标签织图，根据标签序号排序
	 * @param maxSerial
	 * @param joinId
	 * @param labelId
	 * @param rowSelection
	 * @return
	 */
	public List<HTWorldInteractDto> queryLabelWorldV2(int maxSerial, Integer joinId,
			Integer labelId, RowSelection rowSelection);

	/**
	 * 查询标签精品织图
	 * 
	 * @param labelId
	 * @param rowSelection
	 * @return
	 * @author lynch 2015-09-14
	 */
	public List<HTWorldInteractDto> queryLabelSuperbWorld(Integer labelId,
			RowSelection rowSelection);
	
	/**
	 * 查询标签精品织图
	 * 
	 * @param maxSerial
	 * @param labelId
	 * @param rowSelection
	 * @return
	 * @author lynch 2015-09-14
	 */
	public List<HTWorldInteractDto> queryLabelSuperbWorld(Integer maxSerial, Integer labelId,
			RowSelection rowSelection);
	
	/**
	 * 查询标签织图
	 * 
	 * @param maxSerial
	 * @param labelId
	 * @return
	 */
	public long queryLabelWorldCountV2(int maxSerial, Integer labelId);
	
	/**
	 * 查询标签织图作者
	 * 
	 * @param labelId
	 * @param joinId
	 * @return
	 */
	public List<HTWorldLabelWorldAuthor> queryLabelWorldAuthor(Integer labelId, Integer joinId, 
			RowSelection rowSelection);
	
	/**
	 * 查询标签织图作者
	 * 
	 * @param maxId
	 * @param labelId
	 * @param joinId
	 * @return
	 */
	public List<HTWorldLabelWorldAuthor> queryLabelWorldAuthor(int maxId, Integer labelId,
			Integer joinId, RowSelection rowSelection);
	
	
	/**
	 * 查询标签参与者
	 * 
	 * @param joinId
	 * @param activityIds
	 * @param limit
	 * @param callback
	 */
	public void queryParticipatorByLimit(Integer joinId, Integer[] activityIds, 
			Integer limit, final RowCallback<HTWorldLabelWorldAuthor> callback);
	
	
	public HTWorldLabelWorldAuthor buildLabelWorldAuthor(ResultSet rs) throws SQLException;
	
	
}
