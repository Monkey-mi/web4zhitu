package com.hts.web.common.pojo;


/**
 * <p>
 * 消息推送基础类，用于封装推送消息公共字段
 * </p>
 * @author ztj
 *
 */
public class PushBase {

	protected Integer action; // 动作
	protected String user; // 关联用户名
	
	public PushBase() {
		super();
	}
	
	public PushBase(Integer action, String user) {
		this.action = action;
		this.user = user;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public Integer getAction() {
		return action;
	}

	public void setAction(Integer action) {
		this.action = action;
	}
	
}
