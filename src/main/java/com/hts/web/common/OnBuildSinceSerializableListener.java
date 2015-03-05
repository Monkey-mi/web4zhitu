package com.hts.web.common;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 构建序有SinceId列表监听
 * </p>
 * 
 * 创建时间
 * 
 * @author ztj
 * 
 */
public interface OnBuildSinceSerializableListener {
	
	/**
	 * 首次构建
	 */
	public void onBuild(List<? extends Serializable> list, long total);

	/**
	 * 根据maxId构建
	 */
	public void onBuildByMaxId(List<? extends Serializable> list, long total);

	/**
	 * 根据sinceId构建
	 */
	public void onBuildBySinceId(List<? extends Serializable> list, long total);
}