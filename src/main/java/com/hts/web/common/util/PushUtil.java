package com.hts.web.common.util;

public class PushUtil {

	public static final int SHORT_NAME_LEN = 5;
	
	public static final int SHORT_MSG_LEN = 12;
	
	public static final int LONG_MSG_LEN = 20;
	
	/**
	 * 获取名字缩略
	 * @param name
	 * @return
	 */
	public static String getShortName(String name) {
		return StringUtil.getShortCut(name, SHORT_NAME_LEN);
	}
	
	/**
	 * 获取消息缩略
	 * @param msg
	 * @return
	 */
	public static String getShortComment(String msg) {
		int i = msg.indexOf(":");
		return ":"+StringUtil.getShortCut(msg.substring(i+1).trim(),
				SHORT_MSG_LEN);
	}
	
	/**
	 * 获取回复缩略
	 * 
	 * @param reply
	 * @return
	 */
	public static String getShortReply(String reply) {
		int i = reply.indexOf(":");
		return ":" + StringUtil.getShortCut(reply.substring(i+1).trim(),
				SHORT_MSG_LEN);
	}
	
	/**
	 * 获取缩略提示
	 * 
	 * @param msg
	 * @return
	 */
	public static String getShortTip(String msg) {
		return StringUtil.getShortCut(msg.trim(), SHORT_MSG_LEN);
	}
	
	public static String getLongTip(String msg) {
		return StringUtil.getShortCut(msg.trim(), LONG_MSG_LEN);
	}
}
