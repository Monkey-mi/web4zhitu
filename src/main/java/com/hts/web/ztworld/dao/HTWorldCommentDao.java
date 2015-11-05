package com.hts.web.ztworld.dao;

import java.util.List;

import com.hts.web.base.database.RowCallback;
import com.hts.web.base.database.RowSelection;
import com.hts.web.common.dao.BaseDao;
import com.hts.web.common.pojo.HTWorldComment;
import com.hts.web.common.pojo.HTWorldCommentDto;
import com.hts.web.common.pojo.HTWorldCommentInline;

/**
 * <p>
 * 织图评论数据访问接口
 * </p>
 * 
 * @author ztj 2013-8-3 2015-11-04
 *
 */
public interface HTWorldCommentDao extends BaseDao {

	/**
	 * 根据id查询评论信息
	 * 
	 * @param id
	 * @param worldId
	 * @return
	 * 
	 * @author lynch 2015-11-04
	 */
	public HTWorldComment queryCommentById(Integer id, Integer worldId);
	
	/**
	 * 根据id查询评论数据传输信息（包含用户信息）
	 * 
	 * @param id
	 * @param worldId
	 * @return
	 * 
	 * @author lynch 2015-11-04
	 */
	public HTWorldCommentDto queryCommentDtoById(Integer id, Integer worldId);
	
	/**
	 * 查询评论总数
	 * 
	 * @param worldId
	 * @return
	 * @author lynch 2015-11-04
	 */
	public long queryCommentCount(Integer worldId);
	
	/**
	 * 保存织图世界评论
	 * 
	 * @param comment
	 * 
	 * @author lynch 2015-11-04
	 */
	public void saveWorldComment(HTWorldComment comment);
	
	/**
	 * 删除评论
	 * 
	 * @param id
	 * @param worldId
	 */
	public void delComment(Integer id, Integer worldId);
	
	/**
	 * 分页查询织图评论列表
	 * 
	 * @param worldId
	 * @param rowSelection
	 * @return
	 * @author lynch 2015-11-04
	 */
	public List<HTWorldCommentDto> queryComment(Integer worldId, 
			RowSelection rowSelection);
	
	/**
	 * 根据最大id分页查询评论列表
	 * 
	 * @param worldId
	 * @param maxId
	 * @param rowSelection
	 * @return
	 * @author lynch 2015-11-04
	 */
	public List<HTWorldCommentDto> queryCommentByMaxId(Integer worldId, 
			Integer maxId, RowSelection rowSelection);
	
	
	/**
	 * 查询瀑布流内嵌的评论
	 * 
	 * @param worldIds
	 * @param limit
	 * @param callback
	 * 
	 * @author lynch 2015-11-04
	 */
	public void queryInlineComment(Integer[] worldIds, Integer limit,
			RowCallback<HTWorldCommentInline> callback);
	
	
	/**
	 * 查询作者id
	 * 
	 * @param id
	 * @return 找不到返回0
	 */
	public Integer queryAuthorId(Integer id, Integer worldId);
	
	/**
	 * 检测评论是否存在
	 * 
	 * @param id
	 * @param worldId
	 * @return
	 * @author lynch 2015-11-03 2015-11-04
	 */
	public boolean isCommentExist(Integer id, Integer worldId);
	
	/**
	 * 查询所有评论作者id
	 * 
	 * @param worldId
	 * @return
	 * @author lynch 2015-11-03
	 */
	public List<Integer> queryAllAuthorId(Integer worldId, Integer limit);
}
