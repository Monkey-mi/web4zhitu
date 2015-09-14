package com.hts.web.ztworld.service;

import java.util.Map;

import com.hts.web.base.database.RowCallback;
import com.hts.web.common.pojo.HTWorldLabel;
import com.hts.web.common.service.BaseService;

/**
 * <p>
 * 织图标签业务逻辑访问接口
 * </p>
 * 
 * 创建时间：2014-5-5
 * @author tianjie
 *
 */
public interface ZTWorldLabelService extends BaseService {

	/**
	 * 保存标签
	 * 
	 * @param name
	 * @return
	 * @throws Exception
	 */
	public HTWorldLabel saveLabel(String name) throws Exception;
	
	/**
	 * 构建模糊搜索标签列表
	 * 
	 * @param name
	 * @param limit
	 * @param jsonMap
	 */
	public void buildFuzzyLabel(String name, int limit, Map<String, Object> jsonMap) throws Exception;
	
	/**
	 * 查询热门标签
	 * 
	 * @param jsonMap
	 */
	public void buildHotLabel(Map<String, Object> jsonMap) throws Exception ;
	
	/**
	 * 查询活动标签
	 * 
	 * @param jsonMap
	 */
	public void buildActivityLabel(Map<String, Object> jsonMap) throws Exception;
	
	/**
	 * 构建标签织图列表
	 * 
	 * @param isOrderBySerial
	 * @param labelName
	 * @param joinId
	 * @param maxId
	 * @param start
	 * @param limit
	 * @param jsonMap
	 * @param trimTotal
	 * @param trimExtras
	 * @param commentLimit
	 * @param likedLimit
	 * @return
	 */
	public void buildLabelWorld(boolean isOrderBySerial, String labelName, boolean trimValid, 
			Integer joinId, int maxId, int start, int limit, Map<String, Object> jsonMap,boolean trimTotal, 
			boolean trimExtras, int commentLimit, int likedLimit) throws Exception;
	
	/**
	 * 构建标签信息
	 * 
	 * @param labelName
	 * @param jsonMap
	 * @throws Exception
	 */
	public void buildLabel(String labelName, Map<String, Object> jsonMap) throws Exception;
	
	/**
	 * 构建标签织图列表
	 * 
	 * @param isOrderBySerial
	 * @param labelId
	 * @param joinId
	 * @param maxId
	 * @param start
	 * @param limit
	 * @param jsonMap
	 * @param trimTotal
	 * @param trimExtras
	 * @param commentLimit
	 * @param likedLimit
	 * @throws Exception
	 */
	public void buildLabelWorld(boolean isOrderBySerial, Integer labelId, Integer joinId, int maxId,
			int start, int limit, Map<String, Object> jsonMap,boolean trimTotal, 
			boolean trimExtras, int commentLimit, int likedLimit) throws Exception;
	
	/**
	 * 构建精选织图
	 * 
	 * @param labelName
	 * @param joinId
	 * @param maxId
	 * @param start
	 * @param limit
	 * @param jsonMap
	 * @param trimTotal
	 * @param trimExtras
	 * @param commentLimit
	 * @param likedLimit
	 * @throws Exception
	 * 
	 * @author lynch 2015-09-14
	 */
	public void buildLabelSuperbWorld(String labelName, final Integer joinId, int maxId,
			int start, int limit, Map<String, Object> jsonMap,
			final boolean trimTotal, final boolean trimExtras, final int commentLimit,
			final int likedLimit) throws Exception;
	
	/**
	 * 构建标签织图用户列表
	 * 
	 * @param labelId
	 * @param joinId
	 * @param maxId
	 * @param start
	 * @param limit
	 * @param jsonMap
	 * @throws Exception
	 */
	public void buildLabelWorldAuthor(Integer labelId, Integer joinId, 
			int maxId, int start, int limit, Map<String, Object> jsonMap) throws Exception;
	
}
