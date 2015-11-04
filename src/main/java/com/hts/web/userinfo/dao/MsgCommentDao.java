package com.hts.web.userinfo.dao;

import java.util.List;

import com.hts.web.base.database.RowSelection;
import com.hts.web.common.pojo.MsgComment;
import com.hts.web.common.pojo.MsgCommentDto;

/**
 * <p>
 * 评论消息数据访问接口
 * </p>
 * 
 * @author lynch　2015-10-16
 *
 */
public interface MsgCommentDao {

	/**
	 * 查询消息列表
	 * 
	 * @param worldAuthorId
	 * @param rowSelection
	 * @return
	 */
	public List<MsgCommentDto> queryMsg(Integer worldAuthorId,
			RowSelection rowSelection);
	
	/**
	 * 查询消息列表
	 * 
	 * @param maxId
	 * @param worldAuthorId
	 * @param rowSelection
	 * @return
	 */
	public List<MsgCommentDto> queryMsg(Integer maxId, Integer worldAuthorId,
			RowSelection rowSelection);
	
	/**
	 * 保存消息
	 * 
	 * @param msg
	 */
	public void saveMsgComment(MsgComment msg);

	/**
	 * 删除消息
	 * 
	 * @param commentId
	 */
	public void deleteByCommentId(Integer commentId, Integer receiveId);

//	/**
//	 * 更新已读标记
//	 * 
//	 * @param worldAuthorId
//	 */
//	public void updateCK(Integer worldAuthorId);
//
//	/**
//	 * 查询未读总数
//	 * 
//	 * @param worldAuthorId
//	 */
//	public long queryUnCkCount(Integer worldAuthorId);

}
