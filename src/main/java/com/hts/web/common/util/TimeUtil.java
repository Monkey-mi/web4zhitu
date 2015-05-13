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
	 * 获取时间长整形数
	 * 
	 * @param date
	 * @return
	 */
	public static long getTimeLONG(Date date) {
		return date.getTime();
	}
	
	/**
	 * 获取日期长整形数
	 * 
	 * @return
	 */
	public static long getTimeLONG() {
		return new Date().getTime();
	}
	
	/**
	 * 获取从当前日期减去指定时间的日期
	 * 
	 * @param nowDate
	 * @param subtime
	 */
	public static Date getSubDateFromNow(long subtime) {
		Date nowDate = new Date();
		long time = nowDate.getTime();
		nowDate.setTime(time - subtime);
		return nowDate;
	}

	/**
	 * 获取从当前日期减去指定时间的日期长整数
	 * 
	 * @param nowDate
	 * @param subtime
	 */
	public static long getSubTimeFromNow(long subtime) {
		Date nowDate = new Date();
		long time = nowDate.getTime();
		return time - subtime;
	}
	
	/**
	 * 根据长整数获取对应的日期对象
	 * 
	 * @param time
	 * @return
	 */
	public static Date getDate(long time) {
		Date d = new Date();
		d.setTime(time);
		return d;
	}
}
