package com.hts.web.common.pojo;

import java.io.Serializable;

/**
 * <p>
 * 关注结果POJO
 * </p>
 * 
 * 创建时间：2014-09-17
 * 
 * @author tianjie
 * 
 */
public class PushStatus implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2829329306978188379L;

	private Integer id;
	private Integer userId;
	private Integer phone;
	private Integer isMututal;
	private Integer accept;
	private Integer shield;
	private Object interactRes;
	private String remarkMe;

	public PushStatus() {
		super();
	}

	public PushStatus(Integer id, Integer userId, Integer phone, Integer isMututal,
			Integer accept, Integer shield, String remarkMe) {
		super();
		this.id = id;
		this.userId = userId;
		this.phone = phone;
		this.isMututal = isMututal;
		this.accept = accept;
		this.shield = shield;
		this.remarkMe = remarkMe;
	}

	public PushStatus(Integer userId, Integer phone, Integer isMututal, Integer accept,
			Integer shield, String remarkMe) {
		super();
		this.userId = userId;
		this.phone = phone;
		this.isMututal = isMututal;
		this.accept = accept;
		this.shield = shield;
		this.remarkMe = remarkMe;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
	public Integer getPhone() {
		return phone;
	}

	public void setPhone(Integer phone) {
		this.phone = phone;
	}

	public Integer getIsMututal() {
		return isMututal;
	}

	public void setIsMututal(Integer isMututal) {
		this.isMututal = isMututal;
	}

	public Integer getAccept() {
		return accept;
	}

	public void setAccept(Integer accept) {
		this.accept = accept;
	}

	public Integer getShield() {
		return shield;
	}

	public void setShield(Integer shield) {
		this.shield = shield;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Object getInteractRes() {
		return interactRes;
	}

	public void setInteractRes(Object interactRes) {
		this.interactRes = interactRes;
	}

	public String getRemarkMe() {
		return remarkMe;
	}

	public void setRemarkMe(String remarkMe) {
		this.remarkMe = remarkMe;
	}

}
