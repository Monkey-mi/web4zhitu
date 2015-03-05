package com.hts.web.common.pojo;

public class PushSysIM extends PushIM {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2845917884719198759L;
	private Integer sid;

	public PushSysIM() {
		super();
	}


	public PushSysIM(Integer a, String u, String m, Integer uid, Integer tid, Integer sid) {
		super(a, u, m, uid, tid);
		this.sid = sid;
	}

	public Integer getSid() {
		return sid;
	}

	public void setSid(Integer sid) {
		this.sid = sid;
	}
	
	
	
}
