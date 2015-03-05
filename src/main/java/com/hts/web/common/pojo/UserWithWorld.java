package com.hts.web.common.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 用户附加织图标识接口
 * </p>
 * 
 * 创建时间：2014-1-21
 * @author lynch
 *
 */
public interface UserWithWorld extends Serializable {

	/**
	 * 获取用户id
	 * 
	 * @return
	 */
	public Integer getId();
	
	/**
	 * 获取织图信息
	 * 
	 * @return
	 */
	public List<HTWorldThumbUser> getHtworld();

}
