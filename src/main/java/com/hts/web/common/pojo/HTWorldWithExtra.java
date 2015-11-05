package com.hts.web.common.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 织图附加信息（评论，喜欢列表）接口
 * </p>
 * 
 * 创建时间：2014-1-21
 * @author lynch
 *
 */
public interface HTWorldWithExtra extends Serializable {

	
	/**
	 * 获取织图id
	 * 
	 * @return
	 */
	public Integer getId();
	/**
	 * 获取喜欢容器
	 * 
	 * @return
	 */
	public List<HTWorldLikedInline> getLikes();

	/**
	 * 获取评论容器
	 * 
	 * @return
	 */
	public List<HTWorldCommentInline> getComments();
	
	/**
	 * 设置喜欢标记
	 * 
	 * @param liked
	 */
	public void setLiked(Object liked);
	
	/**
	 * 设置收藏标记
	 * 
	 * @param keep
	 */
	public void setKeep(Object keep);
}
