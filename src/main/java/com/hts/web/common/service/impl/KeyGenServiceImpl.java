package com.hts.web.common.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hts.web.common.dao.KeyGenDao;
import com.hts.web.common.service.KeyGenService;

/**
 * <p>
 * 主键生成业务逻辑访问对象
 * </p>
 * 
 * 创建时间：2012
 * @author ztj
 *
 */
@Service("HTSKeyGenService")
public class KeyGenServiceImpl extends BaseServiceImpl implements KeyGenService{

	
	private static final String KEY_PREFIX = "keygen:";
	
	/**
	 * 用户信息表id
	 */
	public static final Integer USER_ID = 4;

	/**
	 * 用户关注表id
	 */
	public static final Integer USER_CONCERN_ID = 9;
	
	/**
	 * 私信对话索引
	 */
	public static final Integer USER_CHAT_ID = 11;
	
	/**
	 * 私信id
	 */
	public static final Integer USER_MSG_ID = 12;
	
	/**
	 * 用户社交绑定表id
	 */
	public static final Integer USER_SOCIAL_ACCOUNT_ID = 5;
	
	/**
	 * 织图世界id
	 */
	public static final Integer HTWORLD_HTWORLD_ID = 1;
	
	/**
	 * 织图世界子世界id
	 */
	public static final Integer HTWORLD_CHILD_WORLD_ID = 3;
	
	/**
	 * 织图世界评论id
	 */
	public static final Integer HTWORLD_COMMENT_ID = 2;
	
	/**
	 * 织图世界喜欢id
	 */
	public static final Integer HTWORLD_LIKED_ID = 6;
	
	/**
	 * 织图世界举报id
	 */
	public static final Integer HTWORLD_REPORT_ID = 8;
	
	/**
	 * 广场推送id
	 */
	public static final Integer HTWORLD_TYPE_WORLD_ID = 10;
	
	/**
	 * 广场活动表id
	 */
	public static final Integer OP_SQUARE_ACTIVITY_ID = 14;
	
	/**
	 * 广场活动织图id
	 */
	public static final Integer OP_ACTIVITY_WORLD_ID = 15;
	
	/**
	 * 推荐用户表id
	 */
	public static final Integer OP_USER_REC_ID = 13;
	
	/**
	 * APP链接id
	 */
	public static final Integer OP_AD_APP_ID = 16;
	
	/**
	 * 系统消息id
	 */
	public static final Integer OP_SYS_MSG_ID = 17;
	
	/**
	 * 用户标签id
	 */
	public static final Integer USER_LABEL_ID = 18;
	
	/**
	 * 织图标签id
	 */
	public static final Integer HTWORLD_LABEL_ID = 19;
	
	/**
	 * 织图分类id
	 */
	public static final Integer HTWORLD_TYPE_ID = 20;
	
	/**
	 * 活动LOGO序号
	 */
	public static final Integer OP_ACTIVITY_LOGO_SERIAL = 21;
	
	/**
	 * 用户消息频道id
	 */
	public static final Integer USER_MSG_TOPIC = 22;
	
	/**
	 * 活动奖品序号
	 */
	public static final Integer OP_ACTIVITY_AWARD_SERIAL = 23;
	
	/**
	 * 活动获奖织图权重
	 */
	public static final Integer OP_ACTIVITY_WINNER_WEIGHT = 24;
	
	/**
	 * 标签织图权重
	 */
	public static final Integer HTWORLD_LABEL_WORLD_WEIGHT = 25;
	
	/**
	 * 标签织图id
	 */
	public static final Integer HTWORLD_LABEL_WORLD_ID = 26;
	
	/**
	 * 活动获胜织图id
	 */
	public static final Integer OP_ACTIVITY_WINNER_ID = 27;
	
	/**
	 * 织图子世界类型id
	 */
	public static final Integer HTWORLD_CHILD_TYPE_ID = 28;
	
	/**
	 * 用户认证信息id
	 */
	public static final Integer USER_VERIFY_ID = 29;
	
	/**
	 * 公告id
	 */
	public static final Integer OP_NOTICE_ID = 30;
	
	/**
	 * 用户举报id
	 */
	public static final Integer USER_REPORT_ID = 31;
	
	/**
	 * 频道id
	 */
	public static final Integer OP_CHANNEL_ID = 32;
	
	/**
	 * 频道top one id
	 */
	public static final Integer OP_CHANNEL_TOP_ONE_ID = 33;
	
	/**
	 * 频道明星id
	 */
	public static final Integer OP_CHANNEL_STAR_ID = 34;
	
	/**
	 * 频道织图id
	 */
	public static final Integer OP_CHANNEL_WORLD_ID = 35;
	
	/**
	 * 贴纸类型id
	 */
	public static final Integer HTWORLD_STICKER_TYPE_ID = 36;
	
	/**
	 * 贴纸id
	 */
	public static final Integer HTWORLD_STICKER_ID = 37;
	
	/**
	 * 关联频道serial
	 */
	public static final Integer OP_CHANNEL_LINK_SERIAL = 38;
	
	/**
	 * 启动页id
	 */
	public static final Integer OP_MSG_START_PAGE_ID = 39;
	
	/**
	 * 贴纸系列序号
	 */
	public static final Integer HTWORLD_STICKER_SET_SERIAL = 40;
	
	/**
	 * 频道红人排序
	 */
	public static final Integer OP_CHANNEL_STAR_SERIAL = 41;
	
	/**
	 * 频道织图精选排序
	 */
	public static final Integer OP_CHANNEL_WORLD_SUPERB_SERIAL = 42;
	
	/**
	 * AT消息ID
	 */
	public static final Integer MSG_AT_ID = 43;
	
	
	/**
	 * 专属主题排序
	 */
	public static final Integer CHANNEL_THEME_SERIAL = 44;
	
	/**
	 * 附近标签id
	 */
	public static final Integer OP_NEAR_LABEL_ID = 45;
	
	/**
	 * 附近标签序号
	 */
	public static final Integer OP_NEAR_LABEL_SERIAL = 46;
	
	
	/**
	 * 织图标签排序
	 */
	public static final Integer LABEL_WORLD_SERIAL = 47;
	
	/**
	 * 附近织图序号
	 */
	public static final Integer OP_NEAR_WORLD_SERIAL = 48;
	
	@Autowired
	private KeyGenDao keyGenDao;
	
	@Override
	public Integer generateId(Integer keyId) {
		return keyGenDao.nextId(KEY_PREFIX + keyId);
	}

}
