package com.hts.web.aliyun.service;


public interface OsUserInfoService {

	/**
	 * 保存用户
	 * 
	 * @param id
	 * @param userName
	 * @param userAvatar
	 * @param signature
	 * @param platformSign
	 * @param star
	 * @throws Exception
	 */
	public void saveUser(Integer id, String userName,
			String userAvatar, String signature, String platformSign,
			Integer star, Integer activity) throws Exception;

	/**
	 * 更新用户信息
	 * 
	 * @param id
	 * @param userName
	 * @param userAvatar
	 * @param signature
	 * @param platformSign
	 * @param star
	 * @throws Exception
	 */
	public void updateUser(Integer id, String userName,
			String userAvatar, String signature, String platformSign,
			Integer star, Integer activity) throws Exception;
	
	/**
	 * 更新用户信息，忽略空值
	 * 
	 * @param id
	 * @param userName
	 * @param userAvatar
	 * @param signature
	 * @param platformSign
	 * @param star
	 * @throws Exception
	 */
	public void updateUserWithoutNULL(Integer id, String userName,
			String userAvatar, String signature, String platformSign,
			Integer star, Integer activity) throws Exception;
	
	/**
	 * 更新最后登录时间
	 * 
	 * @param id
	 * @param lastLogin
	 * @throws Exception
	 */
	public void updateLastLogin(Integer id, Integer lastLogin) throws Exception;
	
	/**
	 * 提交更新操作
	 * 
	 * @throws Exception
	 */
	public void commitOpt() throws Exception;
	
}
