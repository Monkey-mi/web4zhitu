package com.hts.web.push.service;

import java.util.Map;

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
	 * 推送公告
	 * 
	 * @param content
	 * @param recipientId
	 * @throws HTSException
	 */
	public void pushBulletin(String content, Integer recipientId) throws HTSException;
	
	/**
	 * 推送系统消息
	 * 
	 * @param topic
	 * @param msg
	 * @param extra
	 * @throws HTSException
	 */
	public void pushTopicMsg(String topic, PushIM msg, 
			Map<String, Object> extra) throws HTSException;

	/**
	 * 
	 * @param topic
	 * @param msg
	 * @throws Exception
	 */
	public void pushAppMsg(String topic, String msg) throws Exception;
}
