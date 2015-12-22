package com.hts.web.trade.item.dto;

import java.io.Serializable;

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
	
	@Override
	public String toString() {
		return "ItemShowDTO [id=" + id + ", worldId=" + worldId + ", itemSetId=" + itemSetId + ", serial=" + serial
				+ ", title_thumb_path=" + title_thumb_path + ", worldDes=" + worldDes + ", addr=" + addr + ", userName="
				+ userName + ", userAvatar=" + userAvatar + "]";
	}
	
}
