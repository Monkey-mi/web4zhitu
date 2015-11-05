package com.hts.web.common.pojo;

import java.io.Serializable;

/**
 * <p>
 * 织图瀑布流内嵌评论POJO
 * </p>
 * 
 * @author ztj 2014-1-2 2015-11-04
 *
 */
public class HTWorldCommentInline implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7215779811755298106L;
	
	private Integer worldId;
	private String content; // 内容
	private HTWorldCommentInlineUser userInfo;
	
	public HTWorldCommentInline() {
		super();
	}
	
	public HTWorldCommentInline(String content) {
		super();
		this.content = content;
	}

	public Integer getWorldId() {
		return worldId;
	}

	public void setWorldId(Integer worldId) {
		this.worldId = worldId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public HTWorldCommentInlineUser getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(HTWorldCommentInlineUser userInfo) {
		this.userInfo = userInfo;
	}
	
}
