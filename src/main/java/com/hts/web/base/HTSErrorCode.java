package com.hts.web.base;

/**
 * 系统错误代号
 * 
 * @author lynch　2015-11-06
 *
 */
public enum HTSErrorCode {

	/**
	 * 系统内部错误
	 */
	SYS_ERR
	(0xffffffff, "系统内部异常"),
	
	/**
	 * 账号错误
	 */
	LOGIN_CODE_INCORECT
	(0x00000001, "incorect login code"),
	
	/**
	 * 密码错误
	 */
	PASSWORD_INCORECT
	(0x00000002, "incorect password"),
	
	/**
	 *　账号已存在
	 */
	LOGIN_CODE_EXISTS
	(0x00000003, "login code already exists"),
	
	/**
	 * 用户名已存在
	 */
	USER_NAME_EXISTS
	(0x00000004, "user name already exists"),
	
	/**
	 * 推送Token不能为空
	 */
	PUSH_TOKEN_NULL
	(0x00000005, "push token can not be null"),
	
	/**
	 * 用户头像不能为空
	 */
	AVATAR_NULL
	(0x00000006, "avatar can't be null"),
	
	/**
	 * 平台已经弃用
	 */
	PLATFORM_OFF
	(0x00000007, "platform deprecated"),
	
	/**
	 * 接触绑定失败
	 */
	UN_BIND_LOGIN_PLATFORM
	(0x00000008, "unbind platform error"),
	
	/**
	 * 用户名不存在
	 */
	USER_NAME_NOT_EXISTS
	(0x00000009, "user name not exists"),
	
	/**
	 * 无操作权限
	 */
	PERMISSION_DENY
	(0x0000000a, "permission deny"),
	
	/**
	 * 重复点赞
	 */
	REPEAT_LIKE
	(0x0000000b, "repeat like"),

	/**
	 * 重复收藏
	 */
	REPEAT_KEEP
	(0x0000000c, "repeat keep"),
	
	/**
	 * 无效织图
	 */
	INVALID_WORLD
	(0x0000000d, "invalid world"),
	
	/**
	 * 重复申请
	 */
	REPEAT_APPLY
	(0x0000000f, "repeat apply"),
	
	/**
	 * 重复举报
	 */
	REPEAT_REPORT
	(0x00000010, "repeat report"),
	
	/**
	 * 参数错误
	 */
	PARAMATER_ERR
	(0x00000011, "paramater error"),
	
	/**
	 * 无效标签
	 */
	INVALID_LABEL
	(0x00000012, "invalid label"),
	
	/**
	 * 请求失败
	 */
	REQUEST_FAIL
	(0x00000013, "request error"),
	
	/**
	 * 旧密码错误
	 */
	OLD_PASSWORD_INCORECT
	(0x00000014, "incorect old password"),
	
	/**
	 * 用户不存在
	 */
	USER_NOT_EXISTS
	(0x00000015, "user not exists"),
	
	/**
	 * 无效评论
	 */
	INVALID_COMMENT
	(0x00000016, "invalid comment"),
	
	/**
	 * 无效贴纸
	 */
	INVALID_STICKER
	(0x00000017, "invalid sticker"),
	
	/**
	 * 重复关注
	 */
	REPEAT_CONCERN
	(0x00000018, "repeat concern")
	
	;
	
	private int errCode;
	private String errMsg;
	
	private HTSErrorCode(int errCode, String errMsg) {
		this.errCode = errCode;
		this.errMsg = errMsg;
	}
	
	@Override
	public String toString() {
		return errMsg;
	}
	
	/**
	 * 获取错误代号
	 * 
	 * @return
	 */
	public int getErrCode() {
		return errCode;
	}

}
