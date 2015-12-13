package com.hts.web.operations.pojo;

import java.io.Serializable;

/**
 * 商品集合公告对象
 * 
 * @author zhangbo	2015年12月13日
 *
 */
public class ItemSetBulletin implements Serializable {
	
	/**
	 * 序列号
	 * @author zhangbo	2015年12月13日
	 */
	private static final long serialVersionUID = -3489492366628853997L;
	private Integer id;
	private String bulletinPath;
	private String bulletinThumb;
	private String bulletinName;
	private Integer bulletinType;
	private String link;
	
	/**
	 * 商品集合公告分类
	 * @author zhangbo	2015年12月7日
	 */
	private Integer category;
	
	/**
	 * 秒杀商品截止日期，存储的为截止日期的时间戳
	 * @author zhangbo	2015年12月7日
	 */
	private long deadline;
	
	/**
	 * 序号
	 * @author zhangbo	2015年12月12日
	 */
	private Integer serial;

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the bulletinPath
	 */
	public String getBulletinPath() {
		return bulletinPath;
	}

	/**
	 * @param bulletinPath the bulletinPath to set
	 */
	public void setBulletinPath(String bulletinPath) {
		this.bulletinPath = bulletinPath;
	}

	/**
	 * @return the bulletinThumb
	 */
	public String getBulletinThumb() {
		return bulletinThumb;
	}

	/**
	 * @param bulletinThumb the bulletinThumb to set
	 */
	public void setBulletinThumb(String bulletinThumb) {
		this.bulletinThumb = bulletinThumb;
	}

	/**
	 * @return the bulletinName
	 */
	public String getBulletinName() {
		return bulletinName;
	}

	/**
	 * @param bulletinName the bulletinName to set
	 */
	public void setBulletinName(String bulletinName) {
		this.bulletinName = bulletinName;
	}

	/**
	 * @return the bulletinType
	 */
	public Integer getBulletinType() {
		return bulletinType;
	}

	/**
	 * @param bulletinType the bulletinType to set
	 */
	public void setBulletinType(Integer bulletinType) {
		this.bulletinType = bulletinType;
	}

	/**
	 * @return the link
	 */
	public String getLink() {
		return link;
	}

	/**
	 * @param link the link to set
	 */
	public void setLink(String link) {
		this.link = link;
	}

	/**
	 * @return the category
	 */
	public Integer getCategory() {
		return category;
	}

	/**
	 * @param category the category to set
	 */
	public void setCategory(Integer category) {
		this.category = category;
	}

	/**
	 * @return the deadline
	 */
	public long getDeadline() {
		return deadline;
	}

	/**
	 * @param deadline the deadline to set
	 */
	public void setDeadline(long deadline) {
		this.deadline = deadline;
	}

	/**
	 * @return the serial
	 */
	public Integer getSerial() {
		return serial;
	}

	/**
	 * @param serial the serial to set
	 */
	public void setSerial(Integer serial) {
		this.serial = serial;
	} 

}
