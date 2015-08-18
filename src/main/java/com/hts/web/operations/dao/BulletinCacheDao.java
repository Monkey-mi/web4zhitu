package com.hts.web.operations.dao;

import java.util.List;

import com.hts.web.base.database.RowSelection;
import com.hts.web.common.dao.BaseCacheDao;
import com.hts.web.common.pojo.OpMsgBulletin;

/**
 * <p>
 * 系统公告缓存数据访问接口
 * </p>
 * 
 * 创建时间: 2015-06-11
 * @author lynch
 *
 */
public interface BulletinCacheDao extends BaseCacheDao {

	/**
	 * 查询公告缓存
	 * 
	 * @return
	 */
	public List<OpMsgBulletin> queryBulletin();
	
	/**
	 * 更新公告缓存
	 * 
	 * @param list
	 */
	public void updateBulletin(List<OpMsgBulletin> list);
	
	/**
<<<<<<< HEAD
	 * 查询专题列表
	 * 
	 * @return
	 */
	public List<OpMsgBulletin> queryTheme(RowSelection rowSelection);
	
	/**
	 * 查询用户专题列表
	 * 
	 * @return
	 */
	public List<OpMsgBulletin> queryUserTheme(RowSelection rowSelection);
	
	/**
=======
>>>>>>> branch 'dev' of https://github.com/imzhitu/web4zhitu.git
	 * 更新用户主题公告
	 * @param cacheList
	 */
	public void updateUserThemeBulletin(List<OpMsgBulletin> cacheList);
	
	/**
	 * 更新专题公告
	 * @param cacheList
	 */
	public void updateThemeBulletin(List<OpMsgBulletin> cacheList);
	
	
}
