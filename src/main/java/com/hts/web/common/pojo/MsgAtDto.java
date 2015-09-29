package com.hts.web.common.pojo;

import java.io.Serializable;
import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

/**
 * <p>
 * AT消息DTO,主要用于用户查看被at消息列表
 * </p>
 * 
 * @version 3.0.5
 * @author lynch 2015-09-22
 *
 */
public class MsgAtDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4638728649629095808L;
	private Integer id;
	private Integer worldId;
	private Integer objType;
	private Integer objId;
	private Date atTime;
	private String content;
	private MsgAtUserDto userInfo;
	private MsgAtWorldDto htworld;
	
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
	
	public Integer getObjType() {
		return objType;
	}
	
	public void setObjType(Integer objType) {
		this.objType = objType;
	}
	
	public Integer getObjId() {
		return objId;
	}
	
	public void setObjId(Integer objId) {
		this.objId = objId;
	}
	
	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	public Date getAtTime() {
		return atTime;
	}
	
	public void setAtTime(Date atTime) {
		this.atTime = atTime;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}

	public MsgAtUserDto getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(MsgAtUserDto userInfo) {
		this.userInfo = userInfo;
	}

	public MsgAtWorldDto getHtworld() {
		return htworld;
	}

	public void setHtworld(MsgAtWorldDto htworld) {
		this.htworld = htworld;
	}
	
}
