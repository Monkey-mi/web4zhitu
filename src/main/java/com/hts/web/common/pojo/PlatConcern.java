package com.hts.web.common.pojo;

import java.io.Serializable;

/**
 * <p>
 * 社交平台关注POJO，用于强制使用用户社交平台的信息关注指定的社交平台账户
 * </p>
 * 
 * 创建时间:2015-05-17
 * @author lynch
 *
 */
public class PlatConcern implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8569083019710414208L;
	private String uid;
	private String cid;
	private String cname;
	private Integer pid;
	

	public PlatConcern(String uid, String cid, String cname, Integer pid) {
		super();
		this.uid = uid;
		this.cid = cid;
		this.cname = cname;
		this.pid = pid;
	}

	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}
	
	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

}
