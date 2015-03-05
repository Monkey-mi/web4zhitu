package com.hts.web.common.pojo;

import java.io.Serializable;

import com.hts.web.base.constant.Tag;

public class HTWorldFilterLogo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1256894072302062433L;
	
	private Float ver;
	private String logoPath;
	private String logoDesc;
	private Integer valid = Tag.TRUE;
	
	
	@Override
	public String toString() {
		return super.toString();
	}

	public HTWorldFilterLogo() {
		super();
	}

	public HTWorldFilterLogo(Float ver, String logoPath, String logoDesc,
			Integer valid) {
		super();
		this.ver = ver;
		this.logoPath = logoPath;
		this.logoDesc = logoDesc;
		this.valid = valid;
	}

	public Float getVer() {
		return ver;
	}
	
	public void setVer(Float ver) {
		this.ver = ver;
	}
	
	public String getLogoPath() {
		return logoPath;
	}
	
	public void setLogoPath(String logoPath) {
		this.logoPath = logoPath;
	}
	
	public String getLogoDesc() {
		return logoDesc;
	}
	
	public void setLogoDesc(String logoDesc) {
		this.logoDesc = logoDesc;
	}
	public Integer getValid() {
		return valid;
	}
	
	public void setValid(Integer valid) {
		this.valid = valid;
	}
	
}
