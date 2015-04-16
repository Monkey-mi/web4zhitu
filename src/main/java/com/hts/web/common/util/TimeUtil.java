package com.hts.web.common.util;

import java.util.Calendar;
import java.util.Date;

public class TimeUtil {

	/**
	 * 获取格林威治时间戳
	 * 
	 * @param date
	 * @return
	 */
	public static long getGMTTimestamp() {
		Calendar c = Calendar.getInstance();
		long ts = c.getTimeInMillis();
		return ts - c.getTimeZone().getOffset(ts);
	}
	
	/**
	 * 获取格林威治时间
	 * 
	 * @return
	 */
	public static Date getGMTTime() {
		return new Date(getGMTTimestamp());
	}
	
	/**
	 * 获取日期整形数
	 * 
	 * @return
	 */
	public static int getTimeINT() {
		return (int)(new Date().getTime() / 1000);
	}
	
	/**
	 * 获取时间整形数
	 * 
	 * @param date
	 * @return
	 */
	public static int getTimeINT(Date date) {
		return (int)(date.getTime() / 1000);
	}
	
	/**
	 * 获取日期长整形数
	 * 
	 * @return
	 */
	public static long getTimeLONG() {
		return new Date().getTime();
	}
}
