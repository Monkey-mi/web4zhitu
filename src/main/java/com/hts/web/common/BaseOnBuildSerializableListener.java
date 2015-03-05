package com.hts.web.common;

import java.io.Serializable;
import java.util.List;

public interface BaseOnBuildSerializableListener {

	/**
	 * 首次构建
	 */
	public void onBuild(List<? extends Serializable> list, Serializable maxSerializable, long total);

	/**
	 * 根据maxId构建
	 */
	public void onBuildByMaxId(List<? extends Serializable> list, Serializable maxSerializable, long total);
}
