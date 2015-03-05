package com.hts.web.base;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;

import org.apache.struts2.util.StrutsTypeConverter;

/**
 * 该类扩展了Struts2的类型转换功能，完成String与Date之间的转换功能
 *
 */
public class DateTypeConverter extends StrutsTypeConverter {
	private SimpleDateFormat df;
	/**
	 * String转成Date
	 */
	@Override
	public Object convertFromString(Map context, String[] values, Class toClass) {
		String stringValue = values[0].trim();
		if ("".equals(stringValue)) {
			return null;
		}
		try {
			df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			return df.parse(values[0]);
		} catch (ParseException e) {
			try {
				df = new SimpleDateFormat("yyyy-MM-dd");
				return df.parse(values[0]);
			} catch (ParseException ex) {
				ex.printStackTrace();
				return null;
			}
		}
	}

	/**
	 * Date转成String
	 */
	@Override
	public String convertToString(Map context, Object o) {
		df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return df.format(o);
	}
}
