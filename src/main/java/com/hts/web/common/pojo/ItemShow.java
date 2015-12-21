package com.hts.web.common.pojo;

import java.io.Serializable;

public class ItemShow implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 505400429517366420L;

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
	@Override
	public String toString() {
		return "ItemShow [id=" + id + ", worldId=" + worldId + ", itemSetId=" + itemSetId + ", serial=" + serial + "]";
	}
	
	
}
