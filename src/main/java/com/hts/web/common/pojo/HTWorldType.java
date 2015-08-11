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

	public HTWorldType() {
		super();
	}

	public HTWorldType(Integer id, String typeName) {
		this.id = id;
		this.typeName = typeName;
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

}
