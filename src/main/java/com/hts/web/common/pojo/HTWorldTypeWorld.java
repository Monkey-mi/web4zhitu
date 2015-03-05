package com.hts.web.common.pojo;

import java.io.Serializable;

/**
 * <p>
 * 分类织图POJO
 * </p>
 * 
 * 创建时间：2014-1-19
 * 
 * @author lynch
 * 
 */
public class HTWorldTypeWorld implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 195962092832682024L;
	private Integer id;
	private Integer worldId;
	private Integer typeId;
	private Integer superb;
	private Integer valid;
	private Integer serial;
	private Integer weight;
	private Integer recommenderId;

	public HTWorldTypeWorld() {
		super();
	}

	public HTWorldTypeWorld(Integer id, Integer worldId, Integer typeId,
			Integer superb, Integer valid, Integer serial, Integer weight,
			Integer recommenderId) {
		super();
		this.id = id;
		this.worldId = worldId;
		this.typeId = typeId;
		this.superb = superb;
		this.valid = valid;
		this.serial = serial;
		this.weight = weight;
		this.recommenderId = recommenderId;
	}

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

	public Integer getTypeId() {
		return typeId;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}

	public Integer getSuperb() {
		return superb;
	}

	public void setSuperb(Integer superb) {
		this.superb = superb;
	}

	public Integer getValid() {
		return valid;
	}

	public void setValid(Integer valid) {
		this.valid = valid;
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

	public Integer getRecommenderId() {
		return recommenderId;
	}

	public void setRecommenderId(Integer recommenderId) {
		this.recommenderId = recommenderId;
	}


}
