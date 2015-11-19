package com.hts.web.ztworld.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.hts.web.base.database.RowCallback;
import com.hts.web.base.database.RowSelection;
import com.hts.web.common.dao.BaseDao;
import com.hts.web.common.pojo.HTWorld;
import com.hts.web.common.pojo.HTWorldBase;
import com.hts.web.common.pojo.HTWorldChannelName;
import com.hts.web.common.pojo.HTWorldCount;
import com.hts.web.common.pojo.HTWorldDto;
import com.hts.web.common.pojo.HTWorldGeo;
import com.hts.web.common.pojo.HTWorldInteractDto;
import com.hts.web.common.pojo.HTWorldLatest;
import com.hts.web.common.pojo.HTWorldLatestId;
import com.hts.web.common.pojo.HTWorldLatestIndex;
import com.hts.web.common.pojo.HTWorldThumbDto;
import com.hts.web.common.pojo.HTWorldThumbUser;
import com.hts.web.common.pojo.HTWorldThumbnail;
import com.hts.web.common.pojo.UserInfo;

/**
 * <p>
 * 汇图世界数据访问接口
 * </p>
 * 
 * 创建时间：2013-8-3
 * @author ztj
 * 
 */
public interface HTWorldDao extends BaseDao {

	
	/**
	 * 保存世界
	 * 
	 * @param htworld
	 */
	public void saveWorld(HTWorld htworld);
	
	/**
	 * 添加播放次数
	 * @param worldId
	 * @param count
	 */
	public int addClickCount(Integer worldId, Integer count);
	
	/**
	 * 更新评论次数
	 * 
	 * @param worldId
	 * @param count
	 * @return
	 */
	public int updateCommentCount(Integer worldId, Integer count);

	/**
	 * 查询喜欢次数
	 * 
	 * @param worldId
	 * @param count
	 * @return
	 */
	public int queryLikeCount(Integer worldId);
	
	/**
	 * 更新喜欢次数
	 * 
	 * @param worldId
	 * @param count
	 * @return
	 */
	public int updateLikeCount(Integer worldId, Integer count);
	
	/**
	 * 更新收藏次数
	 * 
	 * @param worldId
	 * @param count
	 * @return
	 */
	public int updateKeepCount(Integer worldId, Integer count);
	
	
	/**
	 * 根据世界id查询作者信息
	 * 
	 * @param conn
	 * @param worldId
	 * @return
	 * @throws SQLException 
	 */
	public UserInfo queryUserInfoByWorldId(Integer worldId);
	
	/**
	 * 根据id查询世界
	 * @param conn
	 * @param id
	 * @return
	 * @throws SQLException 
	 */
	public HTWorld queryWorldById(Integer id);
	
	/**
	 * 查询织图和用户互动信息
	 * 
	 * @param worldId
	 * @return
	 */
	public HTWorldInteractDto queryHTWorldInteract(Integer worldId);

	/**
	 * 查询指定用户及其关注好友的织图
	 * 
	 * @param userId
	 * @return
	 */
	public List<HTWorldInteractDto> queryConcernWorld(Integer userId, RowSelection rowSelection);
	
	/**
	 * 根据最大id查询指定用户及其关注好友的织图织图
	 * 
	 * @param userId
	 * @param maxId
	 * @param rowSelection
	 * @return
	 */
	public List<HTWorldInteractDto> queryConcernWorld(Integer userId, Integer maxId, RowSelection rowSelection);
	
	/**
	 * 根据最小世界id查询指定用户及其关注好友的织图总数
	 * 
	 * @param conn
	 * @param userId
	 * @return
	 * @throws SQLException
	 */
	public Long queryConcernWorldCountByMaxId(Integer userId, Integer maxId);
	
	/**
	 * 查询指定用户收藏的织图
	 * 
	 * @param userId
	 * @param rowSelection
	 * @return
	 */
	public List<HTWorldInteractDto> queryKeepWorld(Integer userId, RowSelection rowSelection);
	
	/**
	 * 根据查询指定用户收藏的织图
	 * 
	 * @param userId
	 * @param maxId
	 * @param rowSelection
	 * @return
	 */
	public List<HTWorldInteractDto> queryKeepWorld(Integer userId, Integer maxId, RowSelection rowSelection);

	
	/**
	 * 根据最小世界id查询指定用户收藏的织图总数
	 * @param userId
	 * @return
	 */
	public Long queryKeepWorldCountByMaxId(Integer userId, Integer maxId);
	
	/**
	 * 查询指定用户喜欢的织图
	 * 
	 * @param userId
	 * @param rowSelection
	 * @return
	 */
	public List<HTWorldInteractDto> queryLikedWorld(Integer userId, RowSelection rowSelection);
	
	/**
	 * 根据最大id查询指定用户喜欢的织图
	 * 
	 * @param userId
	 * @param rowSelection
	 * @return
	 */
	public List<HTWorldInteractDto> queryLikedWorld(Integer userId, Integer maxId, RowSelection rowSelection);

	/**
	 * 根据最大id查询指定用户喜欢的织图总数
	 * @param userId
	 * @return
	 */
	public Long queryLikedWorldCountByMaxId(Integer userId, Integer maxId);
	
	/**
	 * 查询指定用户的织图
	 * 
	 * @param userId
	 * @param rowSelection
	 * @return
	 */
	public List<HTWorldInteractDto> queryUserWorld(Integer userId, Integer joinId, RowSelection rowSelection);
	
	/**
	 * 根据查询指定用户的织图
	 * 
	 * @param userId
	 * @param joinId
	 * @param maxId
	 * @param rowSelection
	 * @return
	 */
	public List<HTWorldInteractDto> queryUserWorld(Integer userId, Integer joinId,Integer maxId, RowSelection rowSelection);

	/**
	 * 查询指定用户的织图总数
	 * 
	 * @param userId
	 * @return
	 */
	public Long queryUserWorldCount(Integer userId);
	
	/**
	 * 根据最小世界id查询指定用户的织图总数
	 * 
	 * @param userId
	 * @return
	 */
	public Long queryUserWorldCountByMaxId(Integer userId, Integer maxId);
	
	
	/**
	 * 查询最新织图
	 * 
	 * @param joinId
	 * @param rowSelection
	 * @return
	 */
	public List<HTWorldInteractDto> queryLatestWorld(Integer joinId, RowSelection rowSelection);
	
	/**
	 * 查询最新织图
	 * 
	 * @param joinId
	 * @param maxId
	 * @param rowSelection
	 * @return
	 */
	public List<HTWorldInteractDto> queryLatestWorld(Integer joinId, Integer maxId, RowSelection rowSelection);

	
	/**
	 * 查询最新织图
	 * 
	 * @param joinId
	 * @param ids
	 * @param callback
	 * @return
	 */
	public void queryLatestWorld(Integer joinId, Integer[] ids, 
			RowCallback<HTWorldInteractDto> callback);

	/**
	 * 查询最新织图总数
	 * 
	 * @param maxId
	 * @return
	 */
	public Long queryLatestWorldCount(Integer maxId);
	
	/**
	 * 查询最新和用户织图
	 * 
	 * @param joinId
	 * @param rowSelection
	 * @return
	 */
	public List<HTWorldInteractDto> queryLatestAndUserWorld(Integer joinId, 
			Date beginDate, Date endDate, RowSelection rowSelection);
	
	/**
	 * 查询最新和用户织图
	 * 
	 * @param maxId
	 * @param joinId
	 * @param rowSelection
	 * @return
	 */
	public List<HTWorldInteractDto> queryLatestAndUserWorld(Integer maxId, Integer joinId, 
			Date beginDate, Date endDate, RowSelection rowSelection);
	
	/**
	 * 根据作者id查询世界数据传输对象列表
	 * @param userId
	 * @param rowSelection
	 * @return
	 */
	public List<HTWorldDto> queryHTWorldDtoByUserId(Integer userId, RowSelection rowSelection);
	
	/**
	 * 根据id查询世界数据传输对象
	 * @param worldId
	 * @return
	 */
	public HTWorldDto queryHTWorldDtoById(Integer worldId);
	
	/**
	 * 根据短链查询世界数据传输对象
	 * 
	 * @param shortLink
	 * @return
	 */
	public HTWorldDto queryHTWorldDtoByShortLink(String shortLink);
	
	/**
	 * 根据id查询世界数据传输对象，并且不检查有效性
	 * 
	 * @param worldId
	 * @return
	 */
	public HTWorldDto queryHTWorldDtoByIdNoValidCheck(Integer worldId);
	
	/**
	 * 根据短链查询世界数据传输对象，并且不检查有效性
	 * 
	 * @param shortLink
	 * @return
	 */
	public HTWorldDto queryHTWorldDtoByShortLinkNoValidCheck(String shortLink);
	
	/**
	 * 更新织图屏蔽标志
	 * 
	 * @param worldId
	 * @param shield
	 */
	public void updateWorldShield(Integer worldId, Integer shield);

	/**
	 * 更新织图
	 * 
	 * @param worldId
	 * @param attrMap
	 */
	public void updateWorld(Integer worldId, Map<String, Object> attrMap);
	
	/**
	 * 查询指定用户的织图位置信息
	 * 
	 * @param userId
	 */
	public List<HTWorldGeo> queryWorldGeo(Integer userId);
	
	
	/**
	 * 从元数据构建HTWorld POJO对象
	 * @param meta
	 * @return
	 * @throws SQLException 
	 */
	public HTWorld buildHTWorldByResultSet(ResultSet rs) throws SQLException;
	
	/**
	 * 根据结果集构建关注的织图数据传输对象
	 * 
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public HTWorldInteractDto buildHTWorldInteractDtoByResultSet(ResultSet rs) throws SQLException;
	
	/**
	 * 根据结果集构建关注的织图数据传输对象, without liked and keep
	 * 
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public HTWorldInteractDto buildHTWorldInteractDto(ResultSet rs) throws SQLException;
	
	/**
	 * 根据结果集构建HTWorldDto
	 * 
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public HTWorldDto buildHTWorldDtoByResulSet(ResultSet rs) throws SQLException;
	
	/**
	 * 从结果集构建织图缩略图信息
	 * 
	 * @param worldId
	 * @param rs
	 * @return
	 * @throws SQLException 
	 */
	public HTWorldThumbDto buildHTWorldThumbDtoByResultSet(Integer worldId, ResultSet rs) throws SQLException;
	
	/**
	 * 从结果集构建HTWorldGeo
	 * 
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public HTWorldGeo buildHTWorldGeoByResultSet(ResultSet rs) throws SQLException;
	
	/**
	 * 根据id批量删除织图
	 * 
	 * @param ids
	 */
	public void deleteByIds(Integer[] ids);
	
	/**
	 * 查询限定条数织图缩略图
	 * 
	 * @param ids
	 * @param limit
	 * @param callback
	 * @throws SQLException
	 */
	public void queryHTWorldThumbUserByLimit(Integer[] ids, Integer limit, 
			RowCallback<HTWorldThumbUser> callback);
	
	/**
	 * 查询最新织图
	 * 
	 * @param rowSelection
	 * @return
	 */
	public List<HTWorld> queryHTWorld(RowSelection rowSelection);
	
	/**
	 * 查询最新织图
	 * 
	 * @param maxId
	 * @param rowSelection
	 * @return
	 */
	public List<HTWorld> queryHTWorld(Integer maxId, RowSelection rowSelection);

	/**
	 * 根据描述查询织图
	 * 
	 * @param userId
	 * @param rowSelection
	 * @return
	 */
	public List<HTWorldInteractDto> queryWorldInteractByDesc(Integer userId, 
			String desc, RowSelection rowSelection);
	
	/**
	 * 根据描述查询织图
	 * 
	 * @param maxId
	 * @param userId
	 * @param rowSelection
	 * @return
	 */
	public List<HTWorldInteractDto> queryWorldInteractByDesc(Integer maxId, Integer userId, String desc,
			RowSelection rowSelection);
	
	/**
	 * 根据描述查询织图总数
	 * @param maxId
	 * @param desc
	 * @return
	 */
	public long queryWorldInteractCountByDesc(Integer maxId, String desc);
	
	/**
	 * 根据用户id查询织图总数
	 * 
	 * @param authorId
	 * @return
	 */
	public long queryWorldCountByAuthorId(Integer authorId);
	
	/**
	 * 查询织图缩略图
	 * 
	 * @param wids
	 * @return
	 */
	public List<HTWorldThumbnail> queryWorldThumbnail(Integer[] wids);

	/**
	 * 查询最新列表
	 * 
	 * @param intervals
	 * @return
	 */
	public List<HTWorldLatest> queryLatest(Integer userId, long initTime, int preInterval, int[] intervals, int[] limits);
	
	/**
	 * 查询最新总数列表
	 * 
	 * @param startTime
	 * @param intervals
	 * @return
	 */
	public List<HTWorldLatestIndex> queryLatestIndex(Integer userId, long initTime, int preInterval, int[] intervals);
	
	/**
	 * 查询最新列表
	 * 
	 * @param maxId
	 * @param userId
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<HTWorldLatest> queryLatest(Integer maxId, Integer userId, long startTime, long endTime, Integer limit);
	
	/**
	 * 查询最新列表
	 * 
	 * @param maxId
	 * @param userId
	 * @param startTime
	 * @param limit
	 * @return
	 */
	public List<HTWorldLatest> queryLatest(Integer maxId, Integer userId, long startTime, Integer limit);
	
	/**
	 * 查询最新列表总数
	 * 
	 * @param maxId
	 * @param userId
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public long queryLatestCount(Integer maxId, Integer userId,
			long startTime, long endTime);
	
	/**
	 * 查询最新列表总数
	 * 
	 * @param maxId
	 * @param userId
	 * @param startTime
	 * @return
	 */
	public long queryLatestCount(Integer maxId, Integer userId,
			long startTime);
	
	
	public List<HTWorldLatest> queryLatest2(Integer userId, long initTime,
			int preInterval, int[] intervals, int[] limits);

	public List<HTWorldLatestIndex> queryLatestIndex2(Integer userId, long initTime, 
			int preInterval, int[] intervals);

	public List<HTWorldLatest> queryLatest2(Integer maxId, Integer userId,
			long startTime, final long endTime, Integer limit);
	
	public List<HTWorldLatest> queryLatest2(Integer maxId, Integer userId,
			long startTime, Integer limit);
	
	public long queryLatestCount2(Integer maxId, Integer userId, long startTime, long endTime);
	
	public long queryLatestCount2(Integer maxId, Integer userId,
			long startTime);

	/**
	 * 设置织图信息
	 * 
	 * @param rs
	 * @param world
	 * @throws Exception
	 */
	public void setWorldInfo(ResultSet rs, HTWorldBase world) throws SQLException;	
	
	/**
	 * 查询作者id
	 * 
	 * @param worldId
	 * @return
	 * @throws Exception
	 */
	public Integer queryAuthorId(Integer worldId);

	/**
	 * 根据最大id查询最新id列表
	 * 
	 * @param maxId
	 * @param limit
	 * @return
	 */
	public List<HTWorldLatestId> queryLatestIdByMaxId(Integer maxId, Integer limit);
	
	/**
	 * 根据最小id查询最新id列表
	 * @param minId
	 * @param limit
	 * @return
	 */
	public List<HTWorldLatestId> queryLatestIdByMinId(Integer minId, Integer limit);
	
	/**
	 * 查询计数
	 * 
	 * @param ids
	 * @param callback
	 */
	public void queryCount(Integer[] ids, RowCallback<HTWorldCount> callback);
	
	/**
	 * 查询图片总数
	 * 
	 * @param authorId
	 * @return
	 */
	public Integer queryChildCount(Integer authorId);
	
	/**
	 * 根据id查询子世界总数
	 * 
	 * @param id
	 * @return
	 */
	public Integer queryChildCountById(Integer id);
	
	/**
	 * 解析频道名称为String[]
	 * 
	 * @param names
	 * @return
	 */
	public String[] parseChannelNames(List<HTWorldChannelName> names);
	
	/**
	 * 格式化频道名称为List
	 * 
	 * @param cnamesStr
	 * @param cidsStr
	 * @return
	 */
	public List<HTWorldChannelName> formatChannelNames(String cnamesStr, String cidsStr);
	
	/**
	 * 查询有效性
	 * 
	 * @param id
	 * @return
	 */
	public Integer queryValid(Integer id);
	
	/**
	 * 查询用户的最新n个织图的title_path
	 * @param userId
	 * @return
	 */
	public List<HTWorldInteractDto> queryLastNHtworldInfoByUserId(Integer userId,Integer n);
	
	/**
	 * 根据短链查询织图和作者信息
	 * 
	 * @param shortLink
	 * @return
	 */
	public HTWorldInteractDto queryHTWorldInteractByLink(String shortLink);
}


