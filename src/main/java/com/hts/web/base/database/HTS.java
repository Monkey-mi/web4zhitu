package com.hts.web.base.database;

/**
 * <p>
 * 数据库hts
 * </p>
 * 
 * @author ztj
 *
 */
public class HTS {
	
	private static final String DB = "hts.";
	
	/**
	 * 主键生成表
	 */
	public static final String KEYGEN = DB + "keygen";
	
	/*
	 * 用户信息子模块
	 */
	
	/**
	 * 用户信息表
	 */
	public static final String USER_INFO = DB + "user_info";
	
	/**
	 * 用户关注表
	 */
	public static final String USER_CONCERN = DB + "user_concern";
	
	/**
	 * 用户社交账号绑定表
	 */
	public static final String USER_SOCIAL_ACCOUNT = DB + "user_social_account";
	
	/**
	 * 用户持久登录记录表
	 */
	public static final String USER_LOGIN_PERSISTENT = DB + "user_login_persistent";
	
	/**
	 * 用户活跃度记录表
	 */
	public static final String USER_ACTIVITY = DB + "user_activity";
	
	/**
	 * 用户认证类型表
	 * 
	 */
	public static final String USER_VERIFY = DB + "user_verify";
	
	/**
	 * 用户反馈表
	 */
	public static final String USER_FEEDBACK = DB + "user_feedback";
	
	/**
	 * 用户私信表
	 */
	public static final String USER_MSG = DB + "user_msg";

	/**
	 * 用户私信发件箱表
	 */
	public static final String USER_MSG_SEND_BOX = DB + "user_msg_send_box";
	
	/**
	 * 用户私信收件箱表
	 */
	public static final String USER_MSG_RECIPIENT_BOX = DB + "user_msg_recipient_box";
	
	/**
	 * 用户私信屏蔽表
	 */
	public static final String USER_MSG_SHIELD = DB + "user_msg_shield";
	
	/**
	 * 用户屏蔽表
	 */
	public static final String USER_SHIELD = DB + "user_shield";
	
	/**
	 * 用户标签表
	 */
	public static final String USER_LABEL = DB + "user_label";
	
	/**
	 * 用户标签关联表
	 */
	public static final String USER_INFO_LABEL = DB + "user_info_label";
	
	/**
	 * 用户关注织图表
	 */
	public static final String USER_CONCERN_TYPE = DB + "user_concern_type";
	
	/**
	 * 用户举报表
	 */
	public static final String USER_REPORT = DB + "user_report";
	
	/**
	 * 用户备注表
	 */
	public static final String USER_REMARK = DB + "user_remark";
	
	/**
	 * 社交平台关注的用户表
	 */
	public static final String USER_PLAT_CONCERN = DB + "user_plat_concern";

	/**
	 * 用户推荐表
	 */
	public static final String USER_REC = DB + "user_rec";
	
	/**
	 * 织图管理员表
	 */
	public static final String USER_ADMIN = DB + "user_admin";
	
	/**
	 * 织图世界表
	 */
	public static final String HTWORLD_HTWORLD = DB + "htworld_htworld";

	/**
	 * 织图喜欢表
	 */
	public static final String HTWORLD_LIKED = DB + "htworld_liked";
	
	/**
	 * 织图收藏表
	 */
	public static final String HTWORLD_KEEP = DB + "htworld_keep";
	
	/**
	 * 织图举报表
	 */
	public static final String HTWORLD_REPORT = DB + "htworld_report";
	
	/**
	 * 子世界表
	 */
	public static final String HTWORLD_CHILD_WORLD = DB + "htworld_child_world";
	
	/**
	 * 子世界类型表
	 */
	public static final String HTWORLD_CHILD_WORLD_TYPE = DB + "htworld_child_world_type";
	
	/**
	 * 织图评论表
	 */
	public static final String HTWORLD_COMMENT = DB + "htworld_comment";
	
	/**
	 * 织图评论举报表
	 */
	public static final String HTWORLD_COMMENT_REPORT = DB + "htworld_comment_report";
	
	/**
	 * 织图分类表
	 */
	public static final String HTWORLD_TYPE = DB + "htworld_type";
	
	/**
	 * 织图分类关联表
	 */
	public static final String HTWORLD_TYPE_WORLD = DB + "htworld_type_world";
	
	/**
	 * 织图标签表
	 */
	public static final String HTWORLD_LABEL = DB + "htworld_label";
	
	/**
	 * 织图标签关联表
	 */
	public static final String HTWORLD_LABEL_WORLD = DB + "htworld_label_world";
	
	/**
	 * 织图贴纸
	 */
	public static final String HTWORLD_STICKER = DB + "htworld_sticker";
	
	/**
	 * 织图贴纸解锁表
	 */
	public static final String HTWORLD_STICKER_UNLOCK = DB + "htworld_sticker_unlock";
	
	/**
	 * 贴纸使用记录表
	 */
	public static final String HTWORLD_STICKER_USED = DB + "htworld_sticker_used";
	
	/**
	 * 字幕表
	 */
	public static final String HTWORLD_SUBTITLE = DB + "htworld_subtitle";
	
	
	/*
	 * 运营子模块
	 */
	
	/**
	 * 每周精选表
	 */
	public static final String OPERATIONS_WEEK_RECOMMEND = DB + "operations_week_recommend"; 
	
	
	/**
	 * 广场活动表
	 */
	public static final String OPERATIONS_SQUARE_PUSH_ACTIVITY = DB + "operations_square_push_activity";
	
	/**
	 * 广场活动织图表
	 */
	public static final String OPERATIONS_SQUARE_PUSH_ACTIVITY_WORLD = DB + "operations_square_push_activity_world";
	
	/**
	 * 活动发起人表
	 */
	public static final String OPERATIONS_ACTIVITY_SPONSOR = DB + "operations_activity_sponsor";
	
	/**
	 * 活动logo表
	 */
	public static final String OPERATIONS_ACTIVITY_LOGO = DB + "operations_activity_logo";
	
	/**
	 * 活动奖励表
	 */
	public static final String OPERATIONS_ACTIVITY_AWARD = DB + "operations_activity_award";
	
	/**
	 * 活动获奖织图表
	 */
	public static final String OPERATIONS_ACTIVITY_WINNER = DB + "operations_activity_winner";
	
	/**
	 * 活动点赞排行
	 */
	public static final String OPERATIONS_ACTIVITY_LIKE_RANK = DB + "operations_activity_like_rank";
	
	
	/**
	 * 广场推送话题表
	 */
	public static final String OPERATIONS_SQUARE_PUSH_TOPIC = DB + "operations_square_push_topic";
	
	/**
	 * 用户推荐表
	 */
	public static final String OPERATIONS_USER_RECOMMEND = DB + "operations_user_recommend";
	
	/**
	 * 僵尸用户表
	 */
	public static final String OPERATIONS_USER_ZOMBIE = DB + "operations_user_zombie";
	
	/**
	 * App链接表
	 */
	public static final String OPERATIONS_AD_APPLINK = DB + "operations_ad_applink";
	
	/**
	 * App链接点击记录表
	 */
	public static final String OPERATIONS_AD_APPLINK_RECORD = DB + "operations_ad_applink_record";
	
	/**
	 * 系统消息表
	 */
	public static final String OPERATIONS_SYS_MSG = DB + "operations_sys_msg";
	
	/**
	 * 频道表
	 */
	public static final String OPERATIONS_CHANNEL = DB + "operations_channel";
	
	/**
	 *　频道TopOne表
	 */
	public static final String OPERATIONS_CHANNEL_TOP_ONE = DB + "operations_channel_top_one";
	
	/**
	 * 频道红人表
	 */
	public static final String OPERATIONS_CHANNEL_STAR = DB + "operations_channel_star";
	
	/**
	 * 频道织图表
	 * 
	 */
	public static final String OPERATIONS_CHANNEL_WORLD = DB + "operations_channel_world";
	
	/**
	 * 频道成员表
	 * 
	 */
	public static final String OPERATIONS_CHANNEL_MEMBER = DB + "operations_channel_member";
	
	/**
	 * 频道系统弹幕表
	 */
	public static final String OPERATIONS_CHANNEL_SYS_DANNMU = DB + "operations_channel_sys_danmu";
	
	/**
	 * 频道系统弹幕已读记录表
	 */
	public static final String OPERATIONS_CHANNEL_DANMU_READ = DB + "operations_channel_danmu_read";
	
	/**
	 * 频道计数基数表
	 */
	public static final String OPERATIONS_CHANNEL_COUNT_BASE = DB + "operations_channel_count_base";
	
	/**
	 * 频道专题表
	 */
	public static final String QUERY_CHANNEL_THEME = DB + "operations_channel_theme";
	
	/**
	 * 找回密码表
	 */
	public static final String RETRIEVE_PASSWORD = DB + "user_retrieve_password";
	
	/**
	 * 明星用户推荐表
	 */
	public static final String OPERATIONS_USER_REC = DB + "operations_user_rec";
	
	/**
	 * 关联频道表
	 */
	public static final String OPERATIONS_CHANNEL_LINK = DB + "operations_channel_link";
	
}
