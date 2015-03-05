package com.hts.web.common.pojo;

import java.io.Serializable;
import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

/**
 * <p>
 * App链接点击记录表
 * </p>
 * 
 * 创建时间：2013-11-30
 * 
 * @author ztj
 * 
 */
public class OpAdAppLinkRecord implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6089806282168981634L;
	private Integer id;
	private String recordip;
	private Date recordDate;
	private Integer appId;

	public OpAdAppLinkRecord() {
		super();
	}

	public OpAdAppLinkRecord(Integer id, String recordip, Date recordDate,
			Integer appId) {
		this.id = id;
		this.recordip = recordip;
		this.recordDate = recordDate;
		this.appId = appId;
	}
	
	public OpAdAppLinkRecord(String recordip, Date recordDate, Integer appId) {
		this.recordip = recordip;
		this.recordDate = recordDate;
		this.appId = appId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getRecordip() {
		return recordip;
	}

	public void setRecordip(String recordip) {
		this.recordip = recordip;
	}

	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public Date getRecordDate() {
		return recordDate;
	}

	public void setRecordDate(Date recordDate) {
		this.recordDate = recordDate;
	}

	public Integer getAppId() {
		return appId;
	}

	public void setAppId(Integer appId) {
		this.appId = appId;
	}


}
