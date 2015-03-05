package com.hts.web.common.pojo;

import java.io.Serializable;

import com.hts.web.base.constant.Tag;

/**
 * <p>
 * 标签-织图关联POJO
 * </p>
 * 
 * 创建时间：2014-1-22
 * @author lynch
 *
 */
public class HTWorldLabelWorld implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1911935043279189018L;
	
	private Integer id;
	private Integer worldId;
	private Integer userId;
	private Integer labelId;
	private Integer valid = Tag.FALSE;
	private Integer serial = 0;
	private Integer weight = 0;
	
	public HTWorldLabelWorld() {
		super();
	}
	
	public HTWorldLabelWorld(Integer id, Integer worldId, Integer userId, 
			Integer labelId, Integer valid, Integer serial, Integer weight) {
		super();
		this.id = id;
		this.worldId = worldId;
		this.userId = userId;
		this.labelId = labelId;
		this.valid = valid;
		this.serial = serial;
		this.weight = weight;
	}

	public Integer getWorldId() {
		return worldId;
	}
	
	public void setWorldId(Integer worldId) {
		this.worldId = worldId;
	}
	
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getLabelId() {
		return labelId;
	}
	
	public void setLabelId(Integer labelId) {
		this.labelId = labelId;
	}

	public Integer getValid() {
		return valid;
	}

	public void setValid(Integer valid) {
		this.valid = valid;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getSerial() {
		return serial;
	}

	public void setSerial(Integer serial) {
		this.serial = serial;
	}

	public Integer getWeight() {
		return weight;
	}
	
	public void setWeight(Integer weight) {
		this.weight = weight;
	}
	
}
