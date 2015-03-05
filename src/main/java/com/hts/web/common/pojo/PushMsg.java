package com.hts.web.common.pojo;

import java.io.Serializable;

/**
 * <p>
 * 评论推送POJO对象
 * </p>
 * 
 * 创建时间：2013-8-20
 * 
 * @author ztj
 * 
 */
public class PushMsg implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9074839968859607921L;

	private Integer action; // 动作
	private String user; // 关联用户名
	private String msg; // 消息
	private String id;

	public PushMsg() {
		super();
	}

	public PushMsg(Integer action, String user, String msg) {
		super();
		this.action = action;
		this.user = user;
		this.msg = msg;
	}
	
	public PushMsg(Integer action, String user, String msg, String id) {
		super();
		this.action = action;
		this.user = user;
		this.msg = msg;
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getAction() {
		return action;
	}

	public void setAction(Integer action) {
		this.action = action;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	

}
