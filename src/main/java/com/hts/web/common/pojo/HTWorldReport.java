package com.hts.web.common.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 织图世界举报POJO
 * </p>
 * 
 * 创建时间：2013-7-5
 * @author ztj
 *
 */
public class HTWorldReport implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3248253868445537311L;
	private Integer id;
	private Integer userId;
	private Integer worldId;
	private String reportContent;
	private Date reportDate;
	private Integer valid;
	
	public HTWorldReport() {
		super();
	}

	public HTWorldReport(Integer id, Integer userId, Integer worldId,
			String reportContent, Date reportDate, Integer valid) {
		super();
		this.id = id;
		this.userId = userId;
		this.worldId = worldId;
		this.reportContent = reportContent;
		this.reportDate = reportDate;
		this.valid = valid;
	}

	public HTWorldReport(Integer userId, Integer worldId, String reportContent,
			Date reportDate, Integer valid) {
		super();
		this.userId = userId;
		this.worldId = worldId;
		this.reportContent = reportContent;
		this.reportDate = reportDate;
		this.valid = valid;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getWorldId() {
		return worldId;
	}

	public void setWorldId(Integer worldId) {
		this.worldId = worldId;
	}

	public String getReportContent() {
		return reportContent;
	}

	public void setReportContent(String reportContent) {
		this.reportContent = reportContent;
	}

	public Date getReportDate() {
		return reportDate;
	}

	public void setReportDate(Date reportDate) {
		this.reportDate = reportDate;
	}

	public Integer getValid() {
		return valid;
	}

	public void setValid(Integer valid) {
		this.valid = valid;
	}
	
	
}
