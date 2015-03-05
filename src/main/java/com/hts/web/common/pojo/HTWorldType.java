package com.hts.web.common.pojo;

import java.io.Serializable;

/**
 * <p>
 * 织图分类POJO
 * </p>
 * 
 * 创建时间：2014-1-19
 * 
 * @author lynch
 * 
 */
public class HTWorldType implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8203245921875841845L;

	private Integer id;
	private String typeName;
	private String typePinyin;
	private String typeDesc;
	private Integer valid;
	private Integer serial;

	public HTWorldType() {
		super();
	}

	public HTWorldType(Integer id, String typeName, String typePinyin,
			String typeDesc, Integer valid, Integer serial) {
		super();
		this.id = id;
		this.typeName = typeName;
		this.typePinyin = typePinyin;
		this.typeDesc = typeDesc;
		this.valid = valid;
		this.serial = serial;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getTypePinyin() {
		return typePinyin;
	}

	public void setTypePinyin(String typePinyin) {
		this.typePinyin = typePinyin;
	}

	public String getTypeDesc() {
		return typeDesc;
	}

	public void setTypeDesc(String typeDesc) {
		this.typeDesc = typeDesc;
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

}
