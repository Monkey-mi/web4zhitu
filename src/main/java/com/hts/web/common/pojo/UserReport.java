package com.hts.web.common.pojo;

import java.io.Serializable;
import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

/**
 * <p>
 * 用户举报POJO
 * 
 * @author lynch
 *
 */
public class UserReport implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2105586918966605508L;
	private Integer id;
	private Integer userId;
	private Integer reportId;
	private Date reportDate;
	private Integer valid;
	
	public UserReport() {
		super();
	}
	
	public UserReport(Integer id, Integer userId, Integer reportId,
			Date reportDate, Integer valid) {
		super();
		this.id = id;
		this.userId = userId;
		this.reportId = reportId;
		this.reportDate = reportDate;
		this.valid = valid;
	}



	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getReportId() {
		return reportId;
	}

	public void setReportId(Integer reportId) {
		this.reportId = reportId;
	}

	public Integer getValid() {
		return valid;
	}

	public void setValid(Integer valid) {
		this.valid = valid;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public Date getReportDate() {
		return reportDate;
	}

	public void setReportDate(Date reportDate) {
		this.reportDate = reportDate;
	}
	
	

}
