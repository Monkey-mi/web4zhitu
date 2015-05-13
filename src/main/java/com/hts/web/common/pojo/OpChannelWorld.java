package com.hts.web.common.pojo;

import java.io.Serializable;
import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

/**
 * <p>
 * 频道织图POJO
 * </p>
 * 
 * 创建时间: 2015-05-01
 * 
 * @author lynch
 *
 */
public class OpChannelWorld implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 157956695027833615L;

	private Integer id;
	private Integer channelId;
	private Integer worldId;
	private Integer authorId;
	private Date dateAdded;
	private Integer valid;
	private Integer notified;
	private Integer superb;
	private Integer serial;
	
	public OpChannelWorld() {
		super();
	}

	public OpChannelWorld(Integer id, Integer channelId, Integer worldId,
			Integer authorId, Date dateAdded, Integer valid, Integer notified,
			Integer superb, Integer serial) {
		super();
		this.id = id;
		this.channelId = channelId;
		this.worldId = worldId;
		this.authorId = authorId;
		this.dateAdded = dateAdded;
		this.valid = valid;
		this.notified = notified;
		this.superb = superb;
		this.serial = serial;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getChannelId() {
		return channelId;
	}

	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}

	public Integer getWorldId() {
		return worldId;
	}

	public void setWorldId(Integer worldId) {
		this.worldId = worldId;
	}

	public Integer getAuthorId() {
		return authorId;
	}

	public void setAuthorId(Integer authorId) {
		this.authorId = authorId;
	}

	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public Date getDateAdded() {
		return dateAdded;
	}

	public void setDateAdded(Date dateAdded) {
		this.dateAdded = dateAdded;
	}

	public Integer getValid() {
		return valid;
	}

	public void setValid(Integer valid) {
		this.valid = valid;
	}

	public Integer getNotified() {
		return notified;
	}

	public void setNotified(Integer notified) {
		this.notified = notified;
	}

	public Integer getSuperb() {
		return superb;
	}

	public void setSuperb(Integer superb) {
		this.superb = superb;
	}

	public Integer getSerial() {
		return serial;
	}

	public void setSerial(Integer serial) {
		this.serial = serial;
	}

}
