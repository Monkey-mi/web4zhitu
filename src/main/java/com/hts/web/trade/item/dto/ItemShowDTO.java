package com.hts.web.trade.item.dto;

import java.io.Serializable;
import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

public class ItemShowDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7485783919462735550L;
	/**
	 * ID
	 */
	private Integer id;
	/**
	 * 织图ID
	 */
	private Integer worldId;
	/**
	 * 商品集合ID
	 */
	private Integer itemSetId;
	/**
	 * 排序号
	 */
	private Integer serial;
	/**
	 * 首页缩略图路径
	 */
	private String title_thumb_path;
	/**
	 * 织图描述
	 */
	private String worldDes;
	/**
	 * 地址描述
	 */
	private String addr;
	/**
	 * 用户名
	 */
	private String userName;
	/**
	 * 用户头像
	 */
	private String userAvatar;
	/**
	 * 编辑日期
	 */
	private Date dateModified;
	/**
	 * 图片数量
	 */
	private Integer  childCount;
	/**
	 * 点击数
	 */
	private Integer clickCount;
	/**
	 * 点赞数
	 */
	private Integer likeCount;
	/**
	 * 短链
	 */
	private String shortLink;
	/**
	 * 明星标识
	 */
	private String verifyIcon;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getWorldId() {
		return worldId;
	}
	public void setWorldId(Integer worldId) {
		this.worldId = worldId;
	}
	public Integer getItemSetId() {
		return itemSetId;
	}
	public void setItemSetId(Integer itemSetId) {
		this.itemSetId = itemSetId;
	}
	public Integer getSerial() {
		return serial;
	}
	public void setSerial(Integer serial) {
		this.serial = serial;
	}
	public String getTitle_thumb_path() {
		return title_thumb_path;
	}
	public void setTitle_thumb_path(String title_thumb_path) {
		this.title_thumb_path = title_thumb_path;
	}
	public String getWorldDes() {
		return worldDes;
	}
	public void setWorldDes(String worldDes) {
		this.worldDes = worldDes;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserAvatar() {
		return userAvatar;
	}
	public void setUserAvatar(String userAvatar) {
		this.userAvatar = userAvatar;
	}
	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	public Date getDateModified() {
		return dateModified;
	}
	public void setDateModified(Date dateModified) {
		this.dateModified = dateModified;
	}
	public Integer getChildCount() {
		return childCount;
	}
	public void setChildCount(Integer childCount) {
		this.childCount = childCount;
	}
	public Integer getClickCount() {
		return clickCount;
	}
	public void setClickCount(Integer clickCount) {
		this.clickCount = clickCount;
	}
	public Integer getLikeCount() {
		return likeCount;
	}
	public void setLikeCount(Integer likeCount) {
		this.likeCount = likeCount;
	}
	public String getShortLink() {
		return shortLink;
	}
	public void setShortLink(String shortLink) {
		this.shortLink = shortLink;
	}
	public String getVerifyIcon() {
		return verifyIcon;
	}
	public void setVerifyIcon(String verifyIcon) {
		this.verifyIcon = verifyIcon;
	}
	@Override
	public String toString() {
		return "ItemShowDTO [id=" + id + ", worldId=" + worldId + ", itemSetId=" + itemSetId + ", serial=" + serial
				+ ", title_thumb_path=" + title_thumb_path + ", worldDes=" + worldDes + ", addr=" + addr + ", userName="
				+ userName + ", userAvatar=" + userAvatar + ", dateModified=" + dateModified + ", childCount="
				+ childCount + ", clickCount=" + clickCount + ", likeCount=" + likeCount + ", shortLink=" + shortLink
				+ "]";
	}
	
}
