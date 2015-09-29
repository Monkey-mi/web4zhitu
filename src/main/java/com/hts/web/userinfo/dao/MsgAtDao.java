package com.hts.web.userinfo.dao;

import java.util.List;

import com.hts.web.base.database.RowSelection;
import com.hts.web.common.dao.BaseDao;
import com.hts.web.common.pojo.MsgAt;
import com.hts.web.common.pojo.MsgAtDto;

/**
 * <p>
 * AT消息数据访问接口
 * </p>
 * 
 * @author lynch 2015-09-22
 * @version 3.0.5
 *
 */
public interface MsgAtDao extends BaseDao {

	/**
	 * 批量保存消息
	 * 
	 * @param msgs
	 * @version 3.0.5
	 * @author lynch 2015-09-22
	 */
	public void saveAtMsgs(MsgAt[] msgs);
	
	/**
	 * 查询未读数量
	 * 
	 * @param atId 被at用户id
	 * @return 未读数量
	 * 
	 * @version 3.0.5
	 * @author lynch 2015-09-22
	 */
	public Long queryUnCheckCount(Integer atId);
	
	
	/**
	 * 更新未读标记
	 * 
	 * @param atId 被at用户id
	 * 
	 * @version 3.0.5
	 * @author lynch 2015-09-22
	 * @return
	 */
	public void updateCK(Integer atId);
	
	/**
	 * 查询消息列表
	 * 
	 * @param atId
	 * @return
	 */
	public List<MsgAtDto> queryMsg(Integer atId, RowSelection rowSelection);
	
	/**
	 * 根据最大id查询消息列表
	 * 
	 * @param maxId
	 * @param atId
	 * @return
	 */
	public List<MsgAtDto> queryMsg(Integer maxId, Integer atId, RowSelection rowSelection);
	
}
