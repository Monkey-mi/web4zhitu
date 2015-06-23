package com.hts.web.common.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.PropertyFilter;

import org.apache.log4j.Logger;

import com.hts.web.base.constant.OptResult;
import com.hts.web.common.pojo.HTWorld;
import com.hts.web.common.pojo.HTWorldChildWorld;
import com.hts.web.common.pojo.HTWorldChildWorldThumb;
import com.hts.web.common.pojo.HTWorldTextStyle;

/**
 * JSON工具类<br />
 * 
 * 创建时间：2012-10-19
 * @author ztj
 *
 */
public class JSONUtil {

	/**
	 * 操作成功
	 * @param msg
	 * @param jsonMap
	 */
	public static void optSuccess(String msg, Map<String,Object> jsonMap) {
		jsonMap.put(OptResult.RESULT, OptResult.OPT_SUCCESS);
		jsonMap.put(OptResult.MSG, msg);
	}
	
	/**
	 * 操作成功
	 * @param msg
	 * @param jsonMap
	 */
	public static void optSuccess(Map<String,Object> jsonMap) {
		jsonMap.put(OptResult.RESULT, OptResult.OPT_SUCCESS);
	}
	
	/**
	 * 查询成功
	 * @param msg
	 * @param jsonMap
	 */
	public static void querySuccess(Object jso, Map<String, Object> jsonMap) {
		jsonMap.put(OptResult.RESULT, OptResult.OPT_SUCCESS);
		jsonMap.put(OptResult.MSG, jso);
	}
	
	/**
	 * 操作失败
	 * @param msg
	 * @param jsonMap
	 */
	public static void optFailed(String msg, Map<String, Object> jsonMap) {
		jsonMap.put(OptResult.RESULT, OptResult.OPT_FAILED);
		jsonMap.put(OptResult.MSG, msg);
	}

	/**
	 * 操作失败
	 * 
	 * @param msg
	 * @param e
	 * @param jsonMap
	 */
	public static void optFailed(Integer uid, String msg, Exception e, Map<String, Object> jsonMap) {
		optFailed(msg, jsonMap);
		Log.warn(msg, e);
	}
	
	
	/**
	 * 操作失败
	 * 
	 * @param resultCode
	 * @param msg
	 * @param jsonMap
	 */
	public static void optFailed(Integer resultCode, String msg, Map<String, Object> jsonMap) {
		jsonMap.put(OptResult.RESULT, resultCode);
		jsonMap.put(OptResult.MSG, msg);
	}
	
	/**
	 * 操作失败
	 * 
	 * @param resultCode
	 * @param msg
	 * @param e
	 * @param jsonMap
	 */
	public static void optFailed(Integer uid, Integer resultCode, String msg,
			Exception e, Map<String, Object> jsonMap) {
		optFailed(resultCode, msg, jsonMap);
		Log.warn(msg, e);
	}
	
	/**
	 * 操作失败2,自定义logger
	 * 
	 * @param uid
	 * @param resultCode
	 * @param e
	 * @param jsonMap
	 * @param logger
	 */
	public static void optFailed2(Integer uid, Integer resultCode,
			Exception e, Map<String, Object> jsonMap, Logger logger) {
		optFailed(resultCode, e.getMessage(), jsonMap);
		logger.warn(e.getMessage(), e);
	}
	
	/**
	 * 操作失败2,自定义logger
	 * 
	 * @param uid
	 * @param e
	 * @param jsonMap
	 * @param logger
	 */
	public static void optFailed2(Integer uid, Exception e, Map<String, Object> jsonMap, Logger logger) {
		optFailed(e.getMessage(), jsonMap);
		logger.warn(e.getMessage(), e);
	}
	
	
	/**
	 * 查询列表成功
	 * @param jsonMap
	 * @param total
	 * @param rows
	 */
	public static void queryListSuccess(Map<String, Object> jsonMap, Integer total, List rows) {
		jsonMap.put(OptResult.RESULT, OptResult.OPT_SUCCESS);
		jsonMap.put(OptResult.TOTAL, total);
		jsonMap.put(OptResult.ROWS, rows);
	}
	
	/**
	 * 检测的信息存在
	 * @param jsonMap
	 */
	public static void checkExist(Map<String, Object> jsonMap) {
		jsonMap.put(OptResult.RESULT, OptResult.OPT_SUCCESS);
		jsonMap.put(OptResult.MSG, OptResult.CHECK_EXIST);
	}
	
	/**
	 * 返回操作结果
	 * @param resultCode
	 * @param resultMsg
	 * @param jsonMap
	 */
	public static void optResult(int resultCode, Object resultMsg, Map<String,Object> jsonMap) {
		jsonMap.put(OptResult.RESULT, resultCode);
		jsonMap.put(OptResult.MSG, resultMsg);
	}

	/**
	 * 返回操作结果
	 * @param resultCode
	 * @param resultMsg
	 * @param resultKey
	 * @param jsonMap
	 */
	public static void optResult(int resultCode, Object resultMsg, String resultKey, Map<String,Object> jsonMap) {
		jsonMap.put(OptResult.RESULT, resultCode);
		jsonMap.put(resultKey, resultMsg);
	}
	
	/**
	 * 检测的信息不存在
	 * @param jsonMap
	 */
	public static void checkNotExist(Map<String, Object> jsonMap) {
		jsonMap.put(OptResult.RESULT, OptResult.OPT_SUCCESS);
		jsonMap.put(OptResult.MSG, OptResult.CHECK_NOT_EXIST);
	}
	
	/**
	 * 获取汇图世界信息
	 * @param json
	 * @return
	 */
	public static HTWorld getHTWorld(String json) {
		HTWorld htWorld = new HTWorld();
		JSONUtil.toBean(json, htWorld, new String[]{"id","title","childs","titleThumbPath","latitude","longitude"});
		return htWorld;
	}
	
	/**
	 * 
	 * @param json
	 * @return
	 */
	public static Object[] getChildWorldInfo2(String json) {
		Object[] worldInfo = new Object[2];
		Map<Integer,HTWorldChildWorldThumb> thumbsAtIdMap = new HashMap<Integer, HTWorldChildWorldThumb>(); 
		Map<Integer,HTWorldChildWorld> childWorldMap = new HashMap<Integer,HTWorldChildWorld>();
		Map<Integer,HTWorldChildWorldThumb> thumbsMap = new HashMap<Integer, HTWorldChildWorldThumb>();
		worldInfo[0] = childWorldMap;
		worldInfo[1] = thumbsMap;
		
		Object childWorlds = JSONUtil.getProperty("childs", json);
		Object thumbs = JSONUtil.getProperty("thumbs", json);
		JSONArray childArray = JSONArray.fromObject(childWorlds);
		JSONArray thumbArray = JSONArray.fromObject(thumbs);
		for(int i = 0; i < thumbArray.size(); i++) {
			HTWorldChildWorldThumb thumb = (HTWorldChildWorldThumb) JSONUtil.toBean(thumbArray.get(i), HTWorldChildWorldThumb.class);
			thumbsMap.put(thumb.getToId(), thumb);
			thumbsAtIdMap.put(thumb.getAtId(), thumb);
		}
		
		for(int i = 0 ; i < childArray.size(); i++) {
			HTWorldChildWorld childWorld = (HTWorldChildWorld) JSONUtil.toBean(childArray.get(i), HTWorldChildWorld.class);
		}
		
		
		return worldInfo;
	}
	
	/**
	 * 获取子世界信息
	 * @param json
	 * @return
	 */
	public static Object[] getChildWorldInfo(String json) {
		Object childWorlds = JSONUtil.getProperty("childs", json);
		JSONObject childsObject = JSONObject.fromObject(childWorlds);
		return covertChildWorldInfoFromJSONObj(childsObject);
	}
	
	/**
	 * 将JSON字符串转换为子世界信息
	 * @param childWorldsStr
	 * @return
	 */
	public static Object[] coverChildWorldInfoFromString(String childWorldsStr) {
		JSONObject childsObject = JSONObject.fromObject(childWorldsStr);
		return covertChildWorldInfoFromJSONObj(childsObject);
	}
	
	/**
	 * 将JSONObject转换为子世界信息
	 * @param childsObj
	 * @return
	 */
	public static Object[] covertChildWorldInfoFromJSONObj(JSONObject childsObject) {
		Object[] worldInfo = new Object[2];
		Map<Integer,HTWorldChildWorld> childWorldMap = new HashMap<Integer,HTWorldChildWorld>();
		Map<Integer,HTWorldChildWorldThumb> thumbsMap = new HashMap<Integer, HTWorldChildWorldThumb>();
		worldInfo[0] = childWorldMap;
		worldInfo[1] = thumbsMap;
		
		Iterator<String> it = childsObject.keys();
		while(it.hasNext()) {
			String key = it.next();
			Integer childId = Integer.parseInt(key);
			JSONArray childArray = childsObject.getJSONArray(key.toString());
			HTWorldChildWorld childWorld = (HTWorldChildWorld) JSONUtil.toBean(childArray.get(0), HTWorldChildWorld.class);
			childWorld.setIsTitle(0);
			childWorldMap.put(childId, childWorld);
			
			//遍历保存子世界缩略图
			for(int i = 1; i < childArray.size(); i++) {
				HTWorldChildWorldThumb thumb = (HTWorldChildWorldThumb) JSONUtil.toBean(childArray.get(i), HTWorldChildWorldThumb.class);
				childWorld.addThumb(thumb); //向子世界中添加其缩略图信息
				thumbsMap.put(thumb.getToId(), thumb);
			}
		}
		return worldInfo;
	}
	
	/**
	 * 获取属性
	 * @param key
	 * @param json
	 * @return
	 */
	public static Object getProperty(String key, String json) {
		JSONObject jsonObject = JSONObject.fromObject(json);
		Object obj = jsonObject.get(key);
		return obj;
	}
	
	/**
	 * 将JSON对象转化成JavaBean
	 * @param obj
	 * @param beanObj
	 */
	public static Object toBean(Object obj, Class beanClass) {
		JSONObject jsonObj = JSONObject.fromObject(obj);
		Object beanObj = JSONObject.toBean(jsonObj, beanClass);
		return beanObj;
	}
	
	/**
	 * 将JSON对象转化成JavaBean
	 * @param obj
	 * @param beanObj
	 * @param ignoreKeies 不需要转换的属性
	 * @return
	 */
	public static void toBean(Object obj, Object beanObj, final String[] ignoreKeies) {
		JSONObject jsonObj = JSONObject.fromObject(obj);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setRootClass(beanObj.getClass());
		jsonConfig.setJavaPropertyFilter( new PropertyFilter(){  
			   public boolean apply( Object source, String name, Object value ) {  
				  for(String s : ignoreKeies) {
					  if(s.equals(name)) {
						  return true;
					  }
				  }
			      return false;  
			   }  
			});  
		JSONObject.toBean(jsonObj,beanObj, jsonConfig);
	}
	
	/**
	 * 根据文字获取JSONObject
	 * @param text
	 * @return
	 */
	public static HTWorldTextStyle getJSObjectFromText(String text) {
		HTWorldTextStyle style = null;
		if(text != null) {
			style = new HTWorldTextStyle();
			JSONObject jsobj = JSONObject.fromObject(text);
			style.setColor(jsobj.optString("color"));
			style.setMask(jsobj.optInt("mask"));
		}
		return style;
	}
	
}
