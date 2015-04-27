package com.hts.web.operations.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.hts.web.base.database.RowSelection;
import com.hts.web.common.dao.BaseDao;
import com.hts.web.common.pojo.OpWorldType;
import com.hts.web.common.pojo.OpWorldTypeDto;
import com.hts.web.common.pojo.OpWorldTypeDto2;


/**
 * <p>
 * 广场织图数据访问接口
 * </p>
 * 
 * 创建时间：2013-8-3
 * @author ztj
 *
 */
public interface SquarePushDao extends BaseDao {
	
	/**
	 * 查询广场织图
	 * 
	 * @param rowSelection
	 * @return
	 */
	public List<OpWorldTypeDto> querySquare(RowSelection rowSelection);
	
	/**
	 * 查询广场织图
	 * 
	 * @param maxSerial
	 * @param rowSelection
	 * @return
	 */
	public List<OpWorldTypeDto> querySquareByMaxSerial(int maxSerial, RowSelection rowSelection);
	
	/**
	 * 查询广场织图
	 * 
	 * @param minSerial
	 * @param rowSelection
	 * @return
	 */
	public List<OpWorldTypeDto> querySquareByMinSerial(int minSerial, RowSelection rowSelection);
	
	/**
	 * 查询广场织图总数
	 * 
	 * @param maxSerial
	 * @return
	 */
	public long querySquareCountByMaxSerial(int maxSerial);
	
	/**
	 * 查询广场织图总数
	 * 
	 * @param minSerial
	 * @return
	 */
	public long querySquareCountByMinId(int minSerial);
	
	/**
	 * 查询广场织图
	 * 
	 * @param typeId
	 * @param joinId
	 * @param rowSelection
	 * @return
	 */
	public List<OpWorldTypeDto> querySquare(int typeId, Integer joinId, RowSelection rowSelection);
	
	/**
	 * 查询广场织图
	 * 
	 * @param maxSerial
	 * @param typeId
	 * @param joinId
	 * @param rowSelection
	 * @return
	 */
	public List<OpWorldTypeDto> querySquareByMaxId(int maxSerial, int typeId, Integer joinId, RowSelection rowSelection);
	
	/**
	 * 查询广场织图总数
	 * 
	 * @param typeId
	 * @return
	 */
	public long querySquareCount(int typeId);
	
	/**
	 * 查询广场织图总数
	 * 
	 * @param maxSerial
	 * @param typeId
	 * @return
	 */
	public long querySquareCountByMaxId(int maxSerial, int typeId);
	
	/**
	 * 查询精品广场织图
	 * 
	 * @param joinId
	 * @param rowSelection
	 * @return
	 */
	public List<OpWorldTypeDto> querySuperbTypeSquare(Integer joinId, RowSelection rowSelection);

	/**
	 * 查询精品广场织图
	 * 
	 * @param maxSerial
	 * @param joinId
	 * @param rowSelection
	 * @return
	 */
	public List<OpWorldTypeDto> querySuperbTypeSquare(Integer maxSerial, Integer joinId, RowSelection rowSelection);
	
	/**
	 * 查询精品广场织图V2
	 * 
	 * @param joinId
	 * @param rowSelection
	 * @return
	 */
	public List<OpWorldTypeDto> querySuperbTypeSquareV2(Integer joinId, RowSelection rowSelection);
	
	
	/**
	 * 查询精品广场织图V2
	 * 
	 * @param maxSerial
	 * @param joinId
	 * @param rowSelection
	 * @return
	 */
	public List<OpWorldTypeDto> querySuperbTypeSquareV2(Integer maxSerial, Integer joinId, RowSelection rowSelection);
	
	/**
	 * 查询普通广场织图
	 * 
	 * @param typeId
	 * @param joinId
	 * @param rowSelection
	 * @return
	 */
	public List<OpWorldTypeDto> queryNormalSquare(Integer typeId, Integer joinId, RowSelection rowSelection);
	
	/**
	 * 查询普通广场织图
	 * 
	 * @param maxSerial
	 * @param typeId
	 * @param joinId
	 * @param rowSelection
	 * @return
	 */
	public List<OpWorldTypeDto> queryNormalSquareByMaxSerial(int maxSerial, Integer typeId, Integer joinId, 
			RowSelection rowSelection);
	
	
	/**
	 * 查询广场织图总数
	 * 
	 * @param maxSerial
	 * @param typeId
	 * @return
	 */
	public long queryNormalSquareCountByMaxSerial(int maxSerial, Integer typeId);

	/**
	 * 查询广场织图
	 * 
	 * @param rowSelection
	 * @param label
	 * @return
	 */
	public List<OpWorldTypeDto> querySquare(RowSelection rowSelection, String label[]);

	/**
	 * 查询广场织图
	 * 
	 * @param maxSerial
	 * @param rowSelection
	 * @param label
	 * @return
	 */
	public List<OpWorldTypeDto> querySquareByMaxId(int maxSerial,
			RowSelection rowSelection, String label[]);

	/**
	 * 查询广场织图
	 * 
	 * @param minSerial
	 * @param rowSelection
	 * @param label
	 * @return
	 */
	public List<OpWorldTypeDto> querySquareByMinId(int minSerial,
			RowSelection rowSelection, String label[]);

	/**
	 * 查询广场织图总数
	 * 
	 * @param maxSerial
	 * @param label
	 * @return
	 */
	public long querySquareCountByMaxId(int maxSerial, String label[]);

	/**
	 * 查询广场织图总数
	 * 
	 * @param minSerial
	 * @param label
	 * @return
	 */
	public long querySquareCountByMinId(int minSerial, String label[]);
	
	/**
	 * 随机获取广场织图
	 * 
	 * @param limit
	 * @return
	 */public List<OpWorldTypeDto> queryRandomSquare(int limit);
	
	/**
	 * 查询最大广场织图序号
	 * 
	 * @return
	 */
	public Integer queryMaxSquareSerial();
	
	/**
	 * 从主库查询广场精品
	 * 
	 * @param rowSelection
	 * @return
	 */
	public List<OpWorldTypeDto2> querySuperbSquareIndexFromMaster(RowSelection rowSelection);
	
	/**
	 * 查询广场精品分类索引
	 * 
	 * @param rowSelection
	 * @return
	 */
	public List<OpWorldTypeDto2> querySuperbSquareIndex(RowSelection rowSelection);
	
	/**
	 * 查询广场精品分类索引
	 * 
	 * @param maxSerial
	 * @param rowSelection
	 * @return
	 */
	public List<OpWorldTypeDto2> querySuperbSquareIndex(Integer maxSerial, RowSelection rowSelection);
	
	/**
	 * 查询广场分类索引
	 * 
	 * @param limit
	 * @param superbLimit
	 * @param labels
	 * @return
	 */
	public List<OpWorldTypeDto2> querySquarePushIndex(int limit,int superbLimit,List<OpWorldType> labels);
	
	/**
	 * 查询广场分类索引
	 * 
	 * @param limit
	 * @param labels
	 * @return
	 */
	public List<OpWorldTypeDto2> querySquarePushIndex(int limit,List<OpWorldType> labels);
	
	/**
	 * 构建OpWorldTypeDto
	 * 
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public OpWorldTypeDto buildSquareDto(ResultSet rs) throws SQLException;
	
}

