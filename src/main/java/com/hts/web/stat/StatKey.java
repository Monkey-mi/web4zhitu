package com.hts.web.stat;

/**
 * 统计类型
 * 
 * @author lynch
 *
 */
public class StatKey {

	@StatKeyRemark(name="附近织图")
	public static final Integer NRAR_WORLD = 10000;
	
	@StatKeyRemark(name="附近织图下一页")
	public static final Integer NEAR_WORLD_NEXT = 10001;
	
	@StatKeyRemark(name="商品集合")
	public static final Integer ITEM_SET = 10002;

	@StatKeyRemark(name="商品集合下一页")
	public static final Integer ITEM_SET_NEXT = 10003;
	
	@StatKeyRemark(name="附近推荐城市")
	public static final Integer NEAR_CITY_REC = 10004;

	@StatKeyRemark(name="附近标签织图")
	public static final Integer NEAR_LABEL_WORLD = 10005;
	
	@StatKeyRemark(name="附近标签织图下一页")
	public static final Integer NEAR_LABEL_WORLD_NEXT = 10006;

	@StatKeyRemark(name="关注信息流")
	public static final Integer WORLD_CONCERN = 10007;
	
	@StatKeyRemark(name="发图")
	public static final Integer WORLD_SHARE = 10008;

	@StatKeyRemark(name="评论")
	public static final Integer WORLD_COMMENT = 10009;

	@StatKeyRemark(name="回复")
	public static final Integer WORLD_REPLY = 10010;

	@StatKeyRemark(name="点赞")
	public static final Integer WORLD_LIKE = 10011;

	@StatKeyRemark(name="关注")
	public static final Integer USER_CONCERN = 10012;
	
	@StatKeyRemark(name="评论at")
	public static final Integer USER_MSG_AT_COMMENT = 10013;
	
	@StatKeyRemark(name="发图at")
	public static final Integer USER_MSG_AT_WORLD = 10014;

	@StatKeyRemark(name="频道首页")
	public static final Integer CHANNEL_INDEX = 10015;

	@StatKeyRemark(name="频道首页下一页")
	public static final Integer CHANNEL_INDEX_NEXT = 10016;

	@StatKeyRemark(name="频道织图")
	public static final Integer CHANNEL_WORLD = 10017;

	@StatKeyRemark(name="频道织图下一页")
	public static final Integer CHANNEL_WORLD_NEXT = 10018;

	@StatKeyRemark(name="频道精选织图")
	public static final Integer CHANNEL_WORLD_SUPERB = 10019;

	@StatKeyRemark(name="频道精选织图下一页")
	public static final Integer CHANNEL_WORLD_SUPERB_NEXT = 10020;

	@StatKeyRemark(name="发图进频道")
	public static final Integer CHANNEL_SHARE_WORLD = 10021;
	
	@StatKeyRemark(name="活动专题首页")
	public static final Integer OP_ACT_THEME_INDEX = 10022;
	
	@StatKeyRemark(name="活动专题首页下一页")
	public static final Integer OP_ACT_THEME_INDEX_NEXT =10023;
	
	@StatKeyRemark(name="内容专题首页")
	public static final Integer OP_CONTENT_THEME_INDEX = 10024;
	
	@StatKeyRemark(name="内容专题首页下一页")
	public static final Integer OP_CONTENT_THEME_INDEX_NEXT = 10025;
	
	@StatKeyRemark(name="精选")
	public static final Integer OP_WORLD_SUPERB = 10026;
	
	@StatKeyRemark(name="精选下一页")
	public static final Integer OP_WORLD_SUPERB_NEXT = 10027;
	
	@StatKeyRemark(name="达人")
	public static final Integer OP_USER_REC = 10028;
	
	@StatKeyRemark(name="达人下一页")
	public static final Integer OP_USER_REC_NEXT = 10029;
	
	@StatKeyRemark(name="系统消息")
	public static final Integer OP_MSG_SYS = 10030;
	
	@StatKeyRemark(name="关注信息流下一页")
	public static final Integer WORLD_CONCERN_NEXT = 10031;
	
	@StatKeyRemark(name="订阅频道")
	public static final Integer CHANNEL_SUBSCRIBE = 10032;
	
	@StatKeyRemark(name="用户主页网页")
	public static final Integer USER_HOME_PAGE = 10033;
	
	@StatKeyRemark(name="我关注的频道")
	public static final Integer CHANNEL_MY_SUBSCRIBE = 10035;
	
	@StatKeyRemark(name="我关注的频道下一页")
	public static final Integer CHANNEL_MY_SUBSCRIBE_NEXT = 10036;
	
	@StatKeyRemark(name="活动织图")
	public static final Integer ACT_WORLD = 10037;
	
	@StatKeyRemark(name="活动织图下一页")
	public static final Integer ACT_WORLD_NEXT = 10038;
	
}
