package com.hts.web.ztworld.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.hts.web.base.database.RowCallback;
import com.hts.web.base.database.RowSelection;
import com.hts.web.common.dao.BaseDao;
import com.hts.web.common.pojo.HTWorldLabel;

/**
 * <p>
 * 织图标签数据访问接口
 * </p>
 * 
 * 创建时间：2014-1-11
 * @author ztj
 *
 */
public interface HTWorldLabelDao extends BaseDao {

	/**
	 * 保存标签
	 * 
	 * @param label
	 */
	public void saveLabel(HTWorldLabel label);
	
	/**
	 * 根据名字查询标签
	 * 
	 * @param name
	 * @return
	 */
	public HTWorldLabel queryLabelByName(String name);
	
	/**
	 * 根据标签查询id映射
	 * 
	 * @param names
	 * @return
	 */
	public Map<String, HTWorldLabel> queryLabelByNames(String[] names);
	
	
	/**
	 * 根据名字模糊搜索标签
	 * 
	 * @param name
	 * @return
	 */
	public List<HTWorldLabel> queryFuzzyLabelByName(String name, RowSelection rowSelection);
	
	/**
	 * 根据id查询织图总数
	 * 
	 * @param labelId
	 * @return
	 */
	public Integer queryWorldCount(Integer labelId);
	
	/**
	 * 更新织图总数
	 * 
	 * @param labelId
	 */
	public void updateWorldCount(Integer labelId, int count);
	
	/**
	 * 构建{@link HTWorldLabel}
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public HTWorldLabel buildLabel(ResultSet rs) throws SQLException;
	
	/**
	 * 查询标签
	 * 
	 * @param ids
	 * @param callback
	 * @throws Exception
	 */
	public void queryLabel(Integer[] ids, RowCallback<HTWorldLabel> callback) throws Exception;

}
