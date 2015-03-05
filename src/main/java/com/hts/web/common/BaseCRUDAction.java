package com.hts.web.common;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * <p>
 * 系统管理（增、删、改、查）基础类，配合easyui一起使用
 * </p>
 * 
 * 创建时间：2012-11-06
 * 
 * @author ztj
 * 
 */
public class BaseCRUDAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5181470340302381760L;
	protected Map<String, Object> attrMap = new LinkedHashMap<String, Object>();
	protected Integer page;
	protected Integer rows;
	protected String sort = "id";
	protected String order = "desc";
	
	public Integer getPage() {
		return page;
	}
	public void setPage(Integer page) {
		this.page = page;
	}
	public Integer getRows() {
		return rows;
	}
	public void setRows(Integer rows) {
		this.rows = rows;
	}
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}
	
	


}
