package com.hts.web.common.pojo;

import java.io.Serializable;

/**
 * 织图瀑布流内嵌评论用户信息
 * 
 * @author lynch　2015-11-04
 *
 */
public class HTWorldCommentInlineUser implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2738057877070757049L;
	
	private String userName;
	
	

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
}
