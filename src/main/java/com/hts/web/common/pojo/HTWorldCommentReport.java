package com.hts.web.common.pojo;

import java.io.Serializable;
import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

public class HTWorldCommentReport implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1019130940582231090L;

	private Integer id;
	private Integer userId;
	private Integer commentId;
	private String reportContent;
	private Date reportDate;
	private Integer valid;
	

	public HTWorldCommentReport(Integer userId, Integer commentId,
			String reportContent, Date reportDate, Integer valid) {
		super();
		this.userId = userId;
		this.commentId = commentId;
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

	public Integer getCommentId() {
		return commentId;
	}

	public void setCommentId(Integer commentId) {
		this.commentId = commentId;
	}

	public String getReportContent() {
		return reportContent;
	}

	public void setReportContent(String reportContent) {
		this.reportContent = reportContent;
	}

	@JSON(format = "yyyy-MM-dd HH:mm:ss")
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
