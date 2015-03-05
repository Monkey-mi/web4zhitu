package com.hts.web.userinfo.dao;

import com.hts.web.base.database.RowCallback;
import com.hts.web.base.database.RowSelection;
import com.hts.web.common.dao.BaseDao;
import com.hts.web.common.pojo.UserMsgLiked;

public interface UserMsgInteractDao extends BaseDao {

	public void queryLikedMsg(Integer userId, Integer authorId,
			RowSelection rowSelection, RowCallback<UserMsgLiked> callback);

	public void queryLikedMsg(Integer maxId, Integer userId, Integer authorId,
			RowSelection rowSelection, RowCallback<UserMsgLiked> callback);
}
