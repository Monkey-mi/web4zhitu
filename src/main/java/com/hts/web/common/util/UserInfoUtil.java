package com.hts.web.common.util;

import com.hts.web.base.constant.Tag;

public class UserInfoUtil {

	/**
	 * 判断是否为IM的版本
	 * 
	 * @param ver
	 * @return
	 */
	public static Boolean checkIsImVersion(Float ver) {
		if(ver != null && ver >= Tag.VERSION_2_9_82) {
			return true;
		}
		return false;
	}
	
	/**
	 * 判断是否为AT版本
	 * 
	 * @param ver
	 * @return
	 */
	public static Boolean checkIsAtVersion(Float ver) {
		if(ver != null && ver >= Tag.VERSION_3_0_5) {
			return true;
		}
		return false;
	}
	
	/**
	 * 根据用户版本获取系统消息代号<br>
	 * 因为万恶的Android 2.9.83之前的版本没有做消息代号兼容，直接闪退。
	 * 
	 * @param ver
	 * @param msgCode
	 * @return
	 */
	public static int getSysMsgCode(Float ver, Integer msgCode) {
		if(ver <= Tag.VERSION_2_9_83 && msgCode >= Tag.USER_MSG_CHANNEl_TOP_ONE) {
			return Tag.USER_MSG_SYS;
		}
		return msgCode;
	}
}
