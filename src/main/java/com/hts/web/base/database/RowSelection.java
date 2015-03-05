package com.hts.web.base.database;

/**
 * <p>
 * JDBC分页抽象
 * </p>
 * 
 * 创建时间：2012-11-02
 * 
 * @author ztj
 * 
 */
public class RowSelection {

	private Integer firstRow; // 起始位置
	private Integer maxRow; // 结束位置
	private Integer start;
	private Integer limit;

	public RowSelection(Integer start, Integer limit) {
		this.start = start;
		this.limit = limit;
		this.firstRow = (start - 1) * limit;
		this.maxRow = firstRow + limit;
	}

	public Integer getFirstRow() {
		return firstRow;
	}

	public void setFirstRow(Integer firstRow) {
		this.firstRow = firstRow;
	}

	public Integer getMaxRow() {
		return maxRow;
	}

	public void setMaxRow(Integer maxRow) {
		this.maxRow = maxRow;
	}

	public Integer getStart() {
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}
	
	

}
