package com.hts.web.operations.service;

import java.util.Map;

import com.hts.web.common.pojo.OpAdAppLink;
import com.hts.web.common.service.BaseService;

/**
 * <p>
 * 广告运营业务逻辑访问接口
 * </p>
 * 
 * 创建时间：2013-11-30
 * @author ztj
 *
 */
public interface AdOperationsService extends BaseService {

	/**
	 * 从访问路径获取App链接
	 * 
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public String addAppLinkRecordFromURL(String url, String ip) throws Exception;
	
	/**
	 * 根据User-Agent获取访问链接
	 * @param userAgent
	 * @param link
	 * @return
	 */
	public String getAppURLByUserAgent(String userAgent, OpAdAppLink link);
	
	/**
	 * 构建App链接列表
	 * 
	 * @param phoneCode
	 * @param maxId
	 * @param start
	 * @param limit
	 * @param jsonMap
	 * @throws Exception
	 */
	public void buildAppLink(Integer phoneCode, int maxId, int start, int limit, Map<String, Object> jsonMap) throws Exception;
	
}
