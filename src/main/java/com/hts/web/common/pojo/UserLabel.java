package com.hts.web.common.pojo;

import java.io.Serializable;

/**
 * <p>
 * 用户标签POJO
 * </p>
 * 
 * 创建时间：2014-1-13
 * 
 * @author ztj
 * 
 */
public class UserLabel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9042112386284453479L;
	private Integer id;
	private String labelName;
	private String labelPinyin;
	private Integer labelState;
	private Integer labelSex;
	private Integer valid;
	private Integer serial;
	private Integer weight;
	
	public UserLabel() {
		super();
	}

	public UserLabel(Integer id, String labelName, String labelPinyin,
			Integer labelState, Integer labelSex, Integer valid,
			Integer serial, Integer weight) {
		super();
		this.id = id;
		this.labelName = labelName;
		this.labelPinyin = labelPinyin;
		this.labelState = labelState;
		this.labelSex = labelSex;
		this.valid = valid;
		this.serial = serial;
		this.weight = weight;
	}

	public Integer getLabelState() {
		return labelState;
	}

	public void setLabelState(Integer labelState) {
		this.labelState = labelState;
	}

	public Integer getLabelSex() {
		return labelSex;
	}

	public void setLabelSex(Integer labelSex) {
		this.labelSex = labelSex;
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

}
