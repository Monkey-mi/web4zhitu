package com.hts.web.common.util;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * 日期工具类
 * @author ztj
 *
 */
public class DateUtil {

	/**
	 * 将java.util.Date转换为java.sql.Date
	 * 
	 * @param utilDate
	 * @return
	 */
	public static java.sql.Date coverUtilDateToSQLDate(java.util.Date utilDate) {
		return new java.sql.Date(utilDate.getTime());
	}
	
	/**
	 * 获取日期字符串
	 * @return
	 */
	public static String getDateString(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		if(date != null) {
			return format.format(date);
		}
		return null;
	}
	/**
	 * 获取准确日期字符串
	 * 
	 * @param date
	 * @return
	 */
	public static String getExactDateString(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if(date != null) {
			return format.format(date);
		}
		return null;
	}
	
	/**
	 * 获取日期字符串
	 * @param date
	 * @param formatStr
	 * @return
	 */
	public static String getDateStr(Date date, String formatStr) {
		SimpleDateFormat format = new SimpleDateFormat(formatStr);
		if(date != null) {
			return format.format(date);
		}
		return null;
	}
}
