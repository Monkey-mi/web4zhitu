package com.hts.web.common.pojo;

import java.io.Serializable;

/**
 * <p>
 * 用户认证类型信息POJO
 * </p>
 * 
 * 创建时间：2014-7-16
 * 
 * @author tianjie
 * 
 */
public class UserVerify implements Serializable, ObjectWithUserVerify {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7079830466631264752L;

	private Integer id;
	private String verifyName;
	private String verifyDesc;
	private String verifyIcon;
	private Integer serial;
	
	public UserVerify() {
		super();
	}

	public UserVerify(Integer id, String verifyName, String verifyDesc,
			String verifyIcon, Integer serial) {
		super();
		this.id = id;
		this.verifyName = verifyName;
		this.verifyDesc = verifyDesc;
		this.verifyIcon = verifyIcon;
		this.serial = serial;
	}
	
	public UserVerify(String verifyName, String verifyDesc, String verifyIcon,
			Integer serial) {
		super();
		this.verifyName = verifyName;
		this.verifyDesc = verifyDesc;
		this.verifyIcon = verifyIcon;
		this.serial = serial;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getVerifyName() {
		return verifyName;
	}

	public void setVerifyName(String verifyName) {
		this.verifyName = verifyName;
	}

	public String getVerifyDesc() {
		return verifyDesc;
	}

	public void setVerifyDesc(String verifyDesc) {
		this.verifyDesc = verifyDesc;
	}

	public String getVerifyIcon() {
		return verifyIcon;
	}

	public void setVerifyIcon(String verifyIcon) {
		this.verifyIcon = verifyIcon;
	}

	public Integer getSerial() {
		return serial;
	}

	public void setSerial(Integer serial) {
		this.serial = serial;
	}

	@Override
	public Integer getVerifyId() {
		return id;
	}

}
