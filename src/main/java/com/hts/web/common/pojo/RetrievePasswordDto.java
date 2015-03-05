package com.hts.web.common.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * 找回密码dto
 * @author zxx
 *
 */

public class RetrievePasswordDto implements Serializable{
	private static final long serialVersionUID = -830085173128035870L;
	private String login_code;//用户id
	private byte[] sid;		//服务id
	private Date end_time;	//服务id截止时间	
	
	public void setLogin_code(String login_code){
		this.login_code =login_code;
	}
	public String getLogin_code(){
		return this.login_code;
	}
	
	public void setSid(byte[] sid){
		this.sid = sid;
	}
	public byte[] getSid(){
		return this.sid;
	}
	
	public void setEnd_time(Date end_time){
		this.end_time =end_time;
	}
	public Date getEnd_time(){
		return this.end_time;
	}
	
	public RetrievePasswordDto(){
		super();
	}
	
	public RetrievePasswordDto(String login_code, byte[] sid, Date end_time){
		super();
		this.login_code =login_code;
		this.sid = sid;
		this.end_time = end_time;
	}
	
}
