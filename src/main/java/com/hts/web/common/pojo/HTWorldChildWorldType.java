package com.hts.web.common.pojo;

import java.io.Serializable;

/**
 * <p>
 * 子世界类型POJO
 * </p>
 * 
 * 创建时间：2014-6-13
 * 
 * @author tianjie
 * 
 */
public class HTWorldChildWorldType implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4528836166810151206L;

	private Integer id;
	private String typePath;
	private Integer total;
	private Integer useCount;
	private String typeDesc;
	private String descPath;
	private String labelName;
	private Integer serial;
	
	public HTWorldChildWorldType(Integer id, String typePath, Integer total,
			Integer useCount, String typeDesc, String descPath, String labelName, 
			Integer serial) {
		super();
		this.id = id;
		this.typePath = typePath;
		this.total = total;
		this.useCount = useCount;
		this.typeDesc = typeDesc;
		this.descPath = descPath;
		this.labelName = labelName;
		this.serial = serial;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTypePath() {
		return typePath;
	}

	public void setTypePath(String typePath) {
		this.typePath = typePath;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}
	
	public Integer getUseCount() {
		return useCount;
	}

	public void setUseCount(Integer useCount) {
		this.useCount = useCount;
	}
	
	public String getTypeDesc() {
		return typeDesc;
	}

	public void setTypeDesc(String typeDesc) {
		this.typeDesc = typeDesc;
	}

	public String getDescPath() {
		return descPath;
	}

	public void setDescPath(String descPath) {
		this.descPath = descPath;
	}
	
	public String getLabelName() {
		return labelName;
	}

	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}

	public Integer getSerial() {
		return serial;
	}

	public void setSerial(Integer serial) {
		this.serial = serial;
	}

}
