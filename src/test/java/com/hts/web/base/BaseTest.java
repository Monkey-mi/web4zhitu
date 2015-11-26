package com.hts.web.base;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.hts.web.common.util.Log;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * <p>
 * 测试基础类
 * </p>
 * 
 * 创建时间：2013-8-3
 * @author ztj
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/spring-config.xml"}) 
public abstract class BaseTest {

	/**
	 * 将对象转换为JSON并logdebug输出
	 * @param obj
	 */
	protected static void logObj(Object obj) {
		JSONObject jsObj = JSONObject.fromObject(obj);
		Log.debug(jsObj);
	}
	
	protected static void logList(List<? extends Serializable> list) {
		JSONArray arr = JSONArray.fromObject(list);
		Log.debug(arr);
	}
	
	protected void logNumberList(Logger logger, NumberListAdapter adapter) throws Exception {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		adapter.buildNumberList(jsonMap);
		JSONObject jsObj = JSONObject.fromObject(jsonMap);
		logger.debug(jsObj);
	}
	
	protected interface NumberListAdapter {
		
		void buildNumberList(Map<String, Object> jsonMap) throws Exception;
		
	}
	
	
}
