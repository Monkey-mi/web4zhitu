package com.hts.web.common.pojo;

import java.io.Serializable;

public class HTWorldLatestIndex implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6071328460473423847L;
	private Integer interval;
	private Long total;

	public HTWorldLatestIndex(Integer interval, Long total) {
		super();
		this.interval = interval;
		this.total = total;
	}

	public Integer getInterval() {
		return interval;
	}

	public void setInterval(Integer interval) {
		this.interval = interval;
	}

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

}
