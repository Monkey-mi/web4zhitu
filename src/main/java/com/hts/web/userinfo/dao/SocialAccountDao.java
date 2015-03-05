package com.hts.web.userinfo.dao;

import java.util.List;

import com.hts.web.common.dao.BaseDao;
import com.hts.web.common.pojo.UserSocialAccount;
import com.hts.web.common.pojo.UserSocialAccountDto;

/**
 * <p>
 * 用户社交账号绑定表
 * </p>
 * 
 * 创建时间：2013-8-3
 * @author ztj
 *
 */
public interface SocialAccountDao extends BaseDao {

	/**
	 * 根据用户id查询其绑定的社交账号
	 * @return
	 */
	public List<UserSocialAccount> querySocialAccountByUserId(Integer userId);
	
	/**
	 * 根据用户id查询其绑定的社交账号
	 * @param userId
	 * @return
	 */
	public List<UserSocialAccountDto> querySocialAccountDtoByUserId(Integer userId);

	/**
	 * 查询社交账号
	 * @param conn
	 * @param userId
	 * @param platformCode
	 */
	public UserSocialAccount querySocialAccountIdByPlatformCode(Integer userId, Integer platformCode);
	
	/**
	 * 保存绑定的社交账号
	 */
	public Integer saveSocialAccount(UserSocialAccount socialAccount);
	
	/**
	 * 更新社交账号
	 * @param conn
	 * @param socialAccount
	 */
	public void udpateSocialAccount(UserSocialAccount socialAccount);
	
	
	public void deleteByPlatformCode(Integer platformCode, Integer userId);
	
	/**
	 * 查询用户绑定的社交平台列表
	 * 
	 * @param userId
	 * @return
	 */
	public List<Integer> querySocialAccountCodeByUserId(Integer userId);
	
	/**
	 * 根据平台代号查询其绑定的社交账号
	 * 
	 * @param platformCodes
	 * @param userId
	 * @return
	 */
	public List<UserSocialAccount> querySocialAccountByPlatformCodes(Integer[] platformCodes, Integer userId);
	
	/**
	 * 查询用户id
	 * 
	 * @param platformId
	 * @param platformCode
	 * @return
	 */
	public UserSocialAccount queryUserId(String platformId, Integer platformCode);
	
}
