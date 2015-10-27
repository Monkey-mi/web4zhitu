package com.hts.web.userinfo.dao;

import com.hts.web.common.dao.BaseDao;
import com.hts.web.common.pojo.UserMsgUnreadCount;

/**
 * <p>
 * 未读消息数据访问接口
 * </p>
 * 
 * @author lynch 2015-10-25
 *
 */
public interface MsgUnreadDao extends BaseDao {

	/**
	 * 未读消息类型
	 * 
	 * @author lynch 2015-10-25
	 *
	 */
	public enum UnreadType {
		
		CONCERN("concern"),LIKE("like"),COMMENT("comment"),
		AT("at"),SYSMSG("sysmsg"),UMSG("umsg");
		
		private String type;
		
		private UnreadType(String type) {
			this.type = type;
		}
		
		@Override
		public String toString() {
			return this.type;
		}
		
	}
	
	/**
	 * 保存未读消息记录
	 * 
	 * @param userId
	 */
	public void saveUnRead(Integer userId);

	/**
	 * 查询未读消息数
	 * 
	 * @param userId
	 * @param unreadType
	 * @return
	 */
	public Integer queryCount(Integer userId, UnreadType unreadType);

	/**
	 * 清空未读消息数量
	 * 
	 * @param userId
	 * @param unreadType
	 */
	public void clearCount(Integer userId, UnreadType unreadType);
	
	/**
	 * 添加未读消息数量
	 * 
	 * @param userId
	 * @param unreadType
	 */
	public void addCount(Integer userId, UnreadType unreadType);
	
	/**
	 * 查询未读消息记录
	 * 
	 * @param userId
	 * @return
	 */
	public UserMsgUnreadCount queryCount(Integer userId);
	
	/**
	 * 更新最大已读id
	 * 
	 * @param userId
	 * @param id
	 * @param unreadType
	 */
	public void updateReadId(Integer userId, Integer id, UnreadType unreadType);
	
	/**
	 * 查询最大已读id
	 * 
	 * @param userId
	 * @param unreadType
	 * @return
	 */
	public Integer queryReadId(Integer userId, UnreadType unreadType);

}
