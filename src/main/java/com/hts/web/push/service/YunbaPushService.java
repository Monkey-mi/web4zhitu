package com.hts.web.push.service;

import com.hts.web.base.HTSException;
import com.hts.web.common.pojo.PushIM;

public interface YunbaPushService {
	
	/**
	 * 推送IM消息
	 * 
	 * @param toAlias
	 * @param msg
	 * @param phoneCode
	 * @param notified
	 * @throws HTSException
	 */
	public void pushIMMsg(Integer toAlias, PushIM msg, String title, Integer phoneCode, Integer notified, Integer shield) throws HTSException;
	
	/**
	 * 推送频道消息
	 * 
	 * @param topic
	 * @param msg
	 * @throws HTSException
	 */
	public void pushTopicMsg(String topic, PushIM msg) throws HTSException;

	/**
	 * 
	 * @param topic
	 * @param msg
	 * @throws Exception
	 */
	public void pushAppMsg(String topic, String msg) throws Exception;
}
