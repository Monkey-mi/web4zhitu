package com.hts.web.common.pojo;

import java.io.Serializable;

/**
 * <p>
 * 用户推荐信息POJO
 * </p>
 * 
 * 创建时间:2015-03-17
 * @author lynch
 *
 */
public class UserRecInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4630527929057122990L;

	private Integer id;
	private Integer platformCode;
	private String province;
	private String city;
	private String userLabel;
	private Integer concernCount;

	public UserRecInfo() {
		super();
	}

	public UserRecInfo(Integer id, Integer platformCode, String province,
			String city, String userLabel, Integer concernCount) {
		super();
		this.id = id;
		this.platformCode = platformCode;
		this.province = province;
		this.city = city;
		this.userLabel = userLabel;
		this.concernCount = concernCount;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getPlatformCode() {
		return platformCode;
	}

	public void setPlatformCode(Integer platformCode) {
		this.platformCode = platformCode;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getUserLabel() {
		return userLabel;
	}

	public void setUserLabel(String userLabel) {
		this.userLabel = userLabel;
	}

	public Integer getConcernCount() {
		return concernCount;
	}

	public void setConcernCount(Integer concernCount) {
		this.concernCount = concernCount;
	}

}
