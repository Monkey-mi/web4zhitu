package com.hts.web.common.pojo;

import java.io.Serializable;

public abstract class AbstractNumberDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1351915948687975491L;
	/**
	 * 
	 */
	private Integer maxId = 0;
	private Integer firstRow; // 起始位置
	private Integer limit;
	
	public Integer getMaxId() {
		return maxId;
	}

	public void setMaxId(Integer maxId) {
		this.maxId = maxId;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public Integer getFirstRow() {
		return firstRow;
	}

	public void setFirstRow(Integer firstRow) {
		this.firstRow = firstRow;
	}

}
