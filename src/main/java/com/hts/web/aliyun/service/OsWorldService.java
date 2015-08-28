package com.hts.web.aliyun.service;

/**
 * <p>
 * 织图OpenSearch业务逻辑访问接口
 * </p>
 * 
 * 创建时间: 2015-08-25
 * @author lynch
 *
 */
public interface OsWorldService {

	/**
	 * 保存织图信息
	 * 
	 * @param id
	 * @param longitude
	 * @param latitude
	 * @param locationDesc
	 * @param province
	 * @param city
	 */
	public void saveWorld(Integer id, Double longitude, 
			Double latitude, String locationDesc, String province, 
			String city);

	/**
	 * 提交限定数量的操作
	 * 
	 * @throws Exception
	 */
	public void commitOpts(Integer limit) throws Exception;
	
}
