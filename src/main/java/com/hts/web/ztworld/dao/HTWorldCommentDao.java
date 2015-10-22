package com.hts.web.ztworld.dao;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.hts.web.base.database.RowCallback;
import com.hts.web.base.database.RowSelection;
import com.hts.web.common.dao.BaseDao;
import com.hts.web.common.pojo.HTWorldComment;
import com.hts.web.common.pojo.HTWorldCommentDto;
import com.hts.web.common.pojo.HTWorldCommentReId;
import com.hts.web.common.pojo.HTWorldCommentUser;

/**
 * <p>
 * 织图评论数据访问接口
 * </p>
 * 
 * 创建时间：2013-8-3
 * @author ztj
 *
 */
public interface HTWorldCommentDao extends BaseDao {

	/**
	 * 根据id查询评论信息
	 * 
	 * @param worldId
	 * @return
	 */
	public HTWorldComment queryCommentById(Integer id);
	
	/**
	 * 根据id查询评论数据传输信息（包含用户信息）
	 * 
	 * @param worldId
	 * @return
	 */
	public HTWorldCommentDto queryCommentDtoById(Integer worldId);
	
	/**
	 * 查询评论总数
	 * @param worldId
	 * @return
	 */
	public long queryCommentCount(Integer worldId);
	
	/**
	 * 保存织图世界评论
	 * @param worldComment
	 * @throws SQLException 
	 */
	public void saveWorldComment(HTWorldComment worldComment);
	
	/**
	 * 分页查询织图评论列表
	 * 
	 * @param worldId
	 * @param rowSelection
	 * @return
	 */
	public List<HTWorldCommentDto> queryComment(Integer worldId, RowSelection rowSelection);
	
	/**
	 * 根据最大id分页查询评论列表
	 * 
	 * @param worldId
	 * @param maxId
	 * @param rowSelection
	 * @return
	 */
	public List<HTWorldCommentDto> queryCommentByMaxId(Integer worldId, Integer maxId, RowSelection rowSelection);
	
	/**
	 * 更加最小id分页查询评论列表
	 * 
	 * @param worldId
	 * @param minId
	 * @param rowSelection
	 * @return
	 */
	public List<HTWorldCommentDto> queryCommentByMinId(Integer worldId, Integer minId, RowSelection rowSelection);
	
	/**
	 * 根据最大id查询织图评论总数
	 * 
	 * @param worldId
	 * @param maxId
	 * @return
	 */
	public long queryCommentCountByMaxId(Integer worldId, Integer maxId);
	
	/**
	 * 根据最小id查询织图评论总数
	 * 
	 * @param worldId
	 * @param minId
	 * @return
	 */
	public long queryCommentCountByMinId(Integer worldId, Integer minId);
	
	/**
	 * 条件查询评论总数
	 * 
	 * @param attrMap
	 * @param userAttrMap
	 * @return
	 */
	public long queryCommentCount(Map<String, Object> attrMap, Map<String, Object> userAttrMap);
	
	/**
	 * 根据最大id条件查询评论总数
	 * 
	 * @param attrMap
	 * @param userAttrMap
	 * @return
	 */
	public long queryCommentCountByMaxId(Integer maxId, Map<String, Object> attrMap, Map<String, Object> userAttrMap);
	
	/**
	 * 根据最小id条件查询评论总数
	 * 
	 * @param minId
	 * @param attrMap
	 * @param userAttrMap
	 * @return
	 */
	public long queryCommentCountByMinId(Integer minId, Map<String, Object> attrMap, Map<String, Object> userAttrMap);
	
	
	/**
	 * 更新评论屏蔽标志
	 * 
	 * @param id
	 * @param shield
	 */
	public void updateCommentShield(Integer id, Integer shield);
	
	/**
	 * 更新推送标志
	 * 
	 * @param commentId
	 * @param valid
	 */
	public void updatePushed(Integer commentId, Integer valid);
	
	/**
	 * 查询指定用户的评论消息
	 * 
	 * @param userId
	 * @param rowSelection
	 * @return
	 */
	public List<HTWorldCommentDto> queryUserComment(Integer userId, RowSelection rowSelection);
	
	/**
	 * 根据最大id查询指定用户的评论消息
	 * 
	 * @param userId
	 * @param maxId
	 * @param rowSelection
	 * @return
	 */
	public List<HTWorldCommentDto> queryUserCommentByMaxId(Integer userId, Integer maxId, RowSelection rowSelection);
	
	/**
	 * 根据最小id查询指定用户的评论消息
	 * 
	 * @param userId
	 * @param minId
	 * @param rowSelection
	 * @return
	 */
	public List<HTWorldCommentDto> queryUserCommentByMinId(Integer userId, Integer minId, RowSelection rowSelection);
	
//	/**
//	 * 查询未读用户评论总数
//	 * 
//	 * @param userId
//	 * @return
//	 */
//	public long queryUnCheckUserCommentCount(Integer userId);
	
	/**
	 * 根据最大id查询指定用户的评论消息总数
	 * 
	 * @param userId
	 * @param maxId
	 * @return
	 */
	public long queryUserCommentCountByMaxId(Integer userId, Integer maxId);
	
	/**
	 * 根据最小id查询指定用户的评论消息总数
	 * 
	 * @param userId
	 * @param minId
	 * @return
	 */
	public long queryUserCommentCountByMinId(Integer userId, Integer minId);
	
	/**
	 * 更新未读评论
	 * 
	 * @param userId
	 */
	public void updateUnreadComment(Integer userId);
	
	/**
	 * 查询限定条数的评论用户信息
	 * 
	 * @param worldIds
	 * @param limit
	 * @return
	 */
	public void queryCommentUserByLimit(Integer[] worldIds, Integer limit, RowCallback<HTWorldCommentUser> callback);
	
	/**
	 * 查询限定条数的评论用户信息
	 * 
	 * @param worldId
	 * 
	 * @param limit
	 * @param callback
	 */
	public void queryCommentUserByLimit(Integer worldId, Integer limit, RowCallback<HTWorldCommentUser> callback);
	
	/**
	 * 根据id来更新valid
	 * @param valid
	 * @param id
	 */
	public void updateValidByIds(Integer[] ids);
	
	/**
	 * 根据id来更新评论时间
	 * @param ids
	 * @param commentDate
	 */
	public void updateCommentDateById(Integer id,Date commentDate);
	
	/**
	 * 根据id来更新ck
	 * @param ck
	 * @param id
	 */
	public void updateCkById(Integer ck,Integer id);
	
	
	/**
	 * 更新评论内容
	 * @param id
	 * @param content
	 */
	public void updateContentById(Integer id , String content);
	
	/**
	 * 查询ReId列表
	 * 
	 * @param ids
	 * @return
	 */
	public List<HTWorldCommentReId> queryReId(Integer[] ids);
	
	
	/**
	 * 查询评论有效性
	 * 
	 * @param id
	 * @return
	 */
	public Integer queryValid(Integer id);
	
	/**
	 * 查询作者id
	 * 
	 * @param id
	 * @return
	 */
	public Integer queryAuthorId(Integer id);
	
	/**
	 * 查询回复作者id
	 * 
	 * @param id
	 * @return
	 */
	public Integer queryReAuthorId(Integer id);
}
