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
}
