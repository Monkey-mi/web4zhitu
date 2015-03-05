package com.hts.web.common.service;


/**
 * <p>
 * 主键生成业务逻辑访问接口
 * </p>
 * 
 * 创建时间：2013-8-3
 * @author ztj
 *
 */
public interface KeyGenService extends BaseService {

	/**
	 * 生成id
	 * @param keyId
	 * @return
	 */
	public Integer generateId(Integer keyId);
}
