package com.hts.web.common.pojo;

import java.io.Serializable;

/**
 * <p>
 * 子世界类型数据数据传输对象
 * </p>
 * 
 * 创建时间：2014-6-13
 * 
 * @author tianjie
 * 
 */
public class HTWorldChildWorldTypeDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5770364319488673997L;
	
	private Integer id;
	private String typePath;
	private String typePath1;
	private String typePath2;
	private String typePath3;
	private String typePath4;
	private String typePath5;
	private String typePath6;
	private String typePath7;
	private String typePath8;
	private String typePath9;
	private String typePath10;
	private Integer total;
	private Integer useCount;
	private String typeDesc;
	private String descPath;
	private String labelName;
	private Integer serial;

	public HTWorldChildWorldTypeDto() {
		super();
	}

	public HTWorldChildWorldTypeDto(Integer id, String typePath,
			String typePath1, String typePath2, String typePath3,
			String typePath4, String typePath5, String typePath6,
			String typePath7, String typePath8, String typePath9,
			String typePath10, Integer total, Integer useCount, 
			String typeDesc, String descPath, String labelName, Integer serial) {
		super();
		this.id = id;
		this.typePath = typePath;
		this.typePath1 = typePath1;
		this.typePath2 = typePath2;
		this.typePath3 = typePath3;
		this.typePath4 = typePath4;
		this.typePath5 = typePath5;
		this.typePath6 = typePath6;
		this.typePath7 = typePath7;
		this.typePath8 = typePath8;
		this.typePath9 = typePath9;
		this.typePath10 = typePath10;
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
	
	public String getTypePath1() {
		return typePath1;
	}

	public void setTypePath1(String typePath1) {
		this.typePath1 = typePath1;
	}

	public String getTypePath2() {
		return typePath2;
	}

	public void setTypePath2(String typePath2) {
		this.typePath2 = typePath2;
	}

	public String getTypePath3() {
		return typePath3;
	}

	public void setTypePath3(String typePath3) {
		this.typePath3 = typePath3;
	}

	public String getTypePath4() {
		return typePath4;
	}

	public void setTypePath4(String typePath4) {
		this.typePath4 = typePath4;
	}

	public String getTypePath5() {
		return typePath5;
	}

	public void setTypePath5(String typePath5) {
		this.typePath5 = typePath5;
	}
	
	public String getTypePath6() {
		return typePath6;
	}

	public void setTypePath6(String typePath6) {
		this.typePath6 = typePath6;
	}

	public String getTypePath7() {
		return typePath7;
	}

	public void setTypePath7(String typePath7) {
		this.typePath7 = typePath7;
	}

	public String getTypePath8() {
		return typePath8;
	}

	public void setTypePath8(String typePath8) {
		this.typePath8 = typePath8;
	}

	public String getTypePath9() {
		return typePath9;
	}

	public void setTypePath9(String typePath9) {
		this.typePath9 = typePath9;
	}

	public String getTypePath10() {
		return typePath10;
	}

	public void setTypePath10(String typePath10) {
		this.typePath10 = typePath10;
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
