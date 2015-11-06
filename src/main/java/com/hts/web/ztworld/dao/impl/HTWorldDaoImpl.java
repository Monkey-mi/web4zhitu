package com.hts.web.ztworld.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.hts.web.base.constant.Tag;
import com.hts.web.base.database.HTS;
import com.hts.web.base.database.RowCallback;
import com.hts.web.base.database.RowSelection;
import com.hts.web.base.database.SQLUtil;
import com.hts.web.common.dao.impl.BaseDaoImpl;
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
import com.hts.web.common.pojo.HTWorldTextStyle;
import com.hts.web.common.pojo.HTWorldThumbDto;
import com.hts.web.common.pojo.HTWorldThumbUser;
import com.hts.web.common.pojo.HTWorldThumbnail;
import com.hts.web.common.pojo.UserInfo;
import com.hts.web.common.pojo.UserInfoDto;
import com.hts.web.common.util.CollectionUtil;
import com.hts.web.common.util.JSONUtil;
import com.hts.web.common.util.StringUtil;
import com.hts.web.userinfo.dao.impl.UserInfoDaoImpl;
import com.hts.web.ztworld.dao.HTWorldDao;

/**
 * <p>
 * 汇图世界数据访问对象
 * </p>
 * 
 * 创建时间：2012-11-01
 * 
 * @author ztj
 * 
 */
@Repository("HTSHTWorldDao")
public class HTWorldDaoImpl extends BaseDaoImpl implements HTWorldDao{
	
	private static String table = HTS.HTWORLD_HTWORLD;
	
	private static final String THUMB_USER = "id,author_id,cover_path,title_path,bg_path,title_thumb_path ";
	
	private static final String GEO_INFO = "id,title_path,title_thumb_path,longitude,latitude,location_desc,location_addr";
	
	/**
	 * 保存世界
	 */
	private static final String SAVE_WORLD = "insert into " + table 
			+ " (id, short_link, world_name, world_desc, world_label, world_type, type_id, date_added,"
			+ " date_modified,author_id, cover_path, title_path, bg_path, title_thumb_path,channel_name,channel_id," 
			+ "longitude,latitude,location_desc,location_addr, phone_code, province," 
			+ "city, size, child_count,ver,tp, valid, latest_valid, shield, text_style)"
			+ " values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	/**
	 * 查询织图世界
	 */
	private static final String QUERY_HTWORLD_BY_ID = "SELECT * FROM " + table + " where id=?";
	
	/**
	 * 查询织图世界数据传输世界头部
	 */
	private static final String QUERY_HTWORLD_DTO_HEADER = "SELECT h.*," + U0_INFO + " FROM " + table + " as h," 
			+ HTS.USER_INFO + " as u0 where h.author_id=u0.id";
	
	/**
	 * 根据id查询织图世界数据传输对象
	 */
	private static final String QUERY_HTWORLD_DTO_BY_ID =  QUERY_HTWORLD_DTO_HEADER + " and h.valid=? and h.shield=? and h.id=?";
	
	/**
	 * 根据作者id查询织图世界数据传输对象
	 */
	private static final String QUERY_HTWORLD_DTO_BY_AUTHOR_ID = QUERY_HTWORLD_DTO_HEADER + " and h.valid=? and h.shield=? and h.author_id=?";
	
	/**
	 * 根据短链查询织图世界数据传输对象
	 */
	private static final String QUERY_HTWORLD_DTO_BY_SHORT_LINK = QUERY_HTWORLD_DTO_HEADER + " and h.valid=? and h.shield=? and h.short_link=?";
	
	/**
	 * 根据id查询织图世界数据传输对象并且不检查有效性
	 */
	private static final String QUERY_HTWORLD_DTO_BY_ID_NO_VALID_CHECK = QUERY_HTWORLD_DTO_HEADER + " and h.id=?";
	
	/**
	 * 根据短链查询织图世界数据传输对象并且不检查有效性
	 */
	private static final String QUERY_HTWORLD_DTO_BY_SHORT_LINK_NO_VALID_CHECK = QUERY_HTWORLD_DTO_HEADER + " and h.short_link=?";

	/** 
	 * 添加播放次数
	 */
	private static final String ADD_CLICK_COUNT = "update " + table + " set click_count=click_count+? where id=? and valid=?";
	
	/** 
	 * 更新评论次数 
	 */
	private static final String UPDATE_COMMENT_COUNT = "update " + table 
			+ " set comment_count=? where id=?";
	
	/**
	 * 查询喜欢次数
	 */
	private static final String QUERY_LIKE_COUNT = "select like_count from " 
			+ table + " where id=?";
	
	/**
	 * 更新喜欢次数
	 */
	private static final String UPDATE_LIKE_COUNT = "update " + table 
			+ " set like_count=? where id=?";
	
	
	/** 
	 * 更新收藏次数 
	 */
	private static final String UPDATE_KEEP_COUNT = "update " + table 
			+ " set keep_count=? where id=?";


	/**
	 * 根据织图id查询作者id
	 */
	private static final String QUERY_USER_INFO_BY_WORLD_ID = "SELECT u.* FROM "
			+ table + " as h, " + HTS.USER_INFO
			+ " as u where h.author_id=u.id and h.id=?";
	
	/**
	 * 查询指定用户及其关注好友的织图
	 */
	private static final String QUERY_CONCERN_WORLD = "select h3.*," + U0_INFO + " from ("
			+ " select h1.id from ("
			+ " (SELECT id FROM " + table + " where author_id=? and valid=1 and shield=0 and tp=0 order by id desc limit ?)"
			+ "	UNION ALL "
			+ "	(select h0.id from " + table + " as h0, " + HTS.USER_CONCERN + " as uc0 "
			+ "	where h0.author_id=uc0.concern_id and h0.valid=1 and h0.shield=0 and h0.tp=0 and uc0.user_id=?"
			+ " and uc0.valid=1 order by h0.id desc limit ?)"
			+ "	) as h1 order by h1.id desc limit ?) as h2, " + table + " as h3, " 
			+ HTS.USER_INFO + " as u0 where h2.id = h3.id and h3.author_id=u0.id";
	
	/**
	 * 根据最大id查询指定用户及其关注好友的织图
	 */
	private static final String QUERY_CONCERN_WORLD_BY_MAX_ID = "select h3.*," + U0_INFO + " from ("
			+ " select h1.id from ("
			+ " (SELECT id FROM " + table + " where author_id=? and valid=1 and shield=0 and tp=0 and id<=? order by id desc limit ?)"
			+ "	UNION ALL "
			+ "	(select h0.id from " + table + " as h0, " + HTS.USER_CONCERN + " as uc0 "
			+ "	where h0.author_id=uc0.concern_id and h0.valid=1 and h0.shield=0 and h0.tp=0 and uc0.user_id=?"
			+ " and uc0.valid=1 and h0.id<=? order by h0.id desc limit ?)"
			+ "	) as h1 order by h1.id desc limit ?) as h2, " + table + " as h3, " 
			+ HTS.USER_INFO + " as u0 where h2.id = h3.id and h3.author_id=u0.id";
	
	
	/**
	 * 根据最大世界id查询指定用户及其关注好友的织图总数
	 */
	private static final String QUERY_CONCERN_WORLD_COUNT_BY_MAX_ID = "select count(*) from (" 
			+ " select h0.id from " + table + " h0, " + HTS.USER_CONCERN + " uc0 where h0.author_id=uc0.concern_id and uc0.user_id=? and uc0.valid=1 and h0.valid=1 and h0.shield=0 and h0.id<=?"
			+ " UNION ALL"
			+ " select h1.id from " + table + " as h1 where h1.author_id=? and h1.valid=1 and h1.id<=?) as h3";
	
	/**
	 * 查询指定用户收藏的织图
	 */
	private static final String QUERY_KEEP_WORLD = "select h.hkid,h.*,1-ISNULL(hl.user_id) as liked,1 as keep"
			+ " from (select h0.*," + U0_INFO + ",hk0.id as hkid"
			+ " from " + table + " as h0, " + HTS.HTWORLD_KEEP + " as hk0, " + HTS.USER_INFO + " as u0"
			+ " where h0.id=hk0.world_id and h0.author_id=u0.id and hk0.user_id=? and h0.valid=1 and h0.shield=0 and hk0.valid=1 order by hkid desc limit ?,?) h"
			+ " left join (select * from " + HTS.HTWORLD_LIKED + " hl0 where hl0.valid=1 and hl0.user_id=?) as hl"
			+ " on h.id = hl.world_id";
	
	/**
	 * 根据最大收藏id查询指定用户收藏的织图
	 */
	private static final String QUERY_KEEP_WORLD_BY_MAX_ID = "select h.hkid,h.*,1-ISNULL(hl.user_id) as liked,1 as keep"
			+ " from (select h0.*," + U0_INFO + ",hk0.id as hkid"
			+ " from " + table + " as h0, " + HTS.HTWORLD_KEEP + " as hk0, " + HTS.USER_INFO + " as u0" 
			+ " where h0.id=hk0.world_id and h0.author_id=u0.id and hk0.user_id=? and h0.valid=1 and h0.shield=0 and hk0.valid=1 and hk0.id<=? order by hkid desc limit ?,?) h"
			+ " left join (select * from " + HTS.HTWORLD_LIKED + " hl0 where hl0.valid=1 and hl0.user_id=?) as hl" 
			+ " on h.id = hl.world_id";
	
	/**
	/**
	 * 根据最大id查询指定用户收藏的织图总数
	 */
	private static final String QUERY_KEEP_WORLD_COUNT_BY_MAX_ID = "select count(*) from " 
			+ table + " as h0, " + HTS.HTWORLD_KEEP + " as hk0"
			+ " where h0.id=hk0.world_id and hk0.user_id=? and h0.valid=1 and h0.shield=0 and hk0.valid=1 and hk0.id<=?";
	
	/**
	 * 查询指定用户喜欢的织图
	 */
	private static final String QUERY_LIKED_WORLD = "select h.hlid,h.*,1 as liked,1-ISNULL(hk.user_id) as keep"
			+ " from (select h0.*," + U0_INFO + ",hl0.id as hlid"
			+ " from " + table + " as h0, " + HTS.HTWORLD_LIKED + " as hl0, " + HTS.USER_INFO + " as u0"
			+ " where h0.id=hl0.world_id and h0.author_id=u0.id and hl0.user_id=? and h0.valid=1 and h0.shield=0 and hl0.valid=1 order by hlid desc limit ?,?) h"
			+ " left join (select * from " + HTS.HTWORLD_KEEP + " hk0 where hk0.valid=1 and hk0.user_id=?) as hk"
			+ " on h.id = hk.world_id";
	
	/**
	 * 根据最大id查询指定用户喜欢的织图
	 */
	private static final String QUERY_LIKED_WORLD_BY_MAX_ID = "select h.hlid,h.*,1 as liked,1-ISNULL(hk.user_id) as keep"
			+ " from (select h0.*," + U0_INFO + ",hl0.id as hlid"
			+ " from " + table + " as h0, " + HTS.HTWORLD_LIKED + " as hl0, " + HTS.USER_INFO + " as u0"
			+ " where h0.id=hl0.world_id and h0.author_id=u0.id and hl0.user_id=? and h0.valid=1 and h0.shield=0 and hl0.valid=1 and hl0.id<=? order by hlid desc limit ?,?) h"
			+ " left join (select * from " + HTS.HTWORLD_KEEP + " hk0 where hk0.valid=1 and hk0.user_id=?) as hk"
			+ " on h.id = hk.world_id";
	
	
	/**
	 * 根据最大id查询指定用户喜欢的织图总数
	 */
	private static final String QUERY_LIKED_WORLD_COUNT_BY_MAX_ID = "select count(*) from " 
			+ table + " as h0, " + HTS.HTWORLD_LIKED + " as hl0"
			+ " where h0.id=hl0.world_id and hl0.user_id=? and h0.valid=1 and h0.shield=0 and hl0.valid=1 and hl0.id<=?";
	
	/**
	 * 查询指定用户的织图
	 */
	private static final String QUERY_USER_WORLD = "select h.*,1-ISNULL(hl.user_id) as liked, 1-ISNULL(hk.user_id) as keep from"
			+ " (select h0.*," + U0_INFO + " from " + table + " as h0, " + HTS.USER_INFO + " as u0" 
			+ " where h0.author_id=u0.id and h0.author_id=? and h0.valid=1 and h0.tp=0 order by h0.id desc LIMIT ?,?) as h"
			+ " left join (select * from " + HTS.HTWORLD_LIKED + " hl0 where hl0.valid=1 and hl0.user_id=?) as hl"
			+ " on h.id = hl.world_id"
			+ " left join (select * from " + HTS.HTWORLD_KEEP + " hk0 where hk0.valid=1 and hk0.user_id=?) as hk"
			+ " on h.id = hk.world_id order by h.id desc";
	
	/**
	 * 根据最大id查询指定用户的织图
	 */
	private static final String QUERY_USER_WORLD_BY_MAX_ID = "select h.*,1-ISNULL(hl.user_id) as liked, 1-ISNULL(hk.user_id) as keep from"
			+ " (select h0.*," + U0_INFO  + " from " + table + " as h0, " + HTS.USER_INFO + " as u0" 
			+ " where h0.author_id=u0.id and h0.author_id=? and h0.valid=1 and h0.tp=0 and h0.id<=? order by h0.id desc LIMIT ?,?) as h"
			+ " left join (select * from " + HTS.HTWORLD_LIKED + " hl0 where hl0.valid=1 and hl0.user_id=? and hl0.world_id<=?) as hl"
			+ " on h.id = hl.world_id"
			+ " left join (select * from " + HTS.HTWORLD_KEEP + " hk0 where hk0.valid=1 and hk0.user_id=? and hk0.world_id<=?) as hk"
			+ " on h.id = hk.world_id order by h.id desc";
	
	
	/**
	 * 根据最大id查询指定用户的织图总数
	 */
	private static final String QUERY_USER_WORLD_COUNT_BY_MAX_ID = "select count(*) from " 
			+ table + " as h, " + HTS.USER_INFO + " as u where h.author_id=u.id and h.author_id=? and h.valid=1 and h.id<=?";
	
	/**
	 * 查询指定用的的织图总数
	 */
	private static final String QUERY_USER_WORLD_COUNT= "select count(*) from " 
			+ table + " as h, " + HTS.USER_INFO + " as u where h.author_id=u.id and h.author_id=? and h.valid=?";
	
	
	/**
	 * 查询最新织图
	 */
	private static final String QUERY_LATEST_WORLD = "select h.*, 1-ISNULL(hl.user_id) as liked, 1-ISNULL(hk.user_id) as keep from (select h3.*," 
			+ U0_INFO + " from " + HTS.USER_INFO + " as u0," + table + " as h3"
			+ " where h3.author_id=u0.id and h3.latest_valid>1 and h3.valid=1 and h3.shield=0 order by h3.id desc LIMIT ?,?) as h"
			+ " left join (select * from " + HTS.HTWORLD_LIKED + " hl0 where hl0.valid=1 and hl0.user_id=?) as hl on h.id = hl.world_id"
			+ " left join (select * from " + HTS.HTWORLD_KEEP + " hk0 where hk0.valid=1 and hk0.user_id=?) as hk on h.id = hk.world_id";
	

	/**
	 * 根据最大id查询最新织图
	 */
	private static final String QUERY_LATEST_WORLD_BY_MAX_ID = "select h.*, 1-ISNULL(hl.user_id) as liked, 1-ISNULL(hk.user_id) as keep from (select h3.*," 
			+ U0_INFO + " from " + HTS.USER_INFO + " as u0," + table + " as h3"
			+ " where h3.author_id=u0.id and h3.latest_valid>1 and h3.valid=1 and h3.shield=0 and h3.id<=? order by h3.id desc LIMIT ?,?) as h"
			+ " left join (select * from " + HTS.HTWORLD_LIKED + " hl0 where hl0.valid=1 and hl0.user_id=? and hl0.world_id<=?) as hl on h.id = hl.world_id"
			+ " left join (select * from " + HTS.HTWORLD_KEEP + " hk0 where hk0.valid=1 and hk0.user_id=? and hk0.world_id<=?) as hk on h.id = hk.world_id";
	
	/**
	 * 根据ids查询最新织图SQL头部
	 */
	private static final String QUERY_LATEST_WORLD_BY_IDS_HEAD = "select h.*, 1-ISNULL(hl.user_id) as liked, 1-ISNULL(hk.user_id) as keep from (select h3.*," 
			+ U0_INFO + " from " + HTS.USER_INFO + " as u0," + table + " as h3"
			+ " where h3.author_id=u0.id and h3.latest_valid>1 and h3.valid=1 and h3.shield=0 and h3.id in ";
	
	/**
	 * 根据ids查询最新织图SQL尾部
	 */
	private static final String QUERY_LATEST_WORLD_BY_IDS_FOOT = ") as h"
			+ " left join (select * from " + HTS.HTWORLD_LIKED + " hl0 where hl0.valid=1 and hl0.user_id=?) as hl on h.id = hl.world_id"
			+ " left join (select * from " + HTS.HTWORLD_KEEP + " hk0 where hk0.valid=1 and hk0.user_id=?) as hk on h.id = hk.world_id";
	
	/**
	 * 根据最大id查询最新织图总数
	 */
	private static final String QUERY_LATEST_WORLD_COUNT_BY_MAX_ID = "select count(*) from " + table 
			+ " where latest_valid=1 and valid=1 and shield=0 and id<=?";
	
	/**
	 * 查询最新和用户织图
	 */
	private static final String QUERY_LATEST_AND_USER_WORLD = "select h.*, 1-ISNULL(hl.user_id) as liked, 1-ISNULL(hk.user_id) as keep from (select h3.*," 
			+ U0_INFO + " from " + HTS.USER_INFO + " as u0," 
			+ " (select * from ("
			+ " select *, latest_valid as interact_id from " + table + " where valid=1 and shield=0 and date_added between ? and ? and latest_valid>1"
			+ " UNION ALL "
			+ " select *, id as interact_id from " + table + " where author_id=? and valid=1 and shield=0 and latest_valid=0" 
			+ " ) as h1 order by h1.interact_id desc LIMIT ?,?) as h3"
			+ " where h3.author_id=u0.id) as h"
			+ " left join (select * from " + HTS.HTWORLD_LIKED + " hl0 where hl0.valid=1 and hl0.user_id=?) as hl on h.id = hl.world_id"
			+ " left join (select * from " + HTS.HTWORLD_KEEP + " hk0 where hk0.valid=1 and hk0.user_id=?) as hk on h.id = hk.world_id";
	
	/**
	 * 根据最大id查询最新和用户织图
	 */
	private static final String QUERY_LATEST_AND_USER_WORLD_BY_MAX_ID = "select h.*, 1-ISNULL(hl.user_id) as liked, 1-ISNULL(hk.user_id) as keep from (select h3.*," 
			+ U0_INFO + " from " + HTS.USER_INFO + " as u0," 
			+ " (select * from ("
			+ " select *, latest_valid as interact_id from " + table + " where valid=1 and shield=0 and date_added between ? and ? and latest_valid>1 and latest_valid<=?"
			+ " UNION ALL "
			+ " select *, id as interact_id from " + table + " where author_id=? and valid=1 and shield=0 and latest_valid=0 and id<=?" 
			+ " ) as h1 order by h1.interact_id desc LIMIT ?,?) as h3"
			+ " where h3.author_id=u0.id) as h"
			+ " left join (select * from " + HTS.HTWORLD_LIKED + " hl0 where hl0.valid=1 and hl0.user_id=?) as hl on h.id = hl.world_id"
			+ " left join (select * from " + HTS.HTWORLD_KEEP + " hk0 where hk0.valid=1 and hk0.user_id=?) as hk on h.id = hk.world_id";
	
	
	/**
	 * 更新屏蔽标记
	 */
	private static final String UPDATE_WORLD_SHIELD = "update " + table + " set shield=? where id=?";
	
	/**
	 * 查询织图互动信息
	 */
	private static final String QUERY_WORLD_INTERACT = "select h0.*," + U0_INFO 
			+ " from htworld_htworld as h0, user_info as u0"
			+ " where h0.author_id=u0.id and h0.id=?";
	
	/**
	 * 根据用户id查询所有织图位置信息
	 */
	private static final String QUERY_WORLD_GEO_BY_UID = "select " + GEO_INFO + " from " + table 
			+ " where longitude!=0 and latitude!=0 and author_id=? and valid=? and shield=? limit 1000";
	
	/**
	 * 根据ids删除织图
	 */
	private static final String DELETE_WORLD_BY_IDS = "delete from " + table + " where id in ";
	
	/**
	 * 查询指定用户最新限定数量的织图SQL头部
	 */
	private static final String QUERY_HTWORLD_THUMB_BY_AUTHORID_HEAD = "select o.* from (";
	
	/**
	 * 查询指定用户最新限定数量的织图SQL
	 */
	private static final String QUERY_HTWORLD_THUMB_BY_AUTHORID = "(select " + THUMB_USER  + " from " 
			+ table + " where author_id=? and valid=? and shield=? order by id desc limit ?)";
	
	
	/**
	 * 查询指定用户最新限定数量的织图SQL尾部
	 */
	private static final String QUERY_HTWORLD_THUMB_BY_AUTHORID_FOOT = " ) as o";
	
	private static final String QUERY_HTWORLD_HEAD = "select * from " + table + " where valid=? and latest_valid=? and shield=?";
	
	/** 
	 * 查询最新织图 
	 */
	private static final String QUERY_HTWORLD = QUERY_HTWORLD_HEAD + ORDER_BY_ID_DESC;
	
	/** 
	 * 根据最大id查询最新织图 
	 */
	private static final String QUERY_HTWORLD_BY_MAX_ID = QUERY_HTWORLD_HEAD + " and id<=? " + ORDER_BY_ID_DESC;
	
	/**
	 * 根据描述查询织图
	 */
	private static final String QUERY_HTWORLD_BY_DESC = "select h.*,1-ISNULL(hl.user_id) as liked, 1-ISNULL(hk.user_id) as keep from"
			+ " (select h0.*," + U0_INFO
			+ " from " + table + " as h0, " + HTS.USER_INFO + " as u0" 
			+ " where h0.author_id=u0.id and h0.world_desc like ? and h0.valid=1 and h0.shield=0 order by h0.id desc limit ?,?) as h"
			+ " left join (select * from " + HTS.HTWORLD_LIKED + " hl0 where hl0.valid=1 and hl0.user_id=?) as hl"
			+ " on h.id = hl.world_id"
			+ " left join (select * from " + HTS.HTWORLD_KEEP + " hk0 where hk0.valid=1 and hk0.user_id=?) as hk"
			+ " on h.id = hk.world_id";
	
	/**
	 * 根据描述和最大id查询织图
	 */
	private static final String QUERY_HTWORLD_BY_DESC_AND_MAX_ID = "select h.*,1-ISNULL(hl.user_id) as liked, 1-ISNULL(hk.user_id) as keep from"
			+ " (select h0.*," + U0_INFO
			+ " from " + table + " as h0, " + HTS.USER_INFO + " as u0" 
			+ " where h0.author_id=u0.id and h0.world_desc like ? and h0.valid=1 and h0.shield=0 and h0.id<=? order by h0.id desc limit ?,?) as h"
			+ " left join (select * from " + HTS.HTWORLD_LIKED + " hl0 where hl0.valid=1 and hl0.user_id=? and hl0.world_id<=?) as hl"
			+ " on h.id = hl.world_id"
			+ " left join (select * from " + HTS.HTWORLD_KEEP + " hk0 where hk0.valid=1 and hk0.user_id=? and hk0.world_id<=?) as hk"
			+ " on h.id = hk.world_id";
	
	/**
	 * 根据描述和最大id查询织图总数
	 */
	private static final String QUERY_HTWORLD_COUNT_BY_DESC_AND_MAX_ID = "select count(*) from " + table 
			+ " where world_desc like ? and valid=1 and shield=0 and id<=?";
	
	/**
	 * 根据用户id查询织图总数
	 */
	private static final String QUERY_WORLD_COUNT_BY_AUTHOR_ID = "select count(*) from " + table + 
			" where author_id=? and valid=1";
	
	/**
	 * 根据ids查询缩略图
	 */
	private static final String QUERY_WORLD_TITLE_THUMB = "select id,author_id,world_desc,title_thumb_path,valid from " + table
			+ " where id in ";
	
	private static final String QUERY_LATEST_HEAD = "select t.id,t.ival,h.author_id,h.title_thumb_path from (";
	
	private static final String QUERY_LATEST_FOOT = ") as t, " + table + " as h where h.id=t.id";
	
	private static final String QUERY_LATEST_MAIN = "(select t0.id,? as ival from ("
			+ " (select id from " + table
			+ " where author_id != ? and date_added <= ? and date_added > ? and latest_valid > 1 and valid=1 and shield=0"
			+ " order by id desc limit ?)"
			+ " union all"
			+ " (select id from " + table
			+ " where date_added <= ? and date_added > ? and author_id=? and valid=1"
			+ " order by id desc limit ?)) as t0 order by id desc limit ?)";
	
	private static final String QUERY_LATEST_INDEX_MAIN = "(select sum(c) as total,? as ival from ("
			+ " (select count(*) c from " + table
			+ " where author_id != ? and date_added <= ? and date_added > ? and latest_valid > 1 and valid=1 and shield=0)"
			+ " union all"
			+ " (select count(*) c from " + table
			+ " where date_added <= ? and date_added > ? and author_id=? and valid=1)) as o)";
	
	private static final String QUERY_LATEST = "select t.id,h.author_id,h.title_thumb_path from "
			+ " (select t0.id from ("
			+ " (select id from " + table
			+ " where id<=? and author_id != ? and date_added <= ? and date_added > ? and latest_valid>1 and valid=1 and shield=0"
			+ " order by id desc limit ?)"
			+ " union all"
			+ " (select id from " + table
			+ " where id<=? and date_added <= ? and date_added > ? and author_id=?"
			+ " order by id desc limit ?)) as t0 order by id desc limit ?) as t," + table + " as h where h.id=t.id";
	
	
	private static final String QUERY_LATEST_COUNT = " select sum(c) as total from ("
			+ " (select count(*) c from " + table
			+ " where id<=? and author_id != ? and date_added <= ? and date_added > ? and latest_valid>1 and valid=1 and shield=0)"
			+ " union all"
			+ " (select count(*) c from " + table
			+ " where id<=? and date_added <= ? and date_added > ? and author_id=?)) as t0";
	
	private static final String QUERY_LATEST_BY_MAX_DATE = "select t.id,h.author_id,h.title_thumb_path from "
			+ " (select t0.id from ("
			+ " (select id from " + table
			+ " where id<=? and author_id != ? and date_added <= ? and latest_valid>1 and valid=1 and shield=0"
			+ " order by id desc limit ?)"
			+ " union all"
			+ " (select id from " + table
			+ " where id<=? and date_added <= ? and author_id=? and valid=1"
			+ " order by id desc limit ?)) as t0 order by id desc limit ?) as t," + table + " as h where h.id=t.id";
	
	
	private static final String QUERY_LATEST_COUNT_BY_MAX_DATE = "select sum(c) total from ("
			+ " (select count(*) c from " + table
			+ " where id<=? and author_id != ? and date_added <= ? and latest_valid>1 and valid=1 and shield=0)"
			+ " union all"
			+ " (select count(*) c from " + table
			+ " where id<=? and date_added <= ? and author_id=? and valid=1)) as t0";
	
	
	
	private static final String QUERY_LATEST_HEAD_V2 = "select t.id,t.latest_valid,t.ival,h.author_id,h.title_thumb_path from (";
	
	private static final String QUERY_LATEST_FOOT_V2 = ") as t, " + table + " as h where h.id=t.id";
	
	private static final String QUERY_LATEST_MAIN_V2 = "(select t0.id,t0.latest_valid,? as ival from ("
			+ " (select id,latest_valid from " + table
			+ " where author_id != ? and date_added <= ? and date_added > ? and latest_valid > 1 and valid=1 and shield=0"
			+ " order by latest_valid desc limit ?)"
			+ " union all"
			+ " (select id,latest_valid from " + table
			+ " where date_added <= ? and date_added > ? and author_id=? and valid=1"
			+ " order by latest_valid desc limit ?)) as t0 order by latest_valid desc limit ?)";
	
	private static final String QUERY_LATEST_INDEX_MAIN_V2 = "(select sum(c) as total,? as ival from ("
			+ " (select count(*) c from " + table
			+ " where author_id != ? and date_added <= ? and date_added > ? and latest_valid > 1 and valid=1 and shield=0)"
			+ " union all"
			+ " (select count(*) c from " + table
			+ " where date_added <= ? and date_added > ? and author_id=? and valid=1)) as o)";
	
	private static final String QUERY_LATEST_V2 = "select t.id,t.latest_valid,h.author_id,h.title_thumb_path from "
			+ " (select t0.id,t0.latest_valid from ("
			+ " (select id,latest_valid from " + table
			+ " where latest_valid<=? and author_id != ? and date_added <= ? and date_added > ? and latest_valid>1 and valid=1 and shield=0"
			+ " order by latest_valid desc limit ?)"
			+ " union all"
			+ " (select id,latest_valid from " + table
			+ " where latest_valid<=? and date_added <= ? and date_added > ? and author_id=?"
			+ " order by latest_valid desc limit ?)) as t0 order by latest_valid desc limit ?) as t," + table + " as h where h.id=t.id";
	
	private static final String QUERY_LATEST_COUNT_V2 = " select sum(c) as total from ("
			+ " (select count(*) c from " + table
			+ " where latest_valid<=? and author_id != ? and date_added <= ? and date_added > ? and latest_valid>1 and valid=1 and shield=0)"
			+ " union all"
			+ " (select count(*) c from " + table
			+ " where latest_valid<=? and date_added <= ? and date_added > ? and author_id=?)) as t0";
	
	
	private static final String QUERY_LATEST_BY_MAX_DATE_V2 = "select t.id,t.latest_valid,h.author_id,h.title_thumb_path from "
			+ " (select t0.id,t0.latest_valid from ("
			+ " (select id,latest_valid from " + table
			+ " where latest_valid<=? and author_id != ? and date_added <= ? and latest_valid>1 and valid=1 and shield=0"
			+ " order by latest_valid desc limit ?)"
			+ " union all"
			+ " (select id,latest_valid from " + table
			+ " where latest_valid<=? and date_added <= ? and author_id=? and valid=1"
			+ " order by latest_valid desc limit ?)) as t0 order by latest_valid desc limit ?) as t," + table + " as h where h.id=t.id";
	

	private static final String QUERY_LATEST_COUNT_BY_MAX_DATE_V2 = "select sum(c) total from ("
			+ " (select count(*) c from " + table
			+ " where latest_valid<=? and author_id != ? and date_added <= ? and latest_valid>1 and valid=1 and shield=0)"
			+ " union all"
			+ " (select count(*) c from " + table
			+ " where latest_valid<=? and date_added <= ? and author_id=? and valid=1)) as t0";
	
	/**
	 * 查询作者id
	 */
	private static final String QUERY_AUTHOR_ID = "select author_id from " + table
			+ " where id=?";
	
	/**
	 * 根据最大id查询最新id
	 */
	private static final String QUERY_LATEST_ID_BY_MAX = "select id, latest_valid from " + table
			+ " where latest_valid>1 and valid=1 and shield=0 and latest_valid<=? order by latest_valid desc limit ?";
	
	/**
	 * 根据最小id查询最新id
	 */
	private static final String QUERY_LATEST_ID_BY_MIN = "select id, latest_valid from " + table
			+ " where latest_valid>1 and valid=1 and shield=0 and latest_valid>=? order by latest_valid desc limit ?";
	
	/**
	 * 查询计数
	 */
	private static final String QUERY_COUNT = "select id,like_count,click_count,comment_count from " + table 
			+ " where id in ";
	
	/**
	 * 查询图片总数
	 */
	private static final String QUERY_CHILD_SUM = "select sum(child_count) from " + table
			+ " where author_id=? and valid=1";
	
	/**
	 * 根据id查询图片总数
	 */
	private static final String QUERY_CHILD_COUNT_BY_ID = "select child_count from " + table
			+ " where id=?";

	/**
	 * 查询有效性
	 */
	private static final String QUERY_VALID = "select valid from " + table
			+ " where id=?";
	
	private static final String QUERY_LAST_N_WORLD_INFO_BY_USER_ID = "select id,title_path from " + table + " where author_id= ? and valid=1 and shield=0 order by id desc limit ?";
	
	@Override
	public void updateWorldShield(Integer worldId, Integer shield) {
		getMasterJdbcTemplate().update(UPDATE_WORLD_SHIELD, new Object[]{shield, worldId});
	}
	
	@Override
	public void saveWorld(HTWorld htworld) {
		String styleStr = null;
		HTWorldTextStyle style = htworld.getTextStyle();
		if(style != null) {
			styleStr = JSONObject.fromObject(style).toString();
		}
		
		List<HTWorldChannelName> channelNames = htworld.getChannelNames();
		String channelName = null;
		String channelId = null;
		if(channelNames != null) {
			String[] names = parseChannelNames(channelNames);
			channelName = names[0];
			channelId = names[1];
		}
		getMasterJdbcTemplate().update(SAVE_WORLD, new Object[]{
				htworld.getId(),
				htworld.getShortLink(),
				htworld.getWorldName(),
				htworld.getWorldDesc(),
				htworld.getWorldLabel(),
				htworld.getWorldType(),
				htworld.getTypeId(),
				htworld.getDateAdded(),
				htworld.getDateModified(),
				htworld.getAuthorId(),
				htworld.getCoverPath(),
				htworld.getTitlePath(),
				htworld.getBgPath(),
				htworld.getTitleThumbPath(),
				channelName,
				channelId,
				htworld.getLongitude(),
				htworld.getLatitude(),
				htworld.getLocationDesc(),
				htworld.getLocationAddr(),
				htworld.getPhoneCode(),
				htworld.getProvince(),
				htworld.getCity(),
				htworld.getSize(),
				htworld.getChildCount(),
				htworld.getVer(),
				htworld.getTp(),
				htworld.getValid(),
				htworld.getLatestValid(),
				htworld.getShield(),
				styleStr
		});
	}
	
	@Override
	public int addClickCount(Integer worldId, Integer count) {
		return getMasterJdbcTemplate().update(ADD_CLICK_COUNT, new Object[]{count, worldId, Tag.TRUE});
	}

	@Override
	public int updateCommentCount(Integer worldId, Integer count) {
		return getMasterJdbcTemplate().update(UPDATE_COMMENT_COUNT, new Object[]{count, worldId});
	}

	@Override
	public int queryLikeCount(Integer worldId) {
		return getJdbcTemplate().queryForInt(QUERY_LIKE_COUNT, worldId);
	}
	
	@Override
	public int updateLikeCount(Integer worldId, Integer count) {
		return getMasterJdbcTemplate().update(UPDATE_LIKE_COUNT, new Object[]{count, worldId});
	}
	
	@Override
	public int updateKeepCount(Integer worldId, Integer count) {
		return getMasterJdbcTemplate().update(UPDATE_KEEP_COUNT, 
				new Object[]{count, worldId});
	}

	@Override
	public UserInfo queryUserInfoByWorldId(Integer worldId) {
		return queryForObjectWithNULL(QUERY_USER_INFO_BY_WORLD_ID, 
				new Object[]{worldId}, new RowMapper<UserInfo>() {

			@Override
			public UserInfo mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return UserInfoDaoImpl.buildUserInfoAndPassByResultSet(rs);
			}
		});
		
	}
	
	@Override
	public HTWorld queryWorldById(Integer id) {
		return queryForObjectWithNULL(QUERY_HTWORLD_BY_ID, new RowMapper<HTWorld>(){

			@Override
			public HTWorld mapRow(ResultSet rs, int rowNum) throws SQLException {
				return buildHTWorldByResultSet(rs);
			}
			
		}, id);
	}

	@Override
	public List<HTWorldInteractDto> queryConcernWorld(Integer userId, RowSelection rowSelection) {
		return getJdbcTemplate().query(QUERY_CONCERN_WORLD, new Object[]{
				userId,rowSelection.getLimit(),
				userId,rowSelection.getLimit(),
				rowSelection.getLimit()},
				new RowMapper<HTWorldInteractDto>() {

					@Override
					public HTWorldInteractDto mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						HTWorldInteractDto dto = buildHTWorldInteractDto(rs);
						return dto;
					}
				});
	}
	
	@Override
	public List<HTWorldInteractDto> queryConcernWorld(Integer userId, Integer maxId, RowSelection rowSelection) {
		return getJdbcTemplate().query(QUERY_CONCERN_WORLD_BY_MAX_ID, new Object[]{
				userId,maxId,rowSelection.getLimit(),
				userId,maxId,rowSelection.getLimit(),
				rowSelection.getLimit()},
				new RowMapper<HTWorldInteractDto>() {

					@Override
					public HTWorldInteractDto mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						HTWorldInteractDto dto = buildHTWorldInteractDto(rs);
						return dto;
					}
				});
	}
	
	@Override
	public Long queryConcernWorldCountByMaxId(Integer userId, Integer maxId) {
		return getJdbcTemplate().queryForLong(QUERY_CONCERN_WORLD_COUNT_BY_MAX_ID, new Object[]{
				userId,maxId,userId,maxId});
	}
	
	@Override
	public List<HTWorldInteractDto> queryKeepWorld(Integer userId, RowSelection rowSelection) {
		return getJdbcTemplate().query(QUERY_KEEP_WORLD, 
				new Object[]{userId,rowSelection.getFirstRow(),rowSelection.getLimit(),userId},
				new RowMapper<HTWorldInteractDto>() {

					@Override
					public HTWorldInteractDto mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						HTWorldInteractDto dto = buildHTWorldInteractDtoByResultSet(rs);
						dto.setInteractId(rs.getInt("hkid"));
						return dto;
					}
				});
	}
	
	@Override
	public List<HTWorldInteractDto> queryKeepWorld(Integer userId, Integer maxId, RowSelection rowSelection) {
		return getJdbcTemplate().query(QUERY_KEEP_WORLD_BY_MAX_ID, 
				new Object[]{userId,maxId,rowSelection.getFirstRow(),rowSelection.getLimit(),userId},
				new RowMapper<HTWorldInteractDto>() {

					@Override
					public HTWorldInteractDto mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						HTWorldInteractDto dto = buildHTWorldInteractDtoByResultSet(rs);
						dto.setInteractId(rs.getInt("hkid"));
						return dto;
					}
				});
	}

	@Override
	public Long queryKeepWorldCountByMaxId(Integer userId, Integer maxId) {
		return getJdbcTemplate().queryForLong(QUERY_KEEP_WORLD_COUNT_BY_MAX_ID, new Object[]{
				userId,maxId});
	}
	
	@Override
	public List<HTWorldInteractDto> queryLikedWorld(Integer userId, RowSelection rowSelection) {
		return getJdbcTemplate().query(QUERY_LIKED_WORLD, 
				new Object[]{userId,rowSelection.getFirstRow(),rowSelection.getLimit(),userId},
				new RowMapper<HTWorldInteractDto>() {

					@Override
					public HTWorldInteractDto mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						HTWorldInteractDto dto = buildHTWorldInteractDtoByResultSet(rs);
						dto.setInteractId(rs.getInt("hlid"));
						return dto;
					}
				});
	}
	
	@Override
	public List<HTWorldInteractDto> queryLikedWorld(Integer userId, Integer maxId, RowSelection rowSelection) {
		return getJdbcTemplate().query(QUERY_LIKED_WORLD_BY_MAX_ID, 
				new Object[]{userId,maxId,rowSelection.getFirstRow(),rowSelection.getLimit(),userId},
				new RowMapper<HTWorldInteractDto>() {

					@Override
					public HTWorldInteractDto mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						HTWorldInteractDto dto = buildHTWorldInteractDtoByResultSet(rs);
						dto.setInteractId(rs.getInt("hlid"));
						return dto;
					}
				});
	}


	@Override
	public Long queryUserWorldCount(Integer userId) {
		return getJdbcTemplate().queryForLong(QUERY_USER_WORLD_COUNT, new Object[]{userId,Tag.TRUE});
	}
	
	@Override
	public Long queryLikedWorldCountByMaxId(Integer userId, Integer maxId) {
		return getJdbcTemplate().queryForLong(QUERY_LIKED_WORLD_COUNT_BY_MAX_ID, new Object[]{
				userId, maxId});
	}
	
	@Override
	public List<HTWorldInteractDto> queryUserWorld(Integer userId, Integer joinId, RowSelection rowSelection) {
		return getJdbcTemplate().query(QUERY_USER_WORLD, 
				new Object[]{userId,rowSelection.getFirstRow(),rowSelection.getLimit(),joinId,joinId}, 
				new RowMapper<HTWorldInteractDto>() {

					@Override
					public HTWorldInteractDto mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						return buildHTWorldInteractDtoByResultSet(rs);
					}
				});
	}
	
	@Override
	public List<HTWorldInteractDto> queryUserWorld(Integer userId, Integer joinId, Integer maxId, RowSelection rowSelection){
		return getJdbcTemplate().query(QUERY_USER_WORLD_BY_MAX_ID,
				new Object[]{userId,maxId,rowSelection.getFirstRow(),rowSelection.getLimit(),joinId,maxId,joinId,maxId},
				new RowMapper<HTWorldInteractDto>() {

					@Override
					public HTWorldInteractDto mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						return buildHTWorldInteractDtoByResultSet(rs);
					}
				});
		
	}

	@Override
	public Long queryUserWorldCountByMaxId(Integer userId, Integer maxId) {
		return getJdbcTemplate().queryForLong(QUERY_USER_WORLD_COUNT_BY_MAX_ID, new Object[]{
				userId,maxId});
	}
	
	@Override
	public List<HTWorldInteractDto> queryLatestWorld(Integer joinId, RowSelection rowSelection) {
		return getJdbcTemplate().query(QUERY_LATEST_WORLD,
				new Object[]{rowSelection.getFirstRow(),rowSelection.getLimit(),joinId,joinId},
				new RowMapper<HTWorldInteractDto>() {

					@Override
					public HTWorldInteractDto mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						return buildHTWorldInteractDtoByResultSet(rs);
					}
				});
	}

	@Override
	public List<HTWorldInteractDto> queryLatestWorld(Integer joinId, Integer maxId,
			RowSelection rowSelection) {
		return getJdbcTemplate().query(QUERY_LATEST_WORLD_BY_MAX_ID,
				new Object[]{maxId,rowSelection.getFirstRow(),rowSelection.getLimit(),joinId,maxId,joinId,maxId},
				new RowMapper<HTWorldInteractDto>() {

					@Override
					public HTWorldInteractDto mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						return buildHTWorldInteractDtoByResultSet(rs);
					}
				});
	}
	
	@Override
	public void queryLatestWorld(Integer joinId,
			Integer[] ids, final RowCallback<HTWorldInteractDto> callback) {
		String inSelection = SQLUtil.buildInSelection(ids);
		String sql = QUERY_LATEST_WORLD_BY_IDS_HEAD + inSelection + QUERY_LATEST_WORLD_BY_IDS_FOOT;
		Object[] args = SQLUtil.getArgsByInCondition(ids, new Object[]{joinId, joinId}, false);
		getJdbcTemplate().query(sql, args, new RowCallbackHandler() {
			
			@Override
			public void processRow(ResultSet rs) throws SQLException {
				HTWorldInteractDto dto = buildHTWorldInteractDtoByResultSet(rs);
				callback.callback(dto);
			}
		});
	}
	
	public List<HTWorldInteractDto> queryLatestAndUserWorld(Integer joinId, 
			Date beginDate, Date endDate, RowSelection rowSelection) {
		return getJdbcTemplate().query(QUERY_LATEST_AND_USER_WORLD,
				new Object[]{beginDate,endDate,joinId,rowSelection.getFirstRow(),rowSelection.getLimit(),joinId,joinId},
				new RowMapper<HTWorldInteractDto>() {

					@Override
					public HTWorldInteractDto mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						HTWorldInteractDto dto = buildHTWorldInteractDtoByResultSet(rs);
						dto.setInteractId(rs.getInt("interact_id"));
						return dto;
					}
				});
	}
	
	public List<HTWorldInteractDto> queryLatestAndUserWorld(Integer maxId, Integer joinId, 
			Date beginDate, Date endDate, RowSelection rowSelection) {
		return getJdbcTemplate().query(QUERY_LATEST_AND_USER_WORLD_BY_MAX_ID,
				new Object[]{beginDate,endDate,maxId,joinId,maxId,rowSelection.getFirstRow(),rowSelection.getLimit(),joinId,joinId},
				new RowMapper<HTWorldInteractDto>() {

					@Override
					public HTWorldInteractDto mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						HTWorldInteractDto dto = buildHTWorldInteractDtoByResultSet(rs);
						dto.setInteractId(rs.getInt("interact_id"));
						return dto;
					}
				});
	}
	

	@Override
	public Long queryLatestWorldCount(Integer maxId) {
		return getJdbcTemplate().queryForLong(QUERY_LATEST_WORLD_COUNT_BY_MAX_ID, new Object[]{maxId});
	}
	
	public List<HTWorldDto> queryHTWorldDtoByUserId(Integer userId, RowSelection rowSelection) {
		return queryForPage(QUERY_HTWORLD_DTO_BY_AUTHOR_ID, new Object[]{Tag.TRUE, Tag.FALSE, userId},
				new RowMapper<HTWorldDto>() {

					@Override
					public HTWorldDto mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						return buildHTWorldDtoByResulSet(rs);
					}
				},rowSelection);
	}
	
	@Override
	public HTWorldDto queryHTWorldDtoById(Integer worldId) {
		return queryForObjectWithNULL(QUERY_HTWORLD_DTO_BY_ID, new RowMapper<HTWorldDto>(){

			@Override
			public HTWorldDto mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return buildHTWorldDtoByResulSet(rs);
			}
			
		}, new Object[]{Tag.TRUE, Tag.FALSE, worldId});
	}
	
	@Override
	public HTWorldDto queryHTWorldDtoByShortLink(String shortLink) {
		return queryForObjectWithNULL(QUERY_HTWORLD_DTO_BY_SHORT_LINK, new RowMapper<HTWorldDto>(){

			@Override
			public HTWorldDto mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return buildHTWorldDtoByResulSet(rs);
			}
			
		}, new Object[]{Tag.TRUE, Tag.FALSE, shortLink});
	}
	
	@Override
	public HTWorldDto queryHTWorldDtoByIdNoValidCheck(Integer worldId) {
		return queryForObjectWithNULL(QUERY_HTWORLD_DTO_BY_ID_NO_VALID_CHECK, new RowMapper<HTWorldDto>(){

			@Override
			public HTWorldDto mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return buildHTWorldDtoByResulSet(rs);
			}
			
		}, new Object[]{worldId});
	};
	
	@Override
	public HTWorldDto queryHTWorldDtoByShortLinkNoValidCheck(String shortLink) {
		return queryForObjectWithNULL(QUERY_HTWORLD_DTO_BY_SHORT_LINK_NO_VALID_CHECK, new RowMapper<HTWorldDto>(){

			@Override
			public HTWorldDto mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return buildHTWorldDtoByResulSet(rs);
			}
			
		}, new Object[]{shortLink});
	};
	
	@Override
	public HTWorldInteractDto queryHTWorldInteract(Integer worldId) {
		return queryForObjectWithNULL(QUERY_WORLD_INTERACT, new RowMapper<HTWorldInteractDto>() {

			@Override
			public HTWorldInteractDto mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return buildHTWorldInteractDto(rs);
			}
			
		}, new Object[]{worldId});
	}
	
	@Override
	public void updateWorld(Integer worldId, Map<String, Object> attrMap) {
		String sql = SQLUtil.buildUpdateSQL(table, attrMap, "id=?");
		List<Object> argsList = new ArrayList<Object>();
		CollectionUtil.collectMapValues(argsList, attrMap);
		attrMap.clear();
		attrMap = null;
		argsList.add(worldId);
		getMasterJdbcTemplate().update(sql, argsList.toArray());
	}
	
	@Override
	public List<HTWorldGeo> queryWorldGeo(Integer userId) {
		return getJdbcTemplate().query(QUERY_WORLD_GEO_BY_UID, new RowMapper<HTWorldGeo>(){

			@Override
			public HTWorldGeo mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return buildHTWorldGeoByResultSet(rs);
			}
			
		}, new Object[]{userId, Tag.TRUE, Tag.FALSE});
	}
	
	@Override
	public void queryHTWorldThumbUserByLimit(Integer[] ids, Integer limit, final 
			RowCallback<HTWorldThumbUser> callback) {
		Object[] args = new Object[ids.length * 4];
		StringBuilder sqlBuilder = new StringBuilder(QUERY_HTWORLD_THUMB_BY_AUTHORID_HEAD);
		for(int i = 0; i < ids.length; i++) {
			if(i != 0) {
				sqlBuilder.append(" union all ");
			}
			sqlBuilder.append(QUERY_HTWORLD_THUMB_BY_AUTHORID);
			int k = i * 4;
			args[k] = ids[i];
			args[k+1] = Tag.TRUE;
			args[k+2] = Tag.FALSE;
			args[k+3] = limit;
		}
		
		sqlBuilder.append(QUERY_HTWORLD_THUMB_BY_AUTHORID_FOOT);
		getJdbcTemplate().query(sqlBuilder.toString(), args, new RowCallbackHandler() {
			
			@Override
			public void processRow(ResultSet rs) throws SQLException {
				HTWorldThumbUser thumb = buildWorldThumbUser(rs);
				callback.callback(thumb);
			}
		});
	}
	
	
	@Override
	public List<HTWorldInteractDto> queryWorldInteractByDesc(Integer userId, 
			String desc, RowSelection rowSelection) {
		return getJdbcTemplate().query(QUERY_HTWORLD_BY_DESC, 
				new Object[]{"%"+desc+"%", rowSelection.getFirstRow(),
					rowSelection.getLimit(), userId, userId},
				new RowMapper<HTWorldInteractDto>() {

			@Override
			public HTWorldInteractDto mapRow(ResultSet rs, int num)
					throws SQLException {
				return buildHTWorldInteractDtoByResultSet(rs);
			}
		});
	}


	@Override
	public List<HTWorldInteractDto> queryWorldInteractByDesc(Integer maxId,
			Integer userId, String desc, RowSelection rowSelection) {
		return getJdbcTemplate().query(QUERY_HTWORLD_BY_DESC_AND_MAX_ID, 
				new Object[]{"%"+desc+"%", maxId, rowSelection.getFirstRow(),
					rowSelection.getLimit(), userId, maxId, userId, maxId},
				new RowMapper<HTWorldInteractDto>() {

			@Override
			public HTWorldInteractDto mapRow(ResultSet rs, int num)
					throws SQLException {
				return buildHTWorldInteractDtoByResultSet(rs);
			}
		});
	}


	@Override
	public long queryWorldInteractCountByDesc(Integer maxId, String desc) {
		return getJdbcTemplate().queryForLong(QUERY_HTWORLD_COUNT_BY_DESC_AND_MAX_ID, 
				new Object[]{"%"+desc+"%", maxId});
	}

	
	/**
	 * 从元数据构建HTWorld POJO对象
	 * @param meta
	 * @return
	 * @throws SQLException 
	 */
	public HTWorld buildHTWorldByResultSet(ResultSet rs) throws SQLException {
		List<HTWorldChannelName> channelNames = formatChannelNames(
						rs.getString("channel_name"),
						rs.getString("channel_id"));
		HTWorld world = new HTWorld(
				rs.getInt("id"), 
				rs.getString("short_link"), 
				rs.getInt("author_id"), 
				rs.getString("world_name"),
				rs.getString("world_desc"),
				rs.getString("world_label"),
				rs.getString("world_type"),
				rs.getInt("type_id"),
				(Date)rs.getObject("date_added"),
				(Date)rs.getObject("date_modified"),
				rs.getInt("click_count"),
				rs.getInt("like_count"),
				rs.getInt("comment_count"),
				rs.getInt("keep_count"),
				rs.getString("cover_path"),
				rs.getString("title_path"),
				rs.getString("bg_path"),
				rs.getString("title_thumb_path"),
				channelNames,
				rs.getDouble("longitude"),
				rs.getDouble("latitude"), 
				rs.getString("location_desc"),
				rs.getString("location_addr"),
				rs.getInt("phone_code"),
				rs.getString("province"),
				rs.getString("city"),
				rs.getInt("size"),
				rs.getInt("child_count"),
				rs.getInt("ver"),
				rs.getInt("tp"),
				rs.getInt("valid"),
				rs.getInt("shield"),
				JSONUtil.getJSObjectFromText(rs.getString("text_style")));
		world.setWorldURL(urlPrefix + world.getShortLink());
		return world;
	}
	
	/**
	 * 根据结果集构建关注的织图数据传输对象
	 * 
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public HTWorldInteractDto buildHTWorldInteractDtoByResultSet(ResultSet rs) throws SQLException {
		HTWorldInteractDto dto = buildHTWorldInteractDto(rs);
		dto.setLiked(rs.getObject("liked"));
		dto.setKeep(rs.getObject("keep"));
		return dto;
	}
	
	public HTWorldInteractDto buildHTWorldInteractDto(ResultSet rs) throws SQLException {
		List<HTWorldChannelName> channelNames = formatChannelNames(
				rs.getString("channel_name"),
				rs.getString("channel_id"));
		HTWorldInteractDto dto = new HTWorldInteractDto(
				rs.getInt("id"),
				rs.getString("short_link"),
				rs.getInt("author_id"),
				rs.getString("world_name"), 
				rs.getString("world_desc"), 
				rs.getString("world_label"),
				rs.getString("world_type"),
				rs.getInt("type_id"),
				(Date)rs.getObject("date_added"), 
				(Date)rs.getObject("date_modified"), 
				rs.getInt("click_count"),
				rs.getInt("like_count"),
				rs.getInt("comment_count"), 
				rs.getInt("keep_count"), 
				rs.getString("cover_path"), 
				rs.getString("title_path"),
				rs.getString("bg_path"),
				rs.getString("title_thumb_path"), 
				channelNames,
				rs.getDouble("longitude"),
				rs.getDouble("latitude"), 
				rs.getString("location_desc"),
				rs.getString("location_addr"), 
				rs.getInt("phone_code"), 
				rs.getString("province"), 
				rs.getString("city"),
				rs.getInt("size"),
				rs.getInt("child_count"),
				rs.getInt("ver"),
				rs.getInt("tp"),
				rs.getInt("valid"),
				rs.getInt("shield"),
				JSONUtil.getJSObjectFromText(rs.getString("text_style")));
		dto.setWorldURL(urlPrefix + dto.getShortLink());
		UserInfoDto userInfo = UserInfoDaoImpl.buildUserInfoDtoByResult(dto.getAuthorId(), rs);
		dto.setUserInfo(userInfo);
		return dto;
	}
	
	/**
	 * 根据结果集构建HTWorldDto
	 * 
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public HTWorldDto buildHTWorldDtoByResulSet(ResultSet rs) throws SQLException {
		List<HTWorldChannelName> channelNames = formatChannelNames(
				rs.getString("channel_name"),
				rs.getString("channel_id"));
		HTWorldDto dto = new HTWorldDto(
				rs.getInt("id"),
				rs.getString("short_link"),
				rs.getInt("author_id"),
				rs.getString("world_name"), 
				rs.getString("world_desc"), 
				rs.getString("world_label"),
				rs.getString("world_type"),
				rs.getInt("type_id"),
				(Date)rs.getObject("date_added"), 
				(Date)rs.getObject("date_modified"), 
				rs.getInt("click_count"),
				rs.getInt("like_count"),
				rs.getInt("comment_count"), 
				rs.getInt("keep_count"), 
				rs.getString("cover_path"), 
				rs.getString("title_path"),
				rs.getString("bg_path"),
				rs.getString("title_thumb_path"),
				channelNames,
				rs.getDouble("longitude"),
				rs.getDouble("latitude"), 
				rs.getString("location_desc"),
				rs.getString("location_addr"), 
				rs.getInt("phone_code"), 
				rs.getString("province"), 
				rs.getString("city"),
				rs.getInt("size"),
				rs.getInt("child_count"),
				rs.getInt("ver"),
				rs.getInt("tp"),
				rs.getInt("valid"),
				rs.getInt("shield"),
				JSONUtil.getJSObjectFromText(rs.getString("text_style")));
		dto.setWorldURL(urlPrefix + dto.getShortLink());
		UserInfoDto userInfo = UserInfoDaoImpl.buildUserInfoDtoByResult(dto.getAuthorId(), rs);
		dto.setUserInfo(userInfo);
		return dto;
	}
	
	@Override
	public void deleteByIds(Integer[] ids) {
		String inSelection = SQLUtil.buildInSelection(ids);
		String sql = DELETE_WORLD_BY_IDS + inSelection;
		getMasterJdbcTemplate().update(sql, (Object[])ids);
	}
	

	@Override
	public List<HTWorld> queryHTWorld(RowSelection rowSelection) {
		return queryForPage(QUERY_HTWORLD, new Object[]{Tag.TRUE, Tag.TRUE, Tag.FALSE}, new RowMapper<HTWorld>() {

			@Override
			public HTWorld mapRow(ResultSet rs, int rowNum) throws SQLException {
				return buildHTWorldByResultSet(rs);
			}
		}, rowSelection);
		
	}

	@Override
	public List<HTWorld> queryHTWorld(Integer maxId, RowSelection rowSelection) {
		return queryForPage(QUERY_HTWORLD_BY_MAX_ID, new Object[]{Tag.TRUE, Tag.TRUE, Tag.FALSE, maxId}, new RowMapper<HTWorld>() {

			@Override
			public HTWorld mapRow(ResultSet rs, int rowNum) throws SQLException {
				return buildHTWorldByResultSet(rs);
			}
		}, rowSelection);
	}
	
	@Override
	public long queryWorldCountByAuthorId(Integer authorId) {
		return getMasterJdbcTemplate().queryForLong(QUERY_WORLD_COUNT_BY_AUTHOR_ID,
				new Object[]{authorId});
	}
	
	@Override
	public List<HTWorldThumbnail> queryWorldThumbnail(Integer[] wids) {
		String inSelection = SQLUtil.buildInSelection(wids);
		String sql = QUERY_WORLD_TITLE_THUMB + inSelection;
		return getJdbcTemplate().query(sql, wids, new RowMapper<HTWorldThumbnail>() {

			@Override
			public HTWorldThumbnail mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return new HTWorldThumbnail(
						rs.getInt("id"),
						rs.getInt("author_id"),
						rs.getString("title_thumb_path"),
						rs.getString("world_desc"),
						rs.getInt("valid"));
			}
			
		});
	}


	/**
	 * 从结果集构建织图缩略图信息
	 * 
	 * @param worldId
	 * @param rs
	 * @return
	 * @throws SQLException 
	 */
	@Override
	public HTWorldThumbDto buildHTWorldThumbDtoByResultSet(Integer worldId, ResultSet rs) throws SQLException {
		return new HTWorldThumbDto(worldId,
				rs.getString("cover_path"),
				rs.getString("title_path"),
				rs.getString("bg_path"),
				rs.getString("title_thumb_path"),
				rs.getInt("valid"),
				rs.getInt("shield"));
	}
	
	/**
	 * 从结果集构建HTWorldGeo
	 * 
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public HTWorldGeo buildHTWorldGeoByResultSet(ResultSet rs) throws SQLException {
		return new HTWorldGeo(
				rs.getInt("id"),
				rs.getString("title_path"),
				rs.getString("title_thumb_path"),
				rs.getDouble("longitude"), 
				rs.getDouble("latitude"),
				rs.getString("location_desc"),
				rs.getString("location_addr"));
	}
	
	public HTWorldThumbUser buildWorldThumbUser(ResultSet rs) throws SQLException {
		return new HTWorldThumbUser(rs.getInt("id"), 
				rs.getInt("author_id"), 
				rs.getString("cover_path"), 
				rs.getString("title_path"), 
				rs.getString("bg_path"),
				rs.getString("title_thumb_path"));
	}

	
	@Override
	public List<HTWorldLatest> queryLatest(Integer userId, long initTime, int preInterval, int[] intervals, int[] limits) {
		StringBuilder builder = new StringBuilder(QUERY_LATEST_HEAD);
		Object[] params = new Object[intervals.length * 10];
		long startTime = initTime - preInterval;
		for(int i = 0; i < intervals.length; i++) {
			if(i == 0) 
				builder.append(QUERY_LATEST_MAIN);
			else 
				builder.append(" union all ").append(QUERY_LATEST_MAIN);
			long endTime = initTime - intervals[i];
			Date beginDate = new Date(startTime);
			Date endDate = new Date(endTime);
			params[i*10 + 0] = intervals[i];
			params[i*10 + 1] = userId;
			params[i*10 + 2] =  beginDate;
			params[i*10 + 3] =  endDate;
			params[i*10 + 4] = limits[i];
			params[i*10 + 5] = beginDate;
			params[i*10 + 6] = endDate;
			params[i*10 + 7] = userId;
			params[i*10 + 8] = limits[i];
			params[i*10 + 9] = limits[i];
			startTime = endTime; 
		}
		builder.append(QUERY_LATEST_FOOT);
		return getJdbcTemplate().query(builder.toString(), params,
				new RowMapper<HTWorldLatest>() {

			@Override
			public HTWorldLatest mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return new HTWorldLatest(
						rs.getInt("id"),
						rs.getInt("author_id"),
						rs.getString("title_thumb_path"),
						rs.getInt("ival"));
			}
		});
	}

	@Override
	public List<HTWorldLatestIndex> queryLatestIndex(Integer userId, long initTime, int preInterval, int[] intervals) {
		StringBuilder builder = new StringBuilder();
		Object[] params = new Object[intervals.length * 7];
		long startTime = initTime - preInterval;
		for(int i = 0; i < intervals.length; i++) {
			if(i == 0) 
				builder.append(QUERY_LATEST_INDEX_MAIN);
			else 
				builder.append(" union all ").append(QUERY_LATEST_INDEX_MAIN);
			long endTime = initTime - intervals[i];
			Date beginDate = new Date(startTime);
			Date endDate = new Date(endTime);
			params[i*7 + 0] = intervals[i];
			params[i*7 + 1] = userId;
			params[i*7 + 2] =  beginDate;
			params[i*7 + 3] =  endDate;
			params[i*7 + 4] = beginDate;
			params[i*7 + 5] = endDate;
			params[i*7 + 6] = userId;
			startTime = endTime;
		}
		return getJdbcTemplate().query(builder.toString(), params,
				new RowMapper<HTWorldLatestIndex>() {

					@Override
					public HTWorldLatestIndex mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						return new HTWorldLatestIndex(
								rs.getInt("ival"), 
								rs.getLong("total"));
					}
			
		});
	}

	@Override
	public List<HTWorldLatest> queryLatest(Integer maxId, Integer userId,
			long startTime, final long endTime, Integer limit) {
		Date startDate = new Date(startTime);
		Date endDate = new Date(endTime);
		return getJdbcTemplate().query(QUERY_LATEST, 
				new Object[]{maxId, userId, startDate, endDate, limit, 
				maxId, startDate, endDate, userId, limit, limit}, 
				new RowMapper<HTWorldLatest>() {

			@Override
			public HTWorldLatest mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return new HTWorldLatest(
						rs.getInt("id"),
						rs.getInt("author_id"),
						rs.getString("title_thumb_path"),
						0);
			}
			
		});
	}
	
	@Override
	public List<HTWorldLatest> queryLatest(Integer maxId, Integer userId,
			long startTime, Integer limit) {
		Date startDate = new Date(startTime);
		return getJdbcTemplate().query(QUERY_LATEST_BY_MAX_DATE, 
				new Object[]{maxId, userId, startDate, limit, 
				maxId, startDate, userId, limit, limit}, 
				new RowMapper<HTWorldLatest>() {

			@Override
			public HTWorldLatest mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return new HTWorldLatest(
						rs.getInt("id"),
						rs.getInt("author_id"),
						rs.getString("title_thumb_path"),
						0);
			}
			
		});
	}
	
	@Override
	public long queryLatestCount(Integer maxId, Integer userId,
			long startTime, long endTime) {
		Date startDate = new Date(startTime);
		Date endDate = new Date(endTime);
		return getJdbcTemplate().queryForLong(QUERY_LATEST_COUNT, 
				new Object[]{maxId, userId, startDate, endDate,
				maxId, startDate, endDate, userId});
	}
	
	@Override
	public long queryLatestCount(Integer maxId, Integer userId,
			long startTime) {
		Date startDate = new Date(startTime);
		return getJdbcTemplate().queryForLong(QUERY_LATEST_COUNT_BY_MAX_DATE, 
				new Object[]{maxId, userId, startDate, 
				maxId, startDate, userId});
	}
	
	
	@Override
	public List<HTWorldLatest> queryLatest2(Integer userId, long initTime, int preInterval, int[] intervals, int[] limits) {
		StringBuilder builder = new StringBuilder(QUERY_LATEST_HEAD_V2);
		Object[] params = new Object[intervals.length * 10];
		long startTime = initTime - preInterval;
		for(int i = 0; i < intervals.length; i++) {
			if(i == 0) 
				builder.append(QUERY_LATEST_MAIN_V2);
			else 
				builder.append(" union all ").append(QUERY_LATEST_MAIN_V2);
			long endTime = initTime - intervals[i];
			Date beginDate = new Date(startTime);
			Date endDate = new Date(endTime);
			params[i*10 + 0] = intervals[i];
			params[i*10 + 1] = userId;
			params[i*10 + 2] =  beginDate;
			params[i*10 + 3] =  endDate;
			params[i*10 + 4] = limits[i];
			params[i*10 + 5] = beginDate;
			params[i*10 + 6] = endDate;
			params[i*10 + 7] = userId;
			params[i*10 + 8] = limits[i];
			params[i*10 + 9] = limits[i];
			startTime = endTime; 
		}
		builder.append(QUERY_LATEST_FOOT_V2);
		return getJdbcTemplate().query(builder.toString(), params,
				new RowMapper<HTWorldLatest>() {

			@Override
			public HTWorldLatest mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return new HTWorldLatest(
						rs.getInt("latest_valid"),
						rs.getInt("id"),
						rs.getInt("author_id"),
						rs.getString("title_thumb_path"),
						rs.getInt("ival"));
			}
		});
	}

	@Override
	public List<HTWorldLatestIndex> queryLatestIndex2(Integer userId, long initTime, int preInterval, int[] intervals) {
		StringBuilder builder = new StringBuilder();
		Object[] params = new Object[intervals.length * 7];
		long startTime = initTime - preInterval;
		for(int i = 0; i < intervals.length; i++) {
			if(i == 0) 
				builder.append(QUERY_LATEST_INDEX_MAIN_V2);
			else 
				builder.append(" union all ").append(QUERY_LATEST_INDEX_MAIN_V2);
			long endTime = initTime - intervals[i];
			Date beginDate = new Date(startTime);
			Date endDate = new Date(endTime);
			params[i*7 + 0] = intervals[i];
			params[i*7 + 1] = userId;
			params[i*7 + 2] =  beginDate;
			params[i*7 + 3] =  endDate;
			params[i*7 + 4] = beginDate;
			params[i*7 + 5] = endDate;
			params[i*7 + 6] = userId;
			startTime = endTime;
		}
		return getJdbcTemplate().query(builder.toString(), params,
				new RowMapper<HTWorldLatestIndex>() {

					@Override
					public HTWorldLatestIndex mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						return new HTWorldLatestIndex(
								rs.getInt("ival"), 
								rs.getLong("total"));
					}
			
		});
	}

	@Override
	public List<HTWorldLatest> queryLatest2(Integer maxId, Integer userId,
			long startTime, final long endTime, Integer limit) {
		Date startDate = new Date(startTime);
		Date endDate = new Date(endTime);
		return getJdbcTemplate().query(QUERY_LATEST_V2, 
				new Object[]{maxId, userId, startDate, endDate, limit, 
				maxId, startDate, endDate, userId, limit, limit}, 
				new RowMapper<HTWorldLatest>() {

			@Override
			public HTWorldLatest mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return new HTWorldLatest(
						rs.getInt("latest_valid"),
						rs.getInt("id"),
						rs.getInt("author_id"),
						rs.getString("title_thumb_path"),
						0);
			}
			
		});
	}
	
	@Override
	public List<HTWorldLatest> queryLatest2(Integer maxId, Integer userId,
			long startTime, Integer limit) {
		Date startDate = new Date(startTime);
		return getJdbcTemplate().query(QUERY_LATEST_BY_MAX_DATE_V2, 
				new Object[]{maxId, userId, startDate, limit, 
				maxId, startDate, userId, limit, limit}, 
				new RowMapper<HTWorldLatest>() {

			@Override
			public HTWorldLatest mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return new HTWorldLatest(
						rs.getInt("latest_valid"),
						rs.getInt("id"),
						rs.getInt("author_id"),
						rs.getString("title_thumb_path"),
						0);
			}
			
		});
	}
	
	@Override
	public long queryLatestCount2(Integer maxId, Integer userId,
			long startTime, long endTime) {
		Date startDate = new Date(startTime);
		Date endDate = new Date(endTime);
		return getJdbcTemplate().queryForLong(QUERY_LATEST_COUNT_V2, 
				new Object[]{maxId, userId, startDate, endDate,
				maxId, startDate, endDate, userId});
	}
	
	@Override
	public long queryLatestCount2(Integer maxId, Integer userId,
			long startTime) {
		Date startDate = new Date(startTime);
		return getJdbcTemplate().queryForLong(QUERY_LATEST_COUNT_BY_MAX_DATE_V2, 
				new Object[]{maxId, userId, startDate, 
				maxId, startDate, userId});
	}

	@Override
	public void setWorldInfo(ResultSet rs, HTWorldBase world) throws SQLException {
		int id = rs.getInt("id");
		String shortLink = rs.getString("short_link");
		String url = shortLink == null ? urlPrefix + id : urlPrefix + shortLink;
		List<HTWorldChannelName> channelNames = formatChannelNames(
				rs.getString("channel_name"),
				rs.getString("channel_id"));
		world.setId(rs.getInt("id"));
		world.setShortLink(shortLink);
		world.setAuthorId(rs.getInt("author_id"));
		world.setWorldName(rs.getString("world_name"));
		world.setWorldDesc(rs.getString("world_desc"));
		world.setWorldLabel(rs.getString("world_label"));
		world.setWorldType(rs.getString("world_type"));
		world.setTypeId(rs.getInt("type_id"));
		world.setDateAdded((Date)rs.getObject("date_added"));
		world.setDateModified((Date)rs.getObject("date_modified"));
		world.setClickCount(rs.getInt("click_count"));
		world.setLikeCount(rs.getInt("like_count"));
		world.setCommentCount(rs.getInt("comment_count"));
		world.setKeepCount(rs.getInt("keep_count"));
		world.setCoverPath(rs.getString("cover_path"));
		world.setTitlePath(rs.getString("title_path"));
		world.setBgPath(rs.getString("bg_path"));
		world.setTitleThumbPath(rs.getString("title_thumb_path"));
		world.setChannelNames(channelNames);
		world.setLongitude(rs.getDouble("longitude"));
		world.setLatitude(rs.getDouble("latitude"));
		world.setLocationDesc(rs.getString("location_desc"));
		world.setLocationAddr(rs.getString("location_addr"));
		world.setPhoneCode(rs.getInt("phone_code"));
		world.setProvince(rs.getString("province"));
		world.setCity(rs.getString("province"));
		world.setSize(rs.getInt("size"));
		world.setChildCount(rs.getInt("child_count"));
		world.setVer(rs.getInt("ver"));
		world.setTp(rs.getInt("tp"));
		world.setValid(rs.getInt("valid"));
		world.setShield(rs.getInt("shield"));
		world.setTextStyle(JSONUtil.getJSObjectFromText(rs.getString("text_style")));
		world.setWorldURL(url);
	}
	
	@Override
	public Integer queryAuthorId(Integer worldId) {
		return getJdbcTemplate().queryForInt(QUERY_AUTHOR_ID, new Object[]{worldId});
	}

	@Override
	public List<HTWorldLatestId> queryLatestIdByMaxId(Integer maxId,
			Integer limit) {
		return getJdbcTemplate().query(QUERY_LATEST_ID_BY_MAX, 
				new Object[]{maxId, limit}, new RowMapper<HTWorldLatestId>(){

					@Override
					public HTWorldLatestId mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						return new HTWorldLatestId(
								rs.getInt("latest_valid"), 
								rs.getInt("id"));
					}
			
		});
	}

	@Override
	public List<HTWorldLatestId> queryLatestIdByMinId(Integer minId,
			Integer limit) {
		return getJdbcTemplate().query(QUERY_LATEST_ID_BY_MIN, 
				new Object[]{minId, limit}, new RowMapper<HTWorldLatestId>(){

					@Override
					public HTWorldLatestId mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						return new HTWorldLatestId(
								rs.getInt("latest_valid"), 
								rs.getInt("id"));
					}
			
		});
	}

	@Override
	public void queryCount(Integer[] ids, final RowCallback<HTWorldCount> callback) {
		String inSelection = SQLUtil.buildInSelection(ids);
		String sql = QUERY_COUNT + inSelection;
		getJdbcTemplate().query(sql, ids, new RowCallbackHandler() {

			@Override
			public void processRow(ResultSet rs) throws SQLException {
				callback.callback(new HTWorldCount(
						rs.getInt("id"),
						rs.getInt("like_count"),
						rs.getInt("click_count"),
						rs.getInt("comment_count")));
			}
			
		});
	}

	@Override
	public Integer queryChildCount(Integer authorId) {
		return getMasterJdbcTemplate().queryForInt(QUERY_CHILD_SUM, authorId);
	}

	@Override
	public Integer queryChildCountById(Integer id) {
		return getJdbcTemplate().queryForInt(QUERY_CHILD_COUNT_BY_ID, id);
	}
	
	public String[] parseChannelNames(List<HTWorldChannelName> names) {
		StringBuilder cnameBuilder = new StringBuilder();
		StringBuilder cidBuilder = new StringBuilder();
		for(int i = 0; i < names.size(); i++) {
			HTWorldChannelName cname = names.get(i);
			String name = cname.getName();
			int cid = cname.getId();
			if((cnameBuilder.length() + name.length()) <= 255 
					&& (cidBuilder.length() + String.valueOf(cid).length()) <= 255) {
				if(i != 0) {
					cnameBuilder.append(",");
					cidBuilder.append(",");
				}
				cnameBuilder.append(name);
				cidBuilder.append(cid);
			}
		}
		return new String[]{cnameBuilder.toString(), cidBuilder.toString()};
	}
	
	public List<HTWorldChannelName> formatChannelNames(String cnamesStr, String cidsStr) {
		if(!StringUtil.checkIsNULL(cnamesStr) 
				&& !StringUtil.checkIsNULL(cidsStr)) {
			List<HTWorldChannelName> list = new ArrayList<HTWorldChannelName>();
			String[] cnames = cnamesStr.split(",");
			String[] cids = cidsStr.split(",");
			for(int i = 0; i < cnames.length; i++) {
				list.add(new HTWorldChannelName(
						Integer.parseInt(cids[i]),
						cnames[i]));
			}
			return list;
		}
		return null;
		
	}

	@Override
	public Integer queryValid(Integer id) {
		try {
			return getJdbcTemplate().queryForInt(QUERY_VALID, id);
		} catch(EmptyResultDataAccessException e) {
			return 0;
		}
	}
	
	@Override
	public List<HTWorldDto> queryLastNHtworldInfoByUserId(Integer userId,Integer n){
		try {
			return getJdbcTemplate().query(QUERY_LAST_N_WORLD_INFO_BY_USER_ID, new Object[]{userId,n}, new RowMapper<HTWorldDto>(){
				@Override
				public HTWorldDto mapRow(ResultSet rs, int rowNum)throws SQLException{
					HTWorldDto dto = new HTWorldDto();
//					dto.setId(rs.getInt("id"));
					dto.setTitlePath(rs.getString("title_path"));;
//					dto.setTitleThumbPath(rs.getString("title_thumb_path"));
					return dto;
				}
			});
		} catch(EmptyResultDataAccessException e){
			return null;
		}
	}
	
}
