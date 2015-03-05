package com.hts.web.common.pojo;

import java.io.Serializable;
import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

/**
 * <p>
 * 私信POJO对象
 * </p>
 * 
 * 创建时间：2013-1-29
 * @author ztj
 *
 */
public class UserMsg implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2978348943668722430L;

	private Integer id;
	private Date msgDate;
	private String content;
	private Integer objType;
	private Integer objId = 0;
	private String objMeta;
	private String thumbPath;
	
	public UserMsg() {
		super();
	}
	
	public UserMsg(Integer id, Date msgDate, String content, Integer objType,
			Integer objId, String objMeta, String thumbPath) {
		this.id = id;
		this.msgDate = msgDate;
		this.content = content;
		this.objType = objType;
		this.objId = objId;
		this.objMeta = objMeta;
		this.thumbPath = thumbPath;
	}
	
	public UserMsg(Integer id, Date msgDate, String content, Integer objType) {
		this.id = id;
		this.msgDate = msgDate;
		this.content = content;
		this.objType = objType;
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public Date getMsgDate() {
		return msgDate;
	}
	public void setMsgDate(Date msgDate) {
		this.msgDate = msgDate;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
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
	
	public String getObjMeta() {
		return objMeta;
	}

	public void setObjMeta(String objMeta) {
		this.objMeta = objMeta;
	}

	public String getThumbPath() {
		return thumbPath;
	}
	public void setThumbPath(String thumbPath) {
		this.thumbPath = thumbPath;
	}
	
	
}
