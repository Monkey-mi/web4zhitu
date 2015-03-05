package com.hts.web.push.yunba;

import io.socket.IOAcknowledge;
import io.socket.IOCallback;
import io.socket.SocketIO;
import io.socket.SocketIOException;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

//@Component("HTSSocketIOClient")
public class SocketIOClientImpl implements SocketIOClient, IOCallback {

	private Logger logger = Logger.getLogger(SocketIOClientImpl.class);
	
	private SocketIO socket;

	private static final Integer QOS_LEVEL = 1;
	private static final String APPKEY = "540bbe74699da7376d411ddc";
	private static final String CONNECT_URL = "http://sock.yunba.io:3000/";
	
	
	public SocketIOClientImpl()  {
		super();
		init();
	}
	
	private void init(){
		socket = new SocketIO();
		logger.info("initial socket.io client and connect to yunba server");
		try {
			socket.connect(CONNECT_URL, this);
			Thread.sleep(3000);
		} catch (Exception e) {
			logger.error("initial socket.io connect error.", e);
		}
		
	}
	

	/**
	 * 关闭客户端
	 */
	public void close() {
		socket.disconnect();
	}
	

	@Override
	public void on(String event, IOAcknowledge ack, Object... args) {
		try {
			if (event.equals("socketconnectack")) {
				onSocketConnectAck();
			} else if (event.equals("connack")) {
				onConnAck((JSONObject) args[0]);
			} else if (event.equals("puback")) {
				onPubAck((JSONObject) args[0]);
			} else if (event.equals("publish2_ack")) {
				onPub2Ack((JSONObject) args[0]);
			}
		} catch (Exception e) {
			logger.warn(e);
		}
	}

	/**
	 * 触发 connect 命令，与 socket.io 服务器确认身份
	 * 
	 * @throws Exception
	 */
	public void onSocketConnectAck() throws Exception {
		logger.debug("onSocketConnectAck success, now connecting with APPKEY");
		socket.emit("connect", new JSONObject("{'appkey': '" + APPKEY + "'}"));
	}

	/**
	 * 消息连接成功后的回调
	 * 
	 * @param json
	 * @throws Exception
	 */
	public void onConnAck(JSONObject json) throws Exception {
		Boolean result = json.getBoolean("success");
		if(result)
			logger.debug("conPubAck success " + result);
		else {
			logger.warn("onConnAck  success " + result);
		}
	}
	
	/**
	 * 发布 publish, publish_to_alias 成功回调
	 * 
	 * @param json
	 * @throws Exception
	 */
	public void onPubAck(JSONObject json) throws Exception {
		Boolean result = json.getBoolean("success");
		if(result)
			logger.debug("conPubAck success " + result + ", msg id " + json.get("messageId"));
		else {
			logger.warn("conPubAck  failed, errorMsg " + json.getString("error_message") + ", msg id " + json.get("messageId"));
			throw new Exception(json.getString("error_message"));
		}
	}
	
	public void onPub2Ack(JSONObject json) throws Exception {
		Boolean result = json.getBoolean("success");
		if(result)
			logger.debug("conPub2Ack success, msg id " + json.get("messageId"));
		else {
			logger.warn("conPub2Ack  failed, errorMsg:" + json.getString("error_message") + ", msg id " + json.get("messageId"));
			throw new Exception(json.getString("error_message"));
		}
	}
	
	@Override
	public void onMessage(JSONObject json, IOAcknowledge ack) {
		try {
			logger.info("Server said:" + json.toString(2));
		} catch (JSONException e) {
			logger.warn(e);
		}
	}

	@Override
	public void onMessage(String data, IOAcknowledge ack) {
		logger.debug("Server said: " + data);
	}

	@Override
	public void onError(SocketIOException socketIOException) {
		logger.warn("an Error occured：" + socketIOException.getMessage() + ", and re-initial...");
		init();
	}

	@Override
	public void onDisconnect() {
		logger.debug("Connection terminated.");
	}

	@Override
	public void onConnect() {
		logger.debug("Connection established.");
	}

	@Override
	public void publish2Alias(Integer alias, String msg) throws Exception {
		publishToAlias(alias, msg, null);
	}
	
	@Override
	public void publishToAlias(Integer alias, String msg, JSONObject apns) throws Exception {
		JSONObject json = new JSONObject();
		JSONObject opts = new JSONObject();
		opts.put("qos", QOS_LEVEL);
		if(apns != null) {
			opts.put("apn_json", apns.toString());
		}
		json.put("opts", opts);
		json.put("alias", alias);
		json.put("msg", msg);
		socket.emit("publish2_to_alias", json);
	}

	@Override
	public void publishToTopic(String topic, String msg, JSONObject apns) throws Exception {
		JSONObject json = new JSONObject();
		JSONObject opts = new JSONObject();
		opts.put("qos", QOS_LEVEL);
		if(apns != null) {
			opts.put("apn_json", apns.toString());
		}
		json.put("opts", opts);
		json.put("topic", topic);
		json.put("msg", msg);
		socket.emit("publish2", json);
	}


	
}
