package com.hts.web.base.constant;

public class CacheKeies {

	/**
	 * 织图模块
	 */
	private static final String MODULE_ZTWORLD = "ztworld:";
	
	/**
	 * 织图：最新
	 */
	public static final String ZTWORLD_LATEST_WORLD = MODULE_ZTWORLD + "latestWorld";
	
	/**
	 * 织图：最新子世界
	 */
	public static final String ZTWORLD_CHILD_LATEST_TYPE = MODULE_ZTWORLD + "child:latestType";
	
	/**
	 * 标签：热门
	 */
	public static final String ZTWORLD_LABEL_HOT = MODULE_ZTWORLD + "label:hot";
	
	/**
	 * 标签：活动
	 */
	public static final String ZTWORLD_LABEL_ACTIVITY = MODULE_ZTWORLD + "label:activity";
	
	/**
	 * 滤镜：logo
	 */
	public static final String ZTWORLD_FILTER_LOGO = MODULE_ZTWORLD + "filter:logo";
	
	/**
	 * 置顶贴纸
	 */
	public static final String ZTWORLD_STICKER_TOP = MODULE_ZTWORLD + "sticker:top";
	
	/**
	 * 推荐贴纸
	 */
	public static final String ZTWORLD_STICKER_RECOMMEND = MODULE_ZTWORLD + "sticker:recommend";
	
	/**
	 * 热门贴纸
	 */
	public static final String ZTWORLD_STICKER_HOT = MODULE_ZTWORLD + "sticker:hot";
	
	/**
	 * 贴纸类型
	 */
	public static final String ZTWORLD_STICKER_TYPE = MODULE_ZTWORLD + "sticker:type";
	
	/**
	 * 推荐贴纸类型
	 */
	public static final String ZTWORLD_STICKER_RECOMMEND_TYPE = MODULE_ZTWORLD + "sticker:recommendType";
	
	/**
	 * 贴纸分类id
	 */
	public static final String ZTWORLD_STICKER_TYPE_ID = MODULE_ZTWORLD+ "sticker:typeId:";
	
	/**
	 * 推荐字幕
	 */
	public static final String ZTWORLD_SUBTITLE = MODULE_ZTWORLD + "subtitle";
	
	/**
	 * 织图分类
	 */
	public static final String ZTWORLD_TYPE = MODULE_ZTWORLD + "type";
	
	/**
	 * 评论广播
	 */
	public static final String ZTWORLD_COMMENT_BROADCAST = MODULE_ZTWORLD + "comment:broadcast";
	
	/**
	 * 织图评论周表中在redis里的最大id
	 */
	public static final String ZTWORLD_COMMENT_WEEK_MAX_ID = MODULE_ZTWORLD + "comment:weekmaxid";
	
	/**
	 * 运营模块
	 */
	private static final String MODULE_OP = "op:";
	
	/**
	 * 运营：活动：所有活动
	 */
	public static final String OP_ACTIVITY = MODULE_OP + "activity";
	
	/**
	 * 运营：活动：所有活动哈希
	 */
	public static final String OP_ACTIVITY_HASH = MODULE_OP +"activity:hash";
	
	/**
	 * 运营：活动：LOGO
	 */
	public static final String OP_ACTIVITY_LOGO = MODULE_OP + "activity:logo";
	
	/**
	 * 运营:活动:star
	 */
	public static final String OP_ACTIVITY_STAR = MODULE_OP + "activity:star";
	
	/**
	 * 运营：分类
	 */
	public static final String OP_TYPE = MODULE_OP + "type";
	
	/**
	 * 运营：公告
	 */
	public static final String OP_NOTICE = MODULE_OP + "notice";
	
	/**
	 * 运营:启动页
	 */
	public static final String OP_MSG_START_PAGE = MODULE_OP + "msg:startPage";
	
	/**
	 * 运营:公告
	 */
	public static final String OP_MSG_BULLETIN = MODULE_OP + "msg:bulletin";
	
	/**
	 * 运营:专题公告列表
	 */
	public static final String OP_MSG_THEME = MODULE_OP + "msg:theme";

	/**
	 * 运营:用户专题公告列表
	 */
	public static final String OP_MSG_USER_THEME = MODULE_OP + "msg:userTheme";
	
	/**
	 * 公用系统消息列表
	 */
	public static final String OP_MSG_COMMON_SYSMSG = MODULE_OP + "msg:commonSysMsg";
	
	/**
	 * 最大公用系统消息id
	 */
	public static final String OP_MSG_COMMON_SYSMSG_MAXID = MODULE_OP + "msg:commonSysMsg:maxId";
	
	/**
	 * 运营：精品分类
	 */
	public static final String OP_SUPERB_TYPE = MODULE_OP + "type:superb";
	
	/**
	 * 运营：置顶精品分类
	 */
	public static final String OP_SUPERB_TYPE_WEIGHT = MODULE_OP + "type:superb:weight";
	
	/**
	 * 运营：用户认证
	 */
	public static final String OP_USER_VERIFY = MODULE_OP + "user:verify";
	
	/**
	 * 认证推荐用户
	 */
	public static final String OP_USER_VERIFY_REC = MODULE_OP + "user:verify:rec:";
	
	/**
	 * 置顶认证推荐用户
	 */
	public static final String OP_USER_VERIFY_REC_TOP = MODULE_OP + "user:verify:rec:top";
	
	/**
	 * 频道
	 */
	public static final String OP_CHANNEL = MODULE_OP + "channel";
	
	/**
	 * 旧版频道
	 */
	public static final String OP_CHANNEL_OLD = MODULE_OP + "channel:old";
	
	/**
	 * 频道TopOne人物
	 */
	public static final String OP_CHANNEL_TOP_ONE = MODULE_OP + "channel:topone";
	
	/**
	 * 频道明星
	 */
	public static final String OP_CHANNEL_STAR = MODULE_OP + "channel:star";
	
	/**
	 * 频道TopOne人物标题
	 */
	public static final String OP_CHANNEL_TOP_ONE_TITLE = MODULE_OP + "channel:toponetitle";
	
	/**
	 * 频道封面
	 */
	public static final String OP_CHANNEL_COVER = MODULE_OP + "channel:cover";

	/**
	 * 频道主题
	 */
	public static final String OP_CHANNEL_THEME = MODULE_OP + "channel:theme";
	
	/**
	 * 频道pv
	 */
	public static final String OP_CHANNEL_PV = MODULE_OP + "channel:pv";
	
	/**
	 * 频道发图自动通过频道id set
	 */
	public static final String OP_CHANNEL_AUTO_REJECT_ID = MODULE_OP + "channel:autoRejectId";
	
	/**
	 * 用户模块
	 */
	private static final String MODULE_USER = "ztworld:";
	
	/**
	 * 用户权限
	 */
	public static final String USER_VERIFY = MODULE_USER + "verify";
	
	/**
	 * 开放搜索模块
	 */
	private static final String MODULE_OS = "os:";
	
	/**
	 * 开放搜索用户信息
	 */
	public static final String OS_USER_INFO = MODULE_OS + "userInfo";
	
	/**
	 * 开放搜索用户登录信息
	 */
	public static final String OS_USER_LOGIN = MODULE_OS + "userLogin";
	
	/**
	 * OpenSearch world
	 */
	public static final String OS_WORLD = MODULE_OS + "world";
	
	
	/**
	 * 第三方社交模块
	 * 
	 */
	private static final String MODULE_PLAT = "plat:";
	
	/**
	 * 第三方社交关注
	 */
	public static final String PLAT_CONCERN = MODULE_PLAT + "conceron";
	
	/**
	 * 第三方社交被关注
	 */
	public static final String PLAT_BE_CONCERN = MODULE_PLAT + "beConceron";
	
	/**
	 * 达人推荐
	 */
	public static final String OP_STAR_RECOMMEND = "starRecommend";

	
	/**
	 * 数据统计模块
	 */
	private static final String MODULE_STAT = "stat:";
	
	/**
	 * 用户注册数据统计
	 */
	public static final String STAT_USER_REGISTER = MODULE_STAT + "userRegister";
	
	
}
