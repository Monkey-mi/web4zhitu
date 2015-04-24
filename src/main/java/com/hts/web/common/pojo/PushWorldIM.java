package com.hts.web.common.pojo;

/**
 * <p>
 * 织图互动相关IM消息
 * </p>
 * 
 * 创建时间：2014-09-12
 * @author tianjie
 *
 */
public class PushWorldIM extends PushIM {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4821371907419382577L;

	private Integer wid;
	private Integer waid;
	private Integer reid;
	private Integer oid;

	public PushWorldIM() {
		super();
	}

	public PushWorldIM(Integer a, String u, String m, Integer uid, Integer tid,
			Integer wid, Integer waid, Integer reid, Integer oid) {
		super(a, u, m, uid, tid);
		this.wid = wid;
		this.waid = waid;
		this.reid = reid;
		this.oid = oid;
	}

	public Integer getWid() {
		return wid;
	}

	public void setWid(Integer wid) {
		this.wid = wid;
	}

	public Integer getOid() {
		return oid;
	}

	public void setOid(Integer oid) {
		this.oid = oid;
	}

	public Integer getWaid() {
		return waid;
	}

	public void setWaid(Integer waid) {
		this.waid = waid;
	}

	public Integer getReid() {
		return reid;
	}

	public void setReid(Integer reid) {
		this.reid = reid;
	}
	
	
	
}
