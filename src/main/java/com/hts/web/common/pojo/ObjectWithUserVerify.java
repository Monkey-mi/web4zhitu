package com.hts.web.common.pojo;

/**
 * <p>
 * 拥有用户认证对象标识接口
 * </p>
 * 
 * 创建时间：2014-7-18
 * @author tianjie
 *
 */
public interface ObjectWithUserVerify {

	/**
	 * 获取认证id
	 * 
	 * @return
	 */
	public Integer getVerifyId();
	
	/**
	 * 设置认证名称
	 * 
	 * @param verifyName
	 * @return
	 */
	public void setVerifyName(String verifyName);
	
	/**
	 * 设置认证icon
	 * 
	 * @param verifyIcon
	 * @return
	 */
	public void setVerifyIcon(String verifyIcon);
}
