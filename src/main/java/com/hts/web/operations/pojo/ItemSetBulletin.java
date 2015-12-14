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
	
	/**
	 * 商品集合公告id，来源于数据库ItemSet对象的id
	 * @author zhangbo	2015年12月14日
	 */
	private Integer id;
	
	/**
	 * 商品集合公告图片路径
	 * @author zhangbo	2015年12月14日
	 */
	private String bulletinPath;
	
	/**
	 * 商品集合公告缩略图路径
	 * @author zhangbo	2015年12月14日
	 */
	private String bulletinThumb;
	
	
	/**
	 * 商品集合公告名称，作为标题使用
	 * @author zhangbo	2015年12月14日
	 */
	private String bulletinName;
	
	/**
	 * 商品集合公告描述
	 * @author zhangbo	2015年12月14日
	 */
	private String bulletinDesc;
	
	
	/**
	 * 商品集合公告类型，与公告对象使用的几种类型一致，但是商品公告类型现在恒定为1，即网页链接类型
	 * @author zhangbo	2015年12月14日
	 */
	private Integer bulletinType;
	
	/**
	 * 商品集合公告链接内容，为网页地址
	 * @author zhangbo	2015年12月14日
	 */
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
	 * @return the bulletinDesc
	 */
	public String getBulletinDesc() {
		return bulletinDesc;
	}

	/**
	 * @param bulletinDesc the bulletinDesc to set
	 */
	public void setBulletinDesc(String bulletinDesc) {
		this.bulletinDesc = bulletinDesc;
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
