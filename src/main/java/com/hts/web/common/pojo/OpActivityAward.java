package com.hts.web.common.pojo;

import java.io.Serializable;

/**
 * <p>
 * 活动奖品POJO
 * </p>
 * 
 * 创建时间：2014-5-18
 * 
 * @author tianjie
 * 
 */
public class OpActivityAward implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8807703202913664497L;
	private Integer id;
	private Integer activityId;
	private String iconThumbPath;
	private String iconPath;
	private String awardName;
	private String awardDesc;
	private Double price;
	private String awardLink;
	private Integer total; // 总数
	private Integer remain; // 剩余数量
	private Integer serial; // 序号

	public OpActivityAward() {
		super();
	}
	
	public OpActivityAward(Integer activityId, String iconThumbPath,
			String iconPath, String awardName, String awardDesc, Double price,
			String awardLink, Integer total, Integer remain, Integer serial) {
		super();
		this.activityId = activityId;
		this.iconThumbPath = iconThumbPath;
		this.iconPath = iconPath;
		this.awardName = awardName;
		this.awardDesc = awardDesc;
		this.price = price;
		this.awardLink = awardLink;
		this.total = total;
		this.remain = remain;
		this.serial = serial;
	}

	public OpActivityAward(Integer id, Integer activityId,
			String iconThumbPath, String iconPath, String awardName, 
			String awardDesc, Double price, String awardLink, Integer total,
			Integer remain, Integer serial) {
		super();
		this.id = id;
		this.activityId = activityId;
		this.iconThumbPath = iconThumbPath;
		this.iconPath = iconPath;
		this.awardName = awardName;
		this.awardDesc = awardDesc;
		this.price = price;
		this.awardLink = awardLink;
		this.total = total;
		this.remain = remain;
		this.serial = serial;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getActivityId() {
		return activityId;
	}

	public void setActivityId(Integer activityId) {
		this.activityId = activityId;
	}
	
	public String getIconThumbPath() {
		return iconThumbPath;
	}

	public void setIconThumbPath(String iconThumbPath) {
		this.iconThumbPath = iconThumbPath;
	}
	
	public String getAwardName() {
		return awardName;
	}

	public void setAwardName(String awardName) {
		this.awardName = awardName;
	}

	public String getAwardDesc() {
		return awardDesc;
	}

	public void setAwardDesc(String awardDesc) {
		this.awardDesc = awardDesc;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getAwardLink() {
		return awardLink;
	}

	public void setAwardLink(String awardLink) {
		this.awardLink = awardLink;
	}

	public String getIconPath() {
		return iconPath;
	}

	public void setIconPath(String iconPath) {
		this.iconPath = iconPath;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public Integer getRemain() {
		return remain;
	}

	public void setRemain(Integer remain) {
		this.remain = remain;
	}

	public Integer getSerial() {
		return serial;
	}

	public void setSerial(Integer serial) {
		this.serial = serial;
	}

}
