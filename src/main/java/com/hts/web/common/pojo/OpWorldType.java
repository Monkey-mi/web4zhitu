package com.hts.web.common.pojo;

import java.io.Serializable;

/**
 * <p>
 * 广场分类标签POJO
 * <p>
 * 
 * @author ztj
 *
 */
public class OpWorldType implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -22144740231957742L;
	private Integer id;
	private String labelName;
	private String labelDesc;
	private Integer concerned = 0; // 是否关注标记
	
	public OpWorldType() {
		super();
	}
	
	public OpWorldType(Integer id, String labelName,
			String labelDesc) {
		this.id = id;
		this.labelName = labelName;
		this.labelDesc = labelDesc;
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
	public String getLabelDesc() {
		return labelDesc;
	}
	public void setLabelDesc(String labelDesc) {
		this.labelDesc = labelDesc;
	}

	public Integer getConcerned() {
		return concerned;
	}

	public void setConcerned(Integer concerned) {
		this.concerned = concerned;
	}
	
	
	
}
