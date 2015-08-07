package com.hts.web.base.constant;

public class Tag {
	
	/**
	 * IOS标识
	 */
	public static final int IOS = 0;
	
	/**
	 * ANDROID标识
	 */
	public static final int ANDROID = 1;
	
	/**
	 * 在线标识
	 */
	public static final int ONLINE = 1;
	
	/**
	 * 离线标识
	 */
	public static final int OFFLINE = 0;
	
	/**
	 * 存在标识
	 */
	public static final int EXIST = 1;
	
	/**
	 * 未关注标记
	 */
	public static final int UN_CONCERN = -1;
	
	/**
	 * 不存在标识
	 */
	public static final int NOT_EXIST = 0;
	
	public static final int TRUE = 1;
	
	public static final int FALSE = 0;
	
	public static final int REJECT = 2;
	
	/**
	 * 性别：未知
	 */
	public static final int SEX_UNKNOWN = 0;
	
	/**
	 * 性别：男性
	 */
	public static final int SEX_MAN = 1;
	
	/**
	 * 性别：女性
	 */
	public static final int SEX_FEMALE = 2;
	
	/**
	 * 基本缩略图类型：圆
	 */
	public static final int BASE_THUMB_CIRCLE = 1;
	
	/**
	 * 基本缩略图类型：爱心
	 */
	public static final int BASE_THUMB_TYPE_AIXIN = 2;
	
	/**
	 * 基本缩略图类型：星
	 */
	public static final int BASE_THUMB_TYPE_XING = 3;
	
	 /**
	  * 基本缩略图类型：花边
	  */
	public static final int BASE_THUMB_TYPE_HUABIAN = 4;
	
	/**
	 * 基本缩略图类型：花
	 */
	public static final int BASE_THUMB_TYPE_HUA = 5;
	
	/**
	 * 无效推送标记
	 */
	public static final String INVALID_PUSH_TOKEN = "0";
	
	
	/**
	 * 系统推送
	 */
	public static final int PUSH_ACTION_SYS = 1;
	
	/**
	 * 评论推送
	 */
	public static final int PUSH_ACTION_COMMENT = 2;
	
	/**
	 * 回复推送
	 */
	public static final int PUSH_ACTION_REPLY = 3;
	
	/**
	 * 喜欢推送
	 */
	public static final int PUSH_ACTION_LIKED = 4;
	
	/**
	 * 收藏推送
	 */
	public static final int PUSH_ACTION_KEEP = 5;

	/**
	 * 关注推送
	 */
	public static final int PUSH_ACTION_CONCERN = 6;

	/**
	 * 系统信息推送
	 */
	public static final int PUSH_ACTION_SYS_MSG = 7;
	
	
	/**
	 * 小秘书私信推送
	 */
	public static final int PUSH_ACTION_XIAOMI_MSG = 8;
	
	/**
	 * 织图推送
	 */
	public static final int PUSH_ACTION_WORLD_MSG = 9;

	/**
	 * 活动推送
	 */
	public static final int PUSH_ACTION_ACTIVITY_MSG = 10;
	
	/**
	 * 微博好友加入消息推送
	 */
	public static final int PUSH_ACTION_WEIBO_FRIEND = 11;
	
	/**
	 * 用户私信推送
	 */
	public static final int PUSH_ACTION_USER_MSG = 12;
	
	/**
	 * 屏蔽消息推送
	 */
	public static final int PUSH_ACTION_SHIELD_MSG = 13;
	
	/**
	 * 用户推荐推送
	 */
	public static final int PUSH_ACTION_USER_REC_MSG = 14;
	
	/**
	 * 旅行广场分类标签
	 */
	public static final int SQUARE_LABEL_TRAVEL = 1;
	
	/**
	 * 空间广场分类标签
	 */
	public static final int SQUARE_LABEL_ZONE = 2;
	
	/**
	 * 穿搭广场分类标签
	 */
	public static final int SQUARE_LABEL_DRESSING = 3;
	
	/**
	 * 美食广场分类标签
	 */
	public static final int SQUARE_LABEL_FOOD = 4;
	
	/**
	 * 其他广场分类标签
	 */
	public static final int SQUARE_LABEL_OTHERS = 5;
	
	/**
	 * 广场活动，普通类型
	 */
	public static final int SQUARE_ACTIVITY_NORMAL = 1;
	
	/**
	 * 广场活动，织图
	 */
	public static final int SQUARE_ACTIVITY_WORLD = 2;
	
	/**
	 * 普通私信类型
	 * （当初设计是用户私信和系统私信是用同一张表，此代号保留为用户对用户的私信，
	 * 后来用户私信被拆分，此代号已经被弃用）
	 */
	public static final int USER_MSG_NORMAL = 1;
	
	/**
	 * 广场通知私信类型
	 */
	public static final int USER_MSG_SQUARE_NOTIFY = 2;
	
	/**
	 * 用户推荐私信类型
	 */
	public static final int USER_MSG_USER_RECOMMEND = 3;
	
	/**
	 * 系统私信通知
	 */
	public static final int USER_MSG_SYS = 4;
	
	/**
	 * 广场活动私信类型
	 */
	public static final int USER_MSG_SQUARE_ACTIVITY_NOTIFY = 5;
	
	/**
	 * 广场规则提醒
	 */
	public static final int USER_MSG_SQUARE_RULE = 5;
	
	/**
	 * 频道Top one通知
	 */
	public static final int USER_MSG_CHANNEl_TOP_ONE = 6;
	
	/**
	 * 频道明星通知
	 */
	public static final int USER_MSG_CHANNEL_STAR = 7;
	
	/**
	 * 频道织图通知
	 */
	public static final int USER_MSG_CHANNEL_WORLD = 8;
	
	/**
	 * 用户推荐通知
	 */
	public static final int USER_MSG_STAR_RECOMMEND = 10;
	
	/**
	 * 本地标记
	 */
	public static final int STATE_LOCAL = 0;
	
	/**
	 * 远程标记
	 */
	public static final int STATE_REMOTE = 1;
	
	/**
	 * 织图标签类型：普通
	 */
	public static final int WORLD_LABEL_NORMAL = 0;
	
	/**
	 * 织图标签类型：活动
	 */
	public static final int WORLD_LABEL_ACTIVITY = 1;
	
	/**
	 * 无社交平台认证标记
	 * 
	 */
	public static final int VERIFY_NONE = 0;
	
	/**
	 * 新浪认证标记
	 */
	public static final int VERIFY_SINA = 20;
	
	
	/**
	 * 活跃类型：发图
	 */
	public static final int ACT_TYPE_WORLD = 1;
	
	/**
	 * 活跃类型；登录
	 */
	public static final int ACT_TYPE_LOGIN = 2;
	
	/**
	 * 活跃类型：点赞
	 */
	public static final int ACT_TYPE_LIKE = 3;
	
	/**
	 * 活跃类型：评论
	 */
	public static final int ACT_TYPE_COMMENT = 4;
	
	/**
	 * 活跃类型：轮换减分
	 */
	public static final int ACT_TYPE_CHANGE_SUB = 5;
	
	/**
	 * 织图类型:默认
	 */
	public static final int WORLD_TYPE_DEFAULT = 0;
	
	/**
	 * 织图类型:文字
	 */
	public static final int WORLD_TYPE_TEXT = 1;
	

	/**
	 * 频道成员
	 */
	public static final int CHANNEL_MEMBER_NORMAL = 0;
	
	/**
	 * 频道主人
	 */
	public static final int CHANNEL_MEMBER_OWNER = 1;
	
	/**
	 * 频道类型普通
	 */
	public static final int CHANNEL_TYPE_NORMAL = 0;

	/**
	 * 频道类型活动
	 */
	public static final int CHANNEL_TYPE_ACTIVITY = 1;

	/**
	 * 频道成员权限: normal
	 */
	public static final int CHANNEL_MEMBER_ROLE_NORMAL = 0;
	
	/**
	 * 频道成员权限: owner
	 */
	public static final int CHANNEL_MEMBER_ROLE_OWNER = 1;
	
	/**
	 * 频道成员权限: admin
	 */
	public static final int CHANNEL_MEMBER_ROLE_ADMIN = 2;
	
	/**
	 * 公告类型:默认
	 */
	public static final int BULLETIN_DEFAULT = 0;
	
	/**
	 * 公告类型:网页
	 */
	public static final int BULLETIN_PAGE = 1;
	
	/**
	 * 公告类型:频道
	 */
	public static final int BULLETIN_CHANNEL = 2;
	
	/**
	 * APP版本:2.0，版本号过溢出则使用次版本
	 */
	public static final float VERSION_2_0 = 2.0f;
	
	/**
	 * APP版本：2.8.2
	 */
	public static final float VERSION_2_8_2 = 2.0802f;
	
	/**
	 * APP版本：2.8.3
	 */
	public static final float VERSION_2_8_3 = 2.0803f;
	
	/**
	 * APP版本：2.9.5
	 */
	public static final float VERSION_2_9_5 = 2.0905f;
	
	/**
	 * APP版本：2.9.7
	 */
	public static final float VERSION_2_9_7 = 2.0907f;
	
	/**
	 * APP版本：2.9.71
	 */
	public static final float VERSION_2_9_71 = 2.0971f;
	
	
	/**
	 * APP版本：2.9.81
	 */
	public static final float VERSION_2_9_81 = 2.0981f;
	
	/**
	 * APP版本：2.9.82
	 */
	public static final float VERSION_2_9_82 = 2.0982f;
	
	/**
	 * APP版本：2.9.83
	 */
	public static final float VERSION_2_9_83 = 2.0983f;
	
	/**
	 * 2.9.84
	 */
	public static final float VERSION_2_9_84 = 2.0984f;
	
	/**
	 * 2.9.88
	 */
	public static final float VERSION_2_9_88 = 2.0988f;
	
	
	/**
	 * 喜欢关系,同城
	 */
	public static final int LIKE_ME_RELATE_CITY = 1;
	
	/**
	 * 喜欢关系,共同关注
	 */
	public static final int LIKE_ME_RELATE_COMMON_CONCERN = 2;
	
	/**
	 * 喜欢关系,好友关注
	 */
	public static final int LIKE_ME_RELATE_FRIEND_CONCERN = 3;
	
	/**
	 * 喜欢关系,同省
	 */
	public static final int LIKE_ME_RELATE_PROVINCE = 4;
	
	
}
