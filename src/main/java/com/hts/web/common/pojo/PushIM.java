package com.hts.web.common.pojo;

import java.io.Serializable;
import java.util.Date;

public class PushIM implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1552522904324143847L;
	
	protected Integer a; // 消息动作类型
	protected String u; // 用户名
	protected String m; // 消息主体
	protected Integer r; // 接受者与发送者的关系
	protected Integer uid; // 发送者id
	protected Integer tid; // 接受者id
	protected Long t0 = new Date().getTime() / 1000; // 发送GMT时间戳

	public PushIM() {
		super();
	}

	/**
	 * @param a 消息动作类型
	 * @param u 用户名
	 * @param m 消息主体
	 * @param uid 发送者id
	 */
	public PushIM(Integer a, String u, String m, Integer uid, Integer tid) {
		super();
		this.a = a;
		this.u = u;
		this.m = m;
		this.uid = uid;
		this.tid = tid;
	}

	public Integer getA() {
		return a;
	}

	public void setA(Integer a) {
		this.a = a;
	}

	public String getU() {
		return u;
	}

	public void setU(String u) {
		this.u = u;
	}

	public String getM() {
		return m;
	}

	public void setM(String m) {
		this.m = m;
	}

	public Integer getR() {
		return r;
	}

	public void setR(Integer r) {
		this.r = r;
	}

	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}
	
	public Integer getTid() {
		return tid;
	}

	public void setTid(Integer tid) {
		this.tid = tid;
	}

	public Long getT0() {
		return t0;
	}

	public void setT0(Long t0) {
		this.t0 = t0;
	}
	
}
