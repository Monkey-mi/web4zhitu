package com.hts.web.common.pojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lynch
 *
 */
public class HTWorldChildWorldAndThumbDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private HTWorldChildWorldDto child;
	private List<HTWorldChildWorldThumb> thumbs = new ArrayList<HTWorldChildWorldThumb>();

	
	public HTWorldChildWorldAndThumbDto() {
		super();
	}

	public HTWorldChildWorldAndThumbDto(HTWorldChildWorldDto child) {
		super();
		this.child = child;
	}

	public HTWorldChildWorldDto getChild() {
		return child;
	}

	public void setChild(HTWorldChildWorldDto child) {
		this.child = child;
	}

	public List<HTWorldChildWorldThumb> getThumbs() {
		return thumbs;
	}

	public void setThumbs(List<HTWorldChildWorldThumb> thumbs) {
		this.thumbs = thumbs;
	}
	
	public void addThumb(HTWorldChildWorldThumb thumb) {
		this.thumbs.add(thumb);
	}
	

}
