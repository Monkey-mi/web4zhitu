package com.hts.web.push.yunba;

import org.json.JSONObject;

/**
 * <p>
 * socket.io消息客户端数据访问接口
 * </p>
 * 
 * @author tianjie
 *
 */
public interface SocketIOClient {

	/**
	 * 向指定别名的用户发送消息
	 * 
	 * @param alias
	 * @param msg
	 * @throws Exception
	 */
	public void publish2Alias(Integer alias, String msg) throws Exception;
	
	/**
	 * 向指定别名的用户发送消息
	 * 
	 * @param alias
	 * @param msg
	 * @throws Exception
	 */
	public void publishToAlias(Integer toAlias, String msg, JSONObject apns) throws Exception;
	
	/**
	 * 向指定topic发送消息
	 * 
	 * @param topic
	 * @param im
	 * @throws Exception
	 */
	public void publishToTopic(String topic, String msg, JSONObject apns) throws Exception;
	
}
