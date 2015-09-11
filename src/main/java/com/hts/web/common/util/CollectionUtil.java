package com.hts.web.common.util;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.PropertyUtilsBean;

/**
 * 集合工具类<br />
 * 
 * 创建时间：2012-10-19
 * @author ztj
 *
 */
public class CollectionUtil {

	/**
	 * 将JavaBean对象转成Map<String, Object>
	 * @param obj
	 * @return
	 */
	public static Map<String, Object> changeToMap(Object obj) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			PropertyUtilsBean propertyUtilsBean = new PropertyUtilsBean();
			PropertyDescriptor[] descriptors = propertyUtilsBean.getPropertyDescriptors(obj);
			for (PropertyDescriptor descriptor : descriptors) {
				String name = descriptor.getName();
				if(!name.equals("class")) {
					map.put(name, propertyUtilsBean.getNestedProperty(obj, name));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
	/**
	 * 收集Map的值
	 * 
	 * @param map
	 * @return
	 */
	public static void collectMapValues(List<Object> collectList, Map<String, Object> map) {
		Set<String> keies = map.keySet();
		Iterator<String> it = keies.iterator();
		while(it.hasNext()) {
			String key = it.next();
			collectList.add(map.get(key));
		}
	}
	
}
