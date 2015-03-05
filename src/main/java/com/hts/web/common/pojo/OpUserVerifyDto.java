package com.hts.web.common.pojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 认证用户索引POJO
 * </p>
 * 
 * 创建时间：2014-7-15
 * @author tianjie
 *
 */
public class OpUserVerifyDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 722696744340091999L;
	private Integer id;
	private String verifyName;
	private String verifyDesc;
	private String verifyIcon;

	private List<UserVerifyDto> userInfo = new ArrayList<UserVerifyDto>();
	
	public OpUserVerifyDto() {
		super();
	}
	
	public OpUserVerifyDto(Integer id, String verifyName, String verifyDesc,
			String verifyIcon) {
		super();
		this.id = id;
		this.verifyName = verifyName;
		this.verifyDesc = verifyDesc;
		this.verifyIcon = verifyIcon;
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

	public List<UserVerifyDto> getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(List<UserVerifyDto> userInfo) {
		this.userInfo = userInfo;
	}

}
