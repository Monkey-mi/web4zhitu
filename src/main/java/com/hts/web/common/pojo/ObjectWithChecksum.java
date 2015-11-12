package com.hts.web.common.pojo;

/**
 * UID加密标志接口
 * 
 * @author lynch　2015-11-12
 *
 */
public interface ObjectWithChecksum {

	/**
	 * 获取用户id
	 * 
	 * @return
	 */
	public Integer getChecksumUID();
	
	/**
	 * UID加密
	 * 
	 * @param uid
	 */
	public void setChecksum(Integer checksum);
	
}
