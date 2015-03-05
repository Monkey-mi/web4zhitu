package com.hts.web.common.pojo;

import java.io.Serializable;
import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

/**
 * <p>
 * 用户推荐POJO
 * </p>
 * 
 * 创建时间：2014-3-17
 * @author tianjie
 *
 */
public class OpUserRecommend implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8872061160236517481L;
	private Integer id;
	private Integer userId;
	private Integer verifyId;
	private String recommendDesc;
	private Integer recommenderId;
	private Date dateAdded;
	private Date dateModified;
	private Integer userAccept;
	private Integer sysAccept;

	public OpUserRecommend() {
		super();
	}

	public OpUserRecommend(Integer id, Integer userId, Integer verifyId, String recommendDesc,
			Integer recommenderId, Date dateAdded, Date dateModified, Integer userAccept,
			Integer sysAccept) {
		super();
		this.id = id;
		this.userId = userId;
		this.verifyId = verifyId;
		this.recommendDesc = recommendDesc;
		this.recommenderId = recommenderId;
		this.dateAdded = dateAdded;
		this.dateModified = dateModified;
		this.userAccept = userAccept;
		this.sysAccept = sysAccept;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
	public Integer getVerifyId() {
		return verifyId;
	}

	public void setVerifyId(Integer verifyId) {
		this.verifyId = verifyId;
	}

	public String getRecommendDesc() {
		return recommendDesc;
	}

	public void setRecommendDesc(String recommendDesc) {
		this.recommendDesc = recommendDesc;
	}

	public Integer getRecommenderId() {
		return recommenderId;
	}

	public void setRecommenderId(Integer recommenderId) {
		this.recommenderId = recommenderId;
	}

	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	public Date getDateAdded() {
		return dateAdded;
	}

	public void setDateAdded(Date dateAdded) {
		this.dateAdded = dateAdded;
	}

	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	public Date getDateModified() {
		return dateModified;
	}

	public void setDateModified(Date dateModified) {
		this.dateModified = dateModified;
	}

	public Integer getUserAccept() {
		return userAccept;
	}

	public void setUserAccept(Integer userAccept) {
		this.userAccept = userAccept;
	}

	public Integer getSysAccept() {
		return sysAccept;
	}

	public void setSysAccept(Integer sysAccept) {
		this.sysAccept = sysAccept;
	}

}
