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
	private Integer oid;

	public PushWorldIM() {
		super();
	}

	public PushWorldIM(Integer a, String u, String m, Integer uid, Integer tid,
			Integer wid, Integer oid) {
		super(a, u, m, uid, tid);
		this.wid = wid;
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
	
}
