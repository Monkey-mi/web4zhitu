package com.hts.web.userinfo.dao;

import com.hts.web.common.dao.BaseDao;
import com.hts.web.common.pojo.MsgAt;
import com.hts.web.common.pojo.MsgAtId;

/**
 * 评论AT消息数据访问接口
 * 
 * @version 3.0.5
 * @author lynch 2015-09-22
 *
 */
public interface MsgAtCommentDao extends BaseDao {

	/**
	 * 保存AT消息
	 * @param msgs
	 * @version 3.0.5
	 * @author lynch 2015-09-22
	 * 
	 */
	public void saveAtMsgs(MsgAt[] msgs);
	
	/**
	 * 查询评论AT消息
	 * 
	 * @param worldId
	 * @param index
	 * @return null表示无记录或查询出错
	 * 
	 * @version 3.0.5
	 * @author lynch 2015-09-22
	 */
	public MsgAtId queryAtId(Integer worldId, Integer index);
	
}
