package com.hts.web.base.constant;

/**
 * <p>
 * 操作结果及代号静态资源类
 * </p>
 * 
 * 创建时间：2012-11-01
 * @author ztj
 *
 */
public class OptResult {
	
	public static final String JSON_KEY_USER_INFO = "userInfo";
	public static final String JSON_KEY_SOCIAL_ACCOUNT = "socialAccount";
	public static final String KEY_IS_EXISTS = "isExists";

	/**
	 * 操作成功代号"0"
	 */
	public static final int OPT_SUCCESS = 0;
	/**
	 * 操作失败代号"-1"
	 */
	public static final int OPT_FAILED = -1;
	/**
	 * "result"
	 */
	public static final String RESULT = "result";
	/**
	 * "msg"
	 */
	public static final String MSG = "msg";
	
	/**
	 * ERR_CODE
	 */
	public static final String ERR_CODE = "errCode";
	/**
	 * "total"
	 */
	public static final String TOTAL = "total";
	/**
	 * "rows"
	 */
	public static final String ROWS = "rows";
	
	
	/**
	 * 添加成功
	 */
	public static final String ADD_SUCCESS = "添加成功";
	/**
	 * 已添加过
	 */
	public static final String HAVE_ADDED = "已添加过";
	/**
	 * 暂无记录
	 */
	public static final String HAVE_NULL = "暂无记录";
	/**
	 * 添加失败
	 */
	public static final String ADD_FAILED = "添加失败";
	
	/**
	 * 删除成功
	 */
	public static final String DELETE_SUCCESS = "删除成功";
	
	/**
	 * 删除失败
	 */
	public static final String DELETE_FAILED = "删除失败";
	
	/**
	 * 更新成功
	 */
	public static final String UPDATE_SUCCESS = "更新成功";
	
	/**
	 * 更新失败
	 */
	public static final String UPDATE_FAILED = "更新失败";
	
	/**
	 * 查询成功
	 */
	public static final String QUERY_SUCCESS = "查询成功";
	
	/**
	 * 查询失败
	 */
	public static final String QUERY_FAILED = "查询失败";
	
	/**
	 * 检测的信息存在代号--"0"
	 */
	public static final Integer CHECK_EXIST = 0;
	
	/**
	 * 检测的信息不存在代号--"-1"
	 */
	public static final Integer CHECK_NOT_EXIST = -1;
	
	/**
	 * 登录成功
	 */
	public static final String LOGIN_SUCCESS = "登录成功";
	/**
	 * 密码错误
	 */
	public static final String ERROR_PASS = "密码错误";
	
	/**
	 * 账号错误
	 */
	public static final String ERROR_LOGIN_CODE = "账号错误"; 
	
	/**
	 * 返回结果KEY：topics
	 */
	public static final String JSON_KEY_TOPICS = "topics";
	
	/**
	 * 返回结果KEY：htworld
	 */
	public static final String JSON_KEY_HTWORLD = "htworld";
	
	/**
	 * 返回结果KEY：childs
	 */
	public static final String JSON_KEY_CHILDS = "childs";
	
	/**
	 * 返回结果KEY：totalCount
	 */
	public static final String JSON_KEY_TOTAL_COUNT = "totalCount";
	
	/**
	 * previousId
	 */
	public static final String JSON_KEY_PREVIOUS_ID = "previousId";
	
	/**
	 * 返回结果KEY: nextId
	 */
	public static final String JSON_KEY_NEXT_ID = "nextId";
	
	/**
	 * 返回结果KEY：concerns
	 */
	public static final String JSON_KEY_CONCERNS = "concerns";
	
	/**
	 * 返回结果KEY：comments
	 */
	public static final String JSON_KEY_COMMENTS = "comments";
	
	/**
	 * 返回结果KEY：comment
	 */
	public static final String JSON_KEY_COMMENT = "comment";
	
	/**
	 * 返回结果KEY：follows
	 */
	public static final String JSON_KEY_FOLLOWS = "follows";
	
	/**
	 * 返回结果KEY：maxId
	 */
	public static final String JSON_KEY_MAX_ID = "maxId";
	
	/**
	 * 返回结果KEY：maxSerial
	 */
	public static final String JSON_KEY_MAX_SERIAL = "maxSerial";
	
	/**
	 * 返回结果KEY：sinceId
	 */
	public static final String JSON_KEY_SINCE_ID = "sinceId";
	
	/**
	 * 返回结果KEY：title
	 */
	public static final String JSON_KEY_TITLE = "title";
	
	/**
	 * 返回结果KEY：page
	 */
	public static final String JSON_KEY_PAGE = "page";
	
	/**
	 * 返回结果KEY：客户端代号
	 */
	public static final String JSON_KEY_PHONE = "phone";
	
	/**
	 * 返回结果KEY：ROWS
	 */
	public static final String JSON_KEY_ROWS = "rows";
	
	/**
	 * 返回结果KEY：pageCount
	 */
	public static final String JSON_KEY_PAGE_COUNT = "pageCount";
	
	/**
	 * 返回结果KEY：total
	 */
	public static final String JSON_KEY_TOTAL = "total";
	
	/**
	 * 返回结果KEY：isMututal
	 */
	public static final String JSON_KEY_IS_MUTUTAL = "isMututal";
	
	/**
	 * 返回结果KEY：likeds
	 */
	public static final String JSON_KEY_LIKEDS = "likeds";
	
	/**
	 * 返回结果KEY：commentCount
	 */
	public static final String JSON_KEY_COMMENT_COUNT = "commentCount";
	
	/**
	 * 返回结果KEY：likedCount
	 */
	public static final String JSON_KEY_LIKED_COUNT = "likedCount";
	
	/**
	 * 返回结果KEY：followCount
	 */
	public static final String JSON_KEY_FOLLOW_COUNT = "followCount";
	
	/**
	 * 返回结果KEY：msgCount
	 */
	public static final String JSON_KEY_MSG_COUNT = "msgCount";
	
	/**
	 * 返回结果KEY：userMsgCount
	 */
	public static final String JSON_KEY_USER_MSG_COUNT = "userMsgCount";
	
	/**
	 * atMsgCount
	 */
	public static final String JSON_KEY_AT_MSG_COUNT = "atMsgCount";
	
	/**
	 * 返回结果KEY：successCount
	 */
	public static final String JSON_KEY_SUCCESS_COUNT = "successCount";
	
	/**
	 * 返回结果KEY：failedCount
	 */
	public static final String JSON_KEY_FAILED_COUNT = "failedCount";
	
	/**
	 * 返回结果KEY：labelCount
	 */
	public static final String JSON_KEY_LABEL_COUNT = "labelCount";
	
	/**
	 * 返回结果KEY：superbCount
	 */
	public static final String JSON_KEY_SUPERB_COUNT = "superbCount";
	
	/**
	 * 返回结果KEY：labelInfo
	 */
	public static final String JSON_KEY_LABEL_INFO = "labelInfo";
	
	/**
	 * 返回结果KEY：activity
	 */
	public static final String JSON_KEY_ACTIVITY = "activity";

	/**
	 * 返回结果KEY：logo
	 */
	public static final String JSON_KEY_LOGO = "logo";
	
	/**
	 * 返回结果KEY：successIds
	 */
	public static final String JSON_KEY_SUCCESS_IDS = "successIds";
	
	/**
	 * 返回结果KEY：failedIds
	 */
	public static final String JSON_KEY_FAILED_IDS = "failedIds";
	
	/**
	 * 返回结果KEY：serial
	 */
	public static final String JSON_KEY_SERIAL = "serial";
	
	/**
	 * 返回结果KEY：msg
	 */
	public static final String JSON_KEY_MSG = "msg";
	
	/**
	 * 返回结果KEY：userRecMsg
	 */
	public static final String JSON_KEY_USER_REC_MSG = "userRecMsg";
	
	/**
	 * userRecMsg
	 */
	public static final String JSON_KEY_USER_REC = "userRec";
	
	
	/**
	 * 返回结果KEY:award
	 */
	public static final String JSON_KEY_AWARD = "award";
	
	/**
	 * 返回结果KEY：valid
	 */
	public static final String JSON_KEY_VALID = "valid";
	
	/**
	 * 返回结果KEY：strangerMsgCount
	 */
	public static final String JSON_KEY_STRANGER_MSG_TOTAL = "strangerMsgCount";
	
	/**
	 * 返回结果KEY：APP
	 */
	public static final String JSON_KEY_APP = "app";
	
	/**
	 * 返回结果KEY：msgId
	 */
	public static final String JSON_KEY_MSG_ID = "msgId";
	
	/**
	 * 返回结果KEY：interact
	 */
	public static final String JSON_KEY_INTERACT = "interact";
	
	/**
	 * interactRes
	 */
	public static final String JSON_KEY_INTERACT_RES = "interactRes";
	
	/**
	 * 返回结果KEY：token
	 */
	public static final String JSON_KEY_TOKEN = "token";
	
	/**
	 * 返回结果KEY：topic
	 */
	public static final String JSON_KEY_TOPIC = "topic";
	
	/**
	 * 返回结果KEY：state
	 */
	public static final String JSON_KEY_STATE = "state";
	
	/**
	 * 返回结果KEY：status
	 */
	public static final String JSON_KEY_STATUS = "status";
	
	/**
	 * 返回结果KEY：verifyName
	 */
	public static final String JSON_KEY_VERIFY_NAME = "verifyName";
	
	/**
	 * 返回结果KEY：verifyIcon
	 */
	public static final String JSON_KEY_VERIFY_ICON = "verifyIcon";
	
	/**
	 * 返回结果KEY:USER_LEVEL
	 */
	public static final String JSON_KEY_USER_LEVEL = "user_level";
	
	/**
	 * 返回结果KEY:WORLD_LEVEL
	 */
	public static final String JSON_KEY_WORLD_LEVEL = "world_level";
	
	/**
	 * 返回结果KEY：nextStart
	 */
	public static final String JSON_KEY_NEXT_START = "nextStart";
	
	/**
	 * 返回结果KEY：hasWinner
	 */
	public static final String JSON_KEY_HAS_WINNER = "hasWinner";
	
	/**
	 * 返回结果KEY：types
	 */
	public static final String JSON_KEY_TYPES = "types";
	
	/**
	 * 返回结果KEY：type
	 */
	public static final String JSON_KEY_TYPE = "type";
	
	/**
	 * recommendType
	 */
	public static final String JSON_KEY_RECOMMEND_TYPE = "recommendType";
	
	/**
	 * 返回结果KEY：index
	 */
	public static final String JSON_KEY_INDEX = "index";
	
	/**
	 * 返回结果KEY：verify
	 */
	public static final String JSON_KEY_VERIFY = "verify";
	
	/**
	 * 返回结果KEY：accept
	 */
	public static final String JSON_KEY_ACCEPT = "accept";
	
	/**
	 * 返回结果KEY：shield
	 */
	public static final String JSON_KEY_SHIELD = "shield";
	
	/**
	 * 返回结果KEY：likeMeCount
	 */
	public static final String JSON_KEY_LIKE_ME_COUNT = "likeMeCount";
	
	/**
	 * 返回结果KEY：likeOtherCount
	 */
	public static final String JSON_KEY_LIKE_OTHER_COUNT = "likeOtherCount";
	
	/**
	 * 返回结果KEY：startTime
	 */
	public static final String JSON_KEY_START_TIME = "startTime";
	
	/**
	 * 返回结果KEY：intervalType
	 */
	public static final String JSON_KEY_INTERVAL_TYPE = "intervalType";
	
	/**
	 * 返回结果KEY：nextCursor
	 */
	public static final String JSON_KEY_NEXT_CURSOR = "nextCursor";

	/**
	 * userId
	 */
	public static final String JSON_KEY_USER_ID = "userId";
	
	/**
	 * channels
	 */
	public static final String JSON_KEY_CHANNELS = "channels";
	
	/**
	 * themes
	 */
	public static final String JSON_KEY_THEMES = "themes";
	
	/**
	 * channel
	 */
	public static final String JSON_KEY_CHANNEL = "channel";
	
	/**
	 * stars
	 */
	public static final String JSON_KEY_STARS = "stars";
	
	/**
	 * obj
	 */
	public static final String JSON_KEY_OBJ = "obj";
	
	/**
	 * maxActivityId
	 */
	public static final String JSON_KEY_MAX_ACTIVITY_ID = "maxActivityId";
	
	/**
	 * maxTopOneId
	 */
	public static final String JSON_KEY_MAX_TOP_ONE_ID = "maxTopOneId";
	
	/**
	 * weight
	 */
	public static final String JSON_KEY_WEIGHT = "weight";
	
	/**
	 * recommendWeight
	 */
	public static final String JSON_KEY_RECOMMEND_WEIGHT = "recommendWeight";
	
	/**
	 *　sticker
	 */
	public static final String JSON_KEY_STICKER = "sticker";
	
	/**
	 * unBindTip
	 */
	public static final String JSON_KEY_UN_BIND_TIP = "unBindTip";
	
	/**
	 * remark
	 */
	public static final String JSON_KEY_REMARK_ME = "remarkMe";
	
	/**
	 * interval
	 */
	public static final String JSON_KEY_INTERVAL = "interval";
	
	/**
	 * recType
	 */
	public static final String JSON_KEY_REC_TYPE = "recType";
	
	/**
	 * rec
	 */
	public static final String JSON_KEY_REC = "rec";
	
	/**
	 * recIndex
	 */
	public static final String JSON_KEY_REC_INDEX = "recIndex";
	
	/**
	 * recMsg
	 */
	public static final String JSON_KEY_REC_MSG = "recMsg";
	
	/**
	 * atPushStatus
	 */
	public static final String JSON_KEY_AT_PUSH_STATUS = "atPushStatus";
	
}


