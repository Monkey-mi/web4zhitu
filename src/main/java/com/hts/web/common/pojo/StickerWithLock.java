package com.hts.web.common.pojo;

public interface StickerWithLock {

	public Integer getStickerId();

	public Integer getHasLock();
	
	public void setUnlock(Integer unlock);
}
