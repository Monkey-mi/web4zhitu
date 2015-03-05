package com.hts.web.userinfo.dao;

import java.util.Date;

import com.hts.web.common.dao.BaseDao;
import com.hts.web.common.pojo.RetrievePasswordDto;

/**
 * 
 * 找回密码dao
 * @time 2014年5月5日 14:25:56
 * @author zxx
 *
 */

public interface UserRetrievePWDDao extends BaseDao {
	/**
	 * 查看找回密码表中是否存在
	 * @param userId
	 * @return
	 */
	public boolean checkRPWDIsExist(String loginCode);
	
	/**
	 * 查看用户帐户是否存在
	 * @param userId
	 * @return
	 */
	public boolean checkUserInfoIsExist(String loginCode);
	
	/**
	 * 根据用户id查询找回密码表中的信息
	 * @param userId
	 * @return
	 */
	public RetrievePasswordDto queryRetrievePasswordByLoginCode(String loginCode);
	
	/**
	 * 重置密码
	 * @param userId
	 * @param pwd
	 */
	public void resetPWD(Integer userId,byte[] pwd);
	
	/**
	 * 删除过期的rpwd
	 * @param endTime
	 */
	public void deleteRPWDByTime(Date endTime);
	
	/**
	 * 增加rpwd
	 */
	public void saveRPWD(RetrievePasswordDto retrievePasswordDto);
	
	/**
	 * 获取登陆账号
	 */
	public String getLoginCodeByUID(Integer userId);
	
	/**
	 * 删除RPWD by loginCode
	 */
	public void deleteRPWDByLoginCode(String loginCode);
	
	/**
	 * 根据logincode获取userId	
	 * @param loginCode
	 * @return
	 */
	public String getUserIdByLoginCode(String loginCode);
	
	/**
	 * 根据logincode来重置密码
	 * @param loginCode
	 * @param pwd
	 */
	public void resetPWD(String loginCode,byte[] pwd);
	
	/**
	 * 根据loginCode查找用户名
	 * @param loginCode
	 * @return
	 */
	public String getUserNameByLoginCode(String loginCode);
}
