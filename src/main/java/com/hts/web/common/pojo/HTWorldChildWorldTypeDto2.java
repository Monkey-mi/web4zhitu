package com.hts.web.common.pojo;

import java.io.Serializable;

/**
 * <p>
 * 子世界类型数据数据传输对象2，封装id和useCount字段
 * </p>
 * 
 * 创建时间：2014-6-13
 * 
 * @author tianjie
 * 
 */
public class HTWorldChildWorldTypeDto2 implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6333358942415845585L;

	private Integer id;
	private Integer useCount;

	public HTWorldChildWorldTypeDto2() {
		super();
	}

	public HTWorldChildWorldTypeDto2(Integer id, Integer useCount) {
		super();
		this.id = id;
		this.useCount = useCount;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUseCount() {
		return useCount;
	}

	public void setUseCount(Integer useCount) {
		this.useCount = useCount;
	}
	
}
