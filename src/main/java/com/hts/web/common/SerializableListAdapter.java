package com.hts.web.common;

import java.io.Serializable;
import java.util.List;

import com.hts.web.base.database.RowSelection;

public interface SerializableListAdapter<T extends Serializable> {

	/**
	 * 获取序列化对象列表
	 * 
	 * @param rowSelection
	 * @return
	 * @throws Exception 
	 */
	public List<T> getSerializables(RowSelection rowSelection) throws Exception;
	
	/**
	 * 获取序列化对象列表
	 * 
	 * @param maxId
	 * @param rowSelection
	 * @return
	 */
	public List<T> getSerializableByMaxId(int maxId, RowSelection rowSelection);
	
	/**
	 * 获取序列化对象总数
	 * 
	 * @param maxId
	 * @return
	 * @throws Exception 
	 */
	public long getTotalByMaxId(int maxId) throws Exception;

}
