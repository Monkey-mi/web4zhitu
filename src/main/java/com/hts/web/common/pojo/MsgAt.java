package com.hts.web.common.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * AT消息POJO
 * 
 * @author lynch 2015-09-22
 *
 */
public class MsgAt implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7040940154070092218L;

	private Integer id;
	private Integer userId;
	private Integer atId;
	private Integer worldId;
	private Integer objType;
	private Integer objId;
	private String content;
	private String atName;
	
	
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

	public Integer getAtId() {
		return atId;
	}

	public void setAtId(Integer atId) {
		this.atId = atId;
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
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getAtName() {
		return atName;
	}

	public void setAtName(String atName) {
		this.atName = atName;
	}

}
