package com.hts.web.base.database;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 * SQL工具类，用于构造SQL
 * </p>
 * @author ztj
 *
 */
public class SQLUtil {

	/**
	 * 构建查询条件
	 * @param attrMap
	 * @return
	 */
	public static String buildSelection(Map<String, Object> attrMap) {
		StringBuilder builder = new StringBuilder();
		Set<String> keySet = attrMap.keySet();
		Object[] keies = keySet.toArray();
		for(int i = 0; i < keies.length; i++) {
			if(i != 0) {
				builder.append(" and ").append(keies[i]).append("=?");
			} else {
				builder.append(" where ").append(keies[0]).append("=?");
			}
		}
		return builder.toString();
	}
	
	/**
	 * 构建查询条件
	 * @param attrMap
	 * @param alias
	 * @return
	 */
	public static String buildSelection(Map<String, Object> attrMap, String alias) {
		StringBuilder builder = new StringBuilder();
		Set<String> keySet = attrMap.keySet();
		Object[] keies = keySet.toArray();
		for(int i = 0; i < keies.length; i++) {
			if(i != 0) {
				builder.append(" and ").append(alias).append(".").append(keies[i]).append("=?");
			} else {
				builder.append(" where ").append(alias).append(".").append(keies[0]).append("=?");
			}
		}
		return builder.toString();
	}
	
	/**
	 * 构建含有格式化时间间隔查询条件
	 * @param attrMap
	 * @param timeField
	 * @return
	 */
	public static String buildSelectionByTimesFormat(Map<String,Object> attrMap,String timeField) {
		String selection = buildSelection(attrMap);
		StringBuilder builder = new StringBuilder(selection);
		if(selection.equals("")) {
			builder.append(" where ");
		} else {
			builder.append(" and ");
		}
		builder.append("DATE_FORMAT(").append(timeField).append(",'%Y-%m-%d')").append(" between ? and ? ");
		return builder.toString();
	}

	/**
	 * 构建含有格式化时间查询条件 
	 * @param attrMap
	 * @param timeField
	 * @return
	 */
	public static String buildSelectionByTimeFormat(Map<String,Object> attrMap,String timeField) {
		String selection = buildSelection(attrMap);
		StringBuilder builder = new StringBuilder(selection);
		if(selection.equals("")) {
			builder.append(" where ");
		} else {
			builder.append(" and ");
		}
		builder.append("DATE_FORMAT(").append(timeField).append(",'%Y-%m-%d')").append(" = ? ");
		return builder.toString();
	}


	/**
	 * 构建选择元数据条件
	 * @param metas
	 * @return
	 */
	public static String buildMetaSelection(String[] metas) {
		StringBuilder builder = new StringBuilder("SELECT ");
		for(int i = 0; i < metas.length; i++) {
			if(i == 0) {
				builder.append(metas[i]);
			} else {
				builder.append(",").append(metas[i]);
			}
		}
		builder.append(" from ");
		return builder.toString();
	}
	
	/**
	 * 构建批量删除条件
	 * 
	 * @param ids
	 * @param name
	 * @return
	 */
	public static String buildDeleteInSelection(Object[] objs, String name) {
		StringBuilder builder = new StringBuilder();
		if(objs.length > 0) {
			builder.append(" where ").append(name).append(" in (");
		}
		for(int i = 0; i < objs.length; i++) {
			if(i == objs.length - 1) {
				builder.append("?)");
			} else {
				builder.append("?,");
			}
		}
		return builder.toString();
	}
	
	/**
	 * 构建in查询条件(?,?,?)
	 * @param objs
	 * @return
	 */
	public static String buildInSelection(Object[] objs) {
		StringBuilder builder = new StringBuilder(" (");
		for(int i = 0; i < objs.length; i++) {
			if(i == objs.length - 1) {
				builder.append("?)");
			} else {
				builder.append("?,");
			}
		}
		return builder.toString();
	}
	
	
	/**
	 * 构建更新条件,注意：查询的条件要放在最后的位置
	 * @param objs
	 * @param where
	 * @return
	 */
	public static String buildUpdateSelection(Map<String, Object> attrMap) {
		StringBuilder builder = new StringBuilder();
		Set<String> keySet = attrMap.keySet();
		Object[] keies = keySet.toArray();
		int len = keies.length;
		if(len > 1) {
			builder.append(" set ");
		}
		for(int i = 0; i < len - 1; i++) {
			if(i != 0) {
				builder.append(",").append(keies[i]).append("=?");
			} else {
				builder.append(keies[i]).append("=?");
			}
		}
		builder.append(" where ").append(keies[len - 1]).append("=?");
		return builder.toString();
	}
	
	/**
	 * 构建更新查询条件
	 * 
	 * @param attrMap
	 * @param where
	 * @return
	 */
	public static String buildUpdateSQL(String table, Map<String, Object> attrMap, String where) {
		StringBuilder builder = new StringBuilder();
		Set<String> keySet = attrMap.keySet();
		Object[] keies = keySet.toArray();
		int len = keies.length;
		if(len == 0) {
			throw new NullPointerException("更新条件为空");
		}
		builder.append("UPDATE ").append(table).append(" set ");
		for(int i = 0; i <= len - 1; i++) {
			if(i != 0) {
				builder.append(",").append(keies[i]).append("=?");
			} else {
				builder.append(keies[i]).append("=?");
			}
		}
		builder.append(" where ").append(where);
		return builder.toString();
	}
	
	/**
	 * 构建保存条件
	 * 
	 * @param attrMap
	 * @return
	 */
	public static String buildSaveSelection(Map<String,Object> attrMap) {
		StringBuilder keyBuilder = new StringBuilder(" (");
		StringBuilder valueBuilder = new StringBuilder(" values (");
		Set<String> keySet = attrMap.keySet();
		Object[] keies = keySet.toArray();
		for(int i = 0; i < keies.length; i++) {
			if(i != 0) {
				keyBuilder.append(",").append(keies[i]);
				valueBuilder.append(",?");
			} else {
				keyBuilder.append(keies[i]);
				valueBuilder.append("?");
			}
		}
		keyBuilder.append(")");
		valueBuilder.append(")");
		return keyBuilder.append(valueBuilder).toString();
	}
	
	/**
	 * 构建含有排序的查询语句
	 * @param timeField
	 * @param startTime
	 * @param endDate
	 * @param orderField
	 * @param order
	 * @return
	 */
	public static String buildSelectionWithOrder(Map<String,Object> attrMap,String orderField, String order) {
		String selection = buildSelection(attrMap);
		return selection + " order by " + orderField + " " + order;
	}

	/**
	 * 构建含有需要被格式化的时间和排序的查询语句
	 * @param attrMap
	 * @param timeField
	 * @param orderField
	 * @param order
	 * @return
	 */
	public static String buildSelectionByFormatTimeWithOrder(Map<String,Object> attrMap,String timeField,String orderField, String order) {
		String selection = buildSelectionByTimeFormat(attrMap, timeField);
		StringBuilder builder = new StringBuilder(selection);
		builder.append("order by ").append(orderField).append(" ").append(order);
		return builder.toString();
	}
	
	/**
	 * 构建含有需要被格式化时间间隔和排序的查询语句
	 * @param attrMap
	 * @param timeField
	 * @param orderField
	 * @param order
	 * @return
	 */
	public static String buildSelectionByFormatTimesWithOrder(Map<String,Object> attrMap,String timeField,String orderField, String order) {
		String selection = buildSelectionByTimesFormat(attrMap, timeField);
		StringBuilder builder = new StringBuilder(selection);
		builder.append("order by ").append(orderField).append(" ").append(order);
		return builder.toString();
	}
	
	/**
	 * 动态构建含有In查询条件的参数列表
	 * 
	 * @param inArgs
	 * @param otherArgs
	 * @param isInBehind in参数是否在所有参数后面
	 * @return
	 */
	public static Object[] getArgsByInCondition(Object[] inArgs, Object[] otherArgs, boolean isInBehind) {
		int inArgsLen = inArgs.length;
		int otherArgsLen = otherArgs.length;
		Object[] args = new Object[otherArgsLen + inArgsLen];
		for(int i = 0; i < args.length; i++) {
			if(!isInBehind) {
				if(i <= inArgsLen - 1) {
					args[i] = inArgs[i];
				} else {
					args[i] = otherArgs[i - inArgsLen];
				}
			} else {
				if(i <= otherArgsLen - 1) {
					args[i] = otherArgs[i];
				} else {
					args[i] = inArgs[i - otherArgsLen];
				}
			}
			
		}
		return args;
	}
	
	/**
	 * 将list装换为“'o','b','j'”格式的字符串
	 * @param a
	 * @return
	 */
	public static String toString(List<String> a) {
		if (a == null)
			return "";
		int iMax = a.size() - 1;
		if (iMax == -1)
			return "";

		StringBuilder b = new StringBuilder();
		for (int i = 0;; i++) {
			b.append("'").append(String.valueOf(a.get(i))).append("'");
			if (i == iMax)
				return b.toString();
			b.append(",");
		}
	}
	
	/**
	 * 构建分页SQL
	 * 
	 * @param sql
	 * @param rowSelection
	 * @return
	 */
	public static String preparePage(String sql, RowSelection rowSelection) {
		sql = sql + " LIMIT " + rowSelection.getFirstRow() + "," + rowSelection.getLimit();
		return sql;
	}
	
}
