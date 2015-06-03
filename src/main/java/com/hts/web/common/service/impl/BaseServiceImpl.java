package com.hts.web.common.service.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.hts.web.base.constant.OptResult;
import com.hts.web.base.database.RowSelection;
import com.hts.web.common.BaseOnBuildSerializableListener;
import com.hts.web.common.BaseSerializableListAdapter;
import com.hts.web.common.OnBuildSerializableListener;
import com.hts.web.common.OnBuildSinceSerializableListener;
import com.hts.web.common.SerializableListAdapter;
import com.hts.web.common.SerializableSinceIdListAdapter;
import com.hts.web.common.pojo.AbstractNumberDto;
import com.hts.web.common.service.BaseService;

/**
 * <p>
 * 业务逻辑基础类
 * </p>
 * 
 * 创建时间：2012-10-18
 * @author ztj
 * 
 */
public class BaseServiceImpl implements BaseService{
	
	/**
	 * 错误代码：重复操作
	 */
	public static final int ERROR_CODE_REPEAT_OPT = 1;
	
	/**
	 * 构建序列化列表
	 * 
	 * @param getIdMethod
	 * @param maxSerializable
	 * @param start
	 * @param limit
	 * @param jsonMap
	 * @param listAdapter
	 * @param serialKey
	 * @param totalKey
	 * @param listener
	 * @return
	 * @throws Exception
	 */
	public Serializable buildSerializables(String getIdMethod, Serializable maxSerializable,
			Integer start, Integer limit, Map<String, Object> jsonMap,
			BaseSerializableListAdapter<? extends Serializable> listAdapter,
			String serialKey, String totalKey, BaseOnBuildSerializableListener listener) throws Exception {
		List<? extends Serializable> list = null;
		long totalCount = 0l;
		boolean hasTotal = totalKey != null ? true : false;
		RowSelection rowSelection = new RowSelection(start, limit);
		if(maxSerializable == null) { // 默认查询
			list = listAdapter.getSerializables(rowSelection);
			if(list.size() > 0) {
				Serializable ser = list.get(0);
				maxSerializable = (Serializable) ser.getClass().getMethod(getIdMethod).invoke(ser);
				if(hasTotal) {totalCount = listAdapter.getTotalByMaxId(maxSerializable);}
				if(listener != null) {
					listener.onBuild(list,maxSerializable, totalCount);
				}
			}
		} else { // 查询比指定时间早的记录
			list = listAdapter.getSerializableByMaxId(maxSerializable, rowSelection);
			if(hasTotal) {totalCount = listAdapter.getTotalByMaxId(maxSerializable);}
			if(listener != null) {
				listener.onBuildByMaxId(list,maxSerializable, totalCount);
			}
		}
		
		jsonMap.put(serialKey, list);
		if(hasTotal) {jsonMap.put(totalKey, totalCount);}
		return maxSerializable;
	}

	/**
	 * 构建序列化列表
	 * 
	 * @param getIdMethod
	 * @param maxSerializable
	 * @param start
	 * @param limit
	 * @param jsonMap
	 * @param listAdapter
	 * @param serialKey
	 * @param totalKey
	 * @return
	 * @throws Exception
	 */
	public Serializable buildSerializables(String getIdMethod, Serializable maxSerializable,
			Integer start, Integer limit, Map<String, Object> jsonMap,
			BaseSerializableListAdapter<? extends Serializable> listAdapter,
			String serialKey, String totalKey) throws Exception {	
		return buildSerializables(getIdMethod, maxSerializable, start, limit,jsonMap,listAdapter,
				serialKey, totalKey, new BaseOnBuildSerializableListener(){
					@Override
					public void onBuild(List<? extends Serializable> list, Serializable maxSerializable, long total) {}
					@Override
					public void onBuildByMaxId(List<? extends Serializable> list, Serializable maxSerializable, long total) {}
		});
	}

	/**
	 * 构建序列化列表
	 * 
	 * @param getIdMethod
	 * @param maxSerializable
	 * @param start
	 * @param limit
	 * @param jsonMap
	 * @param listAdapter
	 * @param serialKey
	 * @param totalKey
	 * @param maxSerKey
	 * @param listener
	 * @throws Exception
	 */
	public void buildSerializables(String getIdMethod, Serializable maxSerializable,
			Integer start, Integer limit, Map<String, Object> jsonMap,
			BaseSerializableListAdapter<? extends Serializable> listAdapter,
			String serialKey, String totalKey, String maxSerKey, 
			BaseOnBuildSerializableListener listener) throws Exception {
		jsonMap.put(maxSerKey, buildSerializables(getIdMethod, maxSerializable, start, limit, 
				jsonMap, listAdapter, serialKey, totalKey, listener));
	}

	/**
	 * 构建序列化列表
	 * 
	 * @param getIdMethod
	 * @param maxSerializable
	 * @param start
	 * @param limit
	 * @param jsonMap
	 * @param listAdapter
	 * @param serialKey
	 * @param totalKey
	 * @param maxSerKey
	 * @throws Exception
	 */
	public void buildSerializables(String getIdMethod, Serializable maxSerializable,
			Integer start, Integer limit, Map<String, Object> jsonMap,
			BaseSerializableListAdapter<? extends Serializable> listAdapter,
			String serialKey, String totalKey, String maxSerKey) throws Exception {	
		jsonMap.put(maxSerKey, buildSerializables(getIdMethod, maxSerializable, start, limit,jsonMap,listAdapter,
				serialKey, totalKey, new BaseOnBuildSerializableListener(){
					@Override
					public void onBuild(List<? extends Serializable> list, Serializable maxSerializable, long total) {}
					@Override
					public void onBuildByMaxId(List<? extends Serializable> list, Serializable maxSerializable, long total) {}
		}));
	}

	/**
	 * 构建序列化列表
	 * 
	 * @param maxSerializable
	 * @param start
	 * @param limit
	 * @param jsonMap
	 * @param listAdapter
	 * @param serialKey
	 * @param totalKey
	 * @param listener
	 * @return
	 * @throws Exception
	 */
	public Serializable buildSerializables(Serializable maxSerializable, Integer start, Integer limit,
			Map<String, Object> jsonMap, BaseSerializableListAdapter<? extends Serializable> listAdapter, 
			String serialKey, String totalKey, BaseOnBuildSerializableListener listener) throws Exception {
		return buildSerializables("getId", maxSerializable, start,  limit, jsonMap,
				listAdapter, serialKey, totalKey,listener);
	}

	/**
	 * 构建序列化列表
	 * 
	 * @param maxSerializable
	 * @param start
	 * @param limit
	 * @param jsonMap
	 * @param listAdapter
	 * @param serialKey
	 * @param totalKey
	 * @return
	 * @throws Exception
	 */
	public Serializable buildSerializables(Serializable maxSerializable, Integer start, Integer limit,
			Map<String, Object> jsonMap, BaseSerializableListAdapter<? extends Serializable> listAdapter,
			String serialKey, String totalKey) throws Exception {
		return buildSerializables("getId", maxSerializable, start, limit, jsonMap, listAdapter, serialKey, totalKey, 
				new BaseOnBuildSerializableListener(){
			@Override
			public void onBuild(List<? extends Serializable> list, Serializable maxSerializable, long total) {}
			@Override
			public void onBuildByMaxId(List<? extends Serializable> list, Serializable maxSerializable, long total) {}
		});
	}

	/**
	 * 构建序列化列表
	 * 
	 * @param maxSerializable
	 * @param start
	 * @param limit
	 * @param jsonMap
	 * @param listAdapter
	 * @param serialKey
	 * @param totalKey
	 * @param maxSerKey
	 * @param listener
	 * @throws Exception
	 */
	public void buildSerializables(Serializable maxSerializable, Integer start, Integer limit,
			Map<String, Object> jsonMap, BaseSerializableListAdapter<? extends Serializable> listAdapter, 
			String serialKey, String totalKey, String maxSerKey,
			BaseOnBuildSerializableListener listener) throws Exception {
		jsonMap.put(maxSerKey, buildSerializables("getId", maxSerializable, start,  limit, jsonMap,
				listAdapter, serialKey, totalKey,listener));
	}

	/**
	 * 构建序列化列表
	 * 
	 * @param maxSerializable
	 * @param start
	 * @param limit
	 * @param jsonMap
	 * @param listAdapter
	 * @param serialKey
	 * @param totalKey
	 * @param maxSerKey
	 * @throws Exception
	 */
	public void buildSerializables(Serializable maxSerializable, Integer start, Integer limit,
			Map<String, Object> jsonMap, BaseSerializableListAdapter<? extends Serializable> listAdapter,
			String serialKey, String totalKey, String maxSerKey) throws Exception {
		jsonMap.put(maxSerKey, buildSerializables("getId", maxSerializable, start,  limit, jsonMap,
				listAdapter, serialKey, totalKey));
	}

	/**
	 * 构建序列化列表
	 * 
	 * @param getIdMethod
	 * @param maxId
	 * @param start
	 * @param limit
	 * @param jsonMap
	 * @param listAdapter
	 * @param serialKey
	 * @param totalKey
	 * @param listener
	 * @return
	 * @throws Exception
	 */
	public Integer buildSerializables(String getIdMethod, Integer maxId,
			Integer start, Integer limit, Map<String, Object> jsonMap,
			SerializableListAdapter<? extends Serializable> listAdapter,
			String serialKey, String totalKey, OnBuildSerializableListener listener) throws Exception {
		List<? extends Serializable> list = null;
		long totalCount = 0l;
		boolean hasTotal = totalKey != null ? true : false;
		RowSelection rowSelection = new RowSelection(start, limit);
		if(maxId <= 0) { // 默认查询
			list = listAdapter.getSerializables(rowSelection);
			if(list.size() > 0) {
				Serializable ser = list.get(0);
				maxId = (Integer)ser.getClass().getMethod(getIdMethod).invoke(ser);
				if(hasTotal) {totalCount = listAdapter.getTotalByMaxId(maxId);}
				if(listener != null) {
					listener.onBuild(list,maxId, totalCount);
				}
			}
			
		} else if(maxId > 0) { // 查询比指定时间早的记录
			list = listAdapter.getSerializableByMaxId(maxId, rowSelection);
			if(hasTotal) {totalCount = listAdapter.getTotalByMaxId(maxId);}
			if(listener != null) {
				listener.onBuildByMaxId(list,maxId, totalCount);
			}
		}
		
		jsonMap.put(serialKey, list);
		if(hasTotal) {jsonMap.put(totalKey, totalCount);}
		return maxId;
	}
	

	/**
	 * 构建序列化列表
	 * 
	 * @param maxId
	 * @param start
	 * @param limit
	 * @param jsonMap
	 * @param listAdapter
	 * @param serialKey
	 * @param totalKey
	 * @param listener
	 * @return
	 * @throws Exception
	 */
	public Integer buildSerializables(Integer maxId, Integer start, Integer limit,
			Map<String, Object> jsonMap, SerializableListAdapter<? extends Serializable> listAdapter, 
			String serialKey, String totalKey, OnBuildSerializableListener listener) throws Exception {
		return buildSerializables("getId", maxId, start,  limit, jsonMap,
				listAdapter, serialKey, totalKey,listener);
	}

	/**
	 * 构建序列化列表
	 * 
	 * @param getIdMethod
	 * @param maxId
	 * @param start
	 * @param limit
	 * @param jsonMap
	 * @param listAdapter
	 * @param serialKey
	 * @param totalKey
	 * @param maxIdKey
	 * @param listener
	 * @throws Exception
	 */
	public void buildSerializables(String getIdMethod, Integer maxId, Integer start, Integer limit,
			Map<String, Object> jsonMap, SerializableListAdapter<? extends Serializable> listAdapter,
			String serialKey, String totalKey, String maxIdKey, OnBuildSerializableListener listener) throws Exception {
		jsonMap.put(maxIdKey,buildSerializables(getIdMethod, maxId, start, limit, jsonMap, listAdapter, serialKey, totalKey,listener));
	}

	/**
	 * 构建序列化列表
	 * 
	 * @param maxId
	 * @param start
	 * @param limit
	 * @param jsonMap
	 * @param listAdapter
	 * @param serialKey
	 * @param totalKey
	 * @param maxIdKey
	 * @param listener
	 * @throws Exception
	 */
	public void buildSerializables(Integer maxId, Integer start, Integer limit,
			Map<String, Object> jsonMap, SerializableListAdapter<? extends Serializable> listAdapter,
			String serialKey, String totalKey, String maxIdKey, OnBuildSerializableListener listener) throws Exception {
		jsonMap.put(maxIdKey,buildSerializables(maxId, start, limit, jsonMap, listAdapter, serialKey, totalKey,listener));
	}

	/**
	 * 构建序列化列表
	 * 
	 * @param getIdMethod
	 * @param maxId
	 * @param start
	 * @param limit
	 * @param jsonMap
	 * @param listAdapter
	 * @param serialKey
	 * @param totalKey
	 * @return
	 * @throws Exception
	 */
	public Integer buildSerializables(String getIdMethod, Integer maxId,
			Integer start, Integer limit, Map<String, Object> jsonMap,
			SerializableListAdapter<? extends Serializable> listAdapter,
			String serialKey, String totalKey) throws Exception {	
		return buildSerializables(getIdMethod, maxId, start, limit,jsonMap,listAdapter,
				serialKey, totalKey, new OnBuildSerializableListener(){
					@Override
					public void onBuild(List<? extends Serializable> list, int maxId, long total) {}
					@Override
					public void onBuildByMaxId(List<? extends Serializable> list, int maxId, long total) {}
		});
	}

	/**
	 * 构建序列化列表
	 * 
	 * @param maxId
	 * @param start
	 * @param limit
	 * @param jsonMap
	 * @param listAdapter
	 * @param serialKey
	 * @param totalKey
	 * @return
	 * @throws Exception
	 */
	public Integer buildSerializables(Integer maxId, Integer start,
			Integer limit, Map<String, Object> jsonMap,
			SerializableListAdapter<? extends Serializable> listAdapter,
			String serialKey, String totalKey) throws Exception {
		return buildSerializables("getId", maxId,start, limit,jsonMap,listAdapter,serialKey, totalKey);
	}

	/**
	 * 构建序列化列表
	 * 
	 * @param getIdMethod
	 * @param maxId
	 * @param start
	 * @param limit
	 * @param jsonMap
	 * @param listAdapter
	 * @param serialKey
	 * @param totalKey
	 * @param maxIdKey
	 * @throws Exception
	 */
	public void buildSerializables(String getIdMethod, Integer maxId,
			Integer start, Integer limit, Map<String, Object> jsonMap,
			SerializableListAdapter<? extends Serializable> listAdapter,
			String serialKey, String totalKey, String maxIdKey)
			throws Exception {
		buildSerializables(getIdMethod, maxId, start, limit,jsonMap,listAdapter,
				serialKey, totalKey, maxIdKey, new OnBuildSerializableListener(){
					@Override
					public void onBuild(List<? extends Serializable> list, int maxId, long total) {}
					@Override
					public void onBuildByMaxId(List<? extends Serializable> list,int maxId, long total) {}
		});
	}

	/**
	 * 构建序列化列表
	 * 
	 * @param maxId
	 * @param start
	 * @param limit
	 * @param jsonMap
	 * @param listAdapter
	 * @param serialKey
	 * @param totalKey
	 * @param maxIdKey
	 * @throws Exception
	 */
	public void buildSerializables(Integer maxId, Integer start, Integer limit,
			Map<String, Object> jsonMap,
			SerializableListAdapter<? extends Serializable> listAdapter,
			String serialKey, String totalKey, String maxIdKey)
			throws Exception {
		buildSerializables("getId", maxId, start, limit,jsonMap,listAdapter,
				serialKey, totalKey, maxIdKey);
	}

	/**
	 * 构建序列化列表
	 * 
	 * @param getIdMethod
	 * @param sinceId
	 * @param maxId
	 * @param start
	 * @param limit
	 * @param jsonMap
	 * @param listAdapter
	 * @param serialKey
	 * @param totalKey
	 * @param listener
	 * @return
	 * @throws Exception
	 */
	public Integer buildSerializables(String getIdMethod, int sinceId, int maxId, int start,
			int limit, Map<String, Object> jsonMap,
			SerializableSinceIdListAdapter<? extends Serializable> listAdapter,
			String serialKey, String totalKey, OnBuildSinceSerializableListener listener) throws Exception {
		List<? extends Serializable> list = null;
		long totalCount = 0l;
		boolean hasTotal = totalKey != null ? true : false;
		RowSelection rowSelection = new RowSelection(start, limit);
		if(sinceId > 0) {
			list = listAdapter.getSerializableBySinceId(sinceId, rowSelection);
			if(hasTotal) {totalCount = listAdapter.getTotalBySinceId(sinceId);}
			if(listener != null) {
				listener.onBuildBySinceId(list, totalCount);
			}
			
		} else if(maxId <= 0) { // 默认查询
			list = listAdapter.getSerializables(rowSelection);
			if(list.size() > 0) {
				Serializable ser = list.get(0);
				maxId = (Integer)ser.getClass().getMethod(getIdMethod).invoke(ser);
				if(hasTotal) {totalCount = listAdapter.getTotalByMaxId(maxId);}
				if(listener != null) {
					listener.onBuild(list, totalCount);
				}
			}
			
		} else if(maxId > 0) { // 查询比指定时间早的记录
			list = listAdapter.getSerializableByMaxId(maxId, rowSelection);
			if(hasTotal) {totalCount = listAdapter.getTotalByMaxId(maxId);}
			if(listener != null) {
				listener.onBuildByMaxId(list, totalCount);
			}
		}
		jsonMap.put(serialKey, list);
		if(hasTotal) {jsonMap.put(totalKey, totalCount);}
		return maxId;
	}

	/**
	 * 构建序列化列表
	 * 
	 * @param sinceId
	 * @param maxId
	 * @param start
	 * @param page
	 * @param jsonMap
	 * @param listAdapter
	 * @param serialKey
	 * @param totalKey
	 * @param listener
	 * @return
	 * @throws Exception
	 */
	public Integer buildSerializables(int sinceId, int maxId, int start,
			int page, Map<String, Object> jsonMap,
			SerializableSinceIdListAdapter<? extends Serializable> listAdapter,
			String serialKey, String totalKey,
			OnBuildSinceSerializableListener listener) throws Exception {
		return buildSerializables("getId", sinceId, maxId, start,
				page, jsonMap,listAdapter, serialKey, totalKey, listener);
	}

	/**
	 * 构建序列化列表
	 * 
	 * @param sinceId
	 * @param maxId
	 * @param start
	 * @param limit
	 * @param jsonMap
	 * @param listAdapter
	 * @param serialKey
	 * @param totalKey
	 * @param maxIdKey
	 * @param listener
	 * @throws Exception
	 */
	public void buildSerializables(int sinceId, int maxId, int start,
			int limit, Map<String, Object> jsonMap,
			SerializableSinceIdListAdapter<? extends Serializable> listAdapter,
			String serialKey, String totalKey, String maxIdKey,
			OnBuildSinceSerializableListener listener) throws Exception {
		jsonMap.put(maxIdKey, buildSerializables(sinceId, maxId, start, limit,
				jsonMap, listAdapter, serialKey, totalKey, listener));
		
	}

	/**
	 * 构建序列化列表
	 * 
	 * @param getIdMethod
	 * @param sinceId
	 * @param maxId
	 * @param start
	 * @param limit
	 * @param jsonMap
	 * @param listAdapter
	 * @param serialKey
	 * @param totalKey
	 * @param maxIdKey
	 * @param listener
	 * @throws Exception
	 */
	public void buildSerializables(String getIdMethod, int sinceId, int maxId,
			int start, int limit, Map<String, Object> jsonMap,
			SerializableSinceIdListAdapter<? extends Serializable> listAdapter,
			String serialKey, String totalKey, String maxIdKey,
			OnBuildSinceSerializableListener listener) throws Exception {
		jsonMap.put(maxIdKey, buildSerializables(getIdMethod, sinceId, maxId,
				start, limit, jsonMap,listAdapter, serialKey, totalKey, listener));
	}
	

	/**
	 * 构建序列化列表
	 * 
	 * @param getIdMethod
	 * @param sinceId
	 * @param maxId
	 * @param start
	 * @param limit
	 * @param jsonMap
	 * @param listAdapter
	 * @param serialKey
	 * @param totalKey
	 * @param maxIdKey
	 * @throws Exception
	 */
	public void buildSerializables(String getIdMethod, int sinceId, int maxId,
			int start, int limit, Map<String, Object> jsonMap,
			SerializableSinceIdListAdapter<? extends Serializable> listAdapter,
			String serialKey, String totalKey, String maxIdKey)
			throws Exception {
		jsonMap.put(maxIdKey, buildSerializables(getIdMethod, sinceId, maxId,
				start, limit, jsonMap,listAdapter, serialKey, totalKey, new OnBuildSinceSerializableListener(){
					@Override
					public void onBuild(List<? extends Serializable> list, long total) {}
		
					@Override
					public void onBuildByMaxId(List<? extends Serializable> list, long total) {}
		
					@Override
					public void onBuildBySinceId(List<? extends Serializable> list, long total) {}
				})
			);
	}

	/**
	 * 构建序列化列表
	 * 
	 * @param getIdMethod
	 * @param sinceId
	 * @param maxId
	 * @param start
	 * @param limit
	 * @param jsonMap
	 * @param listAdapter
	 * @param serialKey
	 * @param totalKey
	 * @return
	 * @throws Exception
	 */
	public Integer buildSerializables(String getIdMethod, int sinceId,
			int maxId, int start, int limit, Map<String, Object> jsonMap,
			SerializableSinceIdListAdapter<? extends Serializable> listAdapter,
			String serialKey, String totalKey) throws Exception {
		return buildSerializables(getIdMethod, sinceId, maxId, start,
				limit, jsonMap,listAdapter, serialKey, totalKey, new OnBuildSinceSerializableListener(){
			
					@Override
					public void onBuild(List<? extends Serializable> list, long total) {}
					
					@Override
					public void onBuildByMaxId(List<? extends Serializable> list, long total) {}
					
					@Override
					public void onBuildBySinceId(List<? extends Serializable> list, long total) {}
		});
	}

	/**
	 * 构建序列化列表
	 * 
	 * @param sinceId
	 * @param maxId
	 * @param start
	 * @param limit
	 * @param jsonMap
	 * @param listAdapter
	 * @param serialKey
	 * @param totalKey
	 * @return
	 * @throws Exception
	 */
	public Integer buildSerializables(int sinceId, int maxId, int start,
			int limit, Map<String, Object> jsonMap,
			SerializableSinceIdListAdapter<? extends Serializable> listAdapter,
			String serialKey, String totalKey) throws Exception {
		return buildSerializables("getId", sinceId, maxId, start,
				limit, jsonMap,listAdapter, serialKey, totalKey);
	}

	/**
	 * 构建序列化列表
	 * 
	 * @param sinceId
	 * @param maxId
	 * @param start
	 * @param limit
	 * @param jsonMap
	 * @param listAdapter
	 * @param serialKey
	 * @param totalKey
	 * @param maxIdKey
	 * @throws Exception
	 */
	public void buildSerializables(int sinceId, int maxId, int start,
			int limit, Map<String, Object> jsonMap,
			SerializableSinceIdListAdapter<? extends Serializable> listAdapter,
			String serialKey, String totalKey, String maxIdKey) throws Exception {
		jsonMap.put(maxIdKey, buildSerializables(sinceId, maxId, start, limit,
				jsonMap, listAdapter, serialKey, totalKey));
	}
	
	protected <T extends AbstractNumberDto> void buildNumberDtos(T dto, int start, int limit, Map<String, Object> jsonMap,
			NumberDtoListAdapter<T> adapter) throws Exception {
		buildNumberDtos(OptResult.JSON_KEY_ROWS, OptResult.JSON_KEY_TOTAL, OptResult.JSON_KEY_MAX_ID, "getId",
				dto, start, limit, jsonMap, adapter);
	}
	
	protected <T extends AbstractNumberDto> void buildNumberDtos(String getIdMethod, T dto, int start, int limit, Map<String, Object> jsonMap,
			NumberDtoListAdapter<T> adapter) throws Exception {
		buildNumberDtos(OptResult.JSON_KEY_ROWS, OptResult.JSON_KEY_TOTAL, OptResult.JSON_KEY_MAX_ID, getIdMethod,
				dto, start, limit, jsonMap, adapter);
	}
	
	/**
	 * 
	 * @throws Exception
	 * @author zhangbo 2015年6月3日
	 */
	protected <T extends AbstractNumberDto> void buildNumberDtos(T dto, int start, int limit, Map<String, Object> jsonMap,
		NumberDtoListAdapter<T> adapter, NumberDtoListMaxIdAdapter maxIdAdapter) throws Exception {
	    buildNumberDtos(OptResult.JSON_KEY_ROWS, OptResult.JSON_KEY_TOTAL, OptResult.JSON_KEY_MAX_ID,
		    dto, start, limit, jsonMap, adapter, maxIdAdapter);
	}
	
	protected <T extends AbstractNumberDto> void buildNumberDtos(String serialKey, 
			String totalKey, String maxIdKey, final String getIdMethod,
			T dto, int start, int limit, Map<String, Object> jsonMap,
			NumberDtoListAdapter<T> adapter) throws Exception {
		buildNumberDtos(serialKey, totalKey, maxIdKey,
				dto, start, limit, jsonMap, adapter, new NumberDtoListMaxIdAdapter() {

					@Override
					public Serializable getMaxId(List<? extends Serializable> list) throws Exception {
						Serializable ser = list.get(0);
						Object obj = ser.getClass().getMethod(getIdMethod).invoke(ser);
						if(obj != null) {
							Serializable maxSerializable = (Serializable) obj;
							return maxSerializable;
						}
						return null;
					}
			
		});
		
	}
	
	
	protected <T extends AbstractNumberDto> void buildNumberDtos(String serialKey, 
			String totalKey, String maxIdKey,
			T dto, int start, int limit, Map<String, Object> jsonMap, NumberDtoListAdapter<T> adapter, 
			NumberDtoListMaxIdAdapter maxIdAdapter) throws Exception {
		int firstRow = (start - 1) * limit;
		dto.setFirstRow(firstRow);
		dto.setLimit(limit);
		
		List<? extends Serializable> list = adapter.queryList(dto);
		long total = adapter.queryTotal(dto);
		
		if(start == 1 && list.size() > 0) {
			Serializable maxSerializable = maxIdAdapter.getMaxId(list);
			jsonMap.put(maxIdKey, maxSerializable);
		}
		
		jsonMap.put(serialKey, list);
		jsonMap.put(totalKey, total);
		
	}
	
	protected interface NumberDtoListAdapter<T extends AbstractNumberDto> {
		
		/**
		 * 查询POJO列表
		 * 
		 * @param dto
		 * @return
		 */
		public List<? extends Serializable> queryList(T dto);
		
		/**
		 * 查询记录总数
		 * 
		 * @param dto
		 * @return
		 */
		public long queryTotal(T dto);
		
	}
	
	protected interface NumberDtoListMaxIdAdapter {
		
		/**
		 * 从列表中获取maxid
		 * @param list
		 * @return
		 */
		public Serializable getMaxId(List<? extends Serializable> list) throws Exception;
	}
	
}
