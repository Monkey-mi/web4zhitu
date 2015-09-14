package com.hts.web.common.pojo;

import java.io.Serializable;
import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

/**
 * <p>
 * 世界标签POJO
 * </p>
 * 
 * 创建时间：2012-11-01
 * 
 * @author ztj
 * 
 */
public class HTWorldLabel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6005260287502136773L;

	private Integer id;
	private String labelName;
	private String labelPinyin;
	private Integer worldCount;
	private Integer superbCount;
	private Date dateAdded;
	private Integer labelState;
	private Integer valid;
	private Integer serial;
	private Integer weight;

	public HTWorldLabel() {
		super();
	}

	public HTWorldLabel(Integer id, String labelName, String labelPinyin,
			Integer worldCount, Integer superbCount, Date dateAdded, 
			Integer labelState, Integer valid, Integer serial,
			Integer weight) {
		super();
		this.id = id;
		this.labelName = labelName;
		this.labelPinyin = labelPinyin;
		this.worldCount = worldCount;
		this.superbCount = superbCount;
		this.dateAdded = dateAdded;
		this.labelState = labelState;
		this.valid = valid;
		this.serial = serial;
		this.weight = weight;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLabelName() {
		return labelName;
	}

	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}

	public String getLabelPinyin() {
		return labelPinyin;
	}

	public void setLabelPinyin(String labelPinyin) {
		this.labelPinyin = labelPinyin;
	}

	public Integer getWorldCount() {
		return worldCount;
	}

	public void setWorldCount(Integer worldCount) {
		this.worldCount = worldCount;
	}
	
	public Integer getSuperbCount() {
		return superbCount;
	}

	public void setSuperbCount(Integer superbCount) {
		this.superbCount = superbCount;
	}

	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public Date getDateAdded() {
		return dateAdded;
	}

	public void setDateAdded(Date dateAdded) {
		this.dateAdded = dateAdded;
	}
	
	public Integer getLabelState() {
		return labelState;
	}

	public void setLabelState(Integer labelState) {
		this.labelState = labelState;
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

}
