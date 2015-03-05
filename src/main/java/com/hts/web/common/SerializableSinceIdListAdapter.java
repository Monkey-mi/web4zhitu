package com.hts.web.common;

import java.io.Serializable;
import java.util.List;

import com.hts.web.base.database.RowSelection;

public interface SerializableSinceIdListAdapter<T extends Serializable> extends
		SerializableListAdapter<T> {
	
	/**
	 * 获取序列化对象列表
	 * 
	 * @param sinceId
	 * @param rowSelection
	 * @return
	 */
	public List<T> getSerializableBySinceId(int sinceId, RowSelection rowSelection);
	
	/**
	 * 获取序列化对象总数
	 * 
	 * @param sinceId
	 * @return
	 */
	public long getTotalBySinceId(int sinceId);

}
