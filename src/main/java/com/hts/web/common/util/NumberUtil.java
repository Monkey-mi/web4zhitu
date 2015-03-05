package com.hts.web.common.util;

import java.util.Set;

/**
 * <p>
 * 数字工具类
 * </p>
 * 
 * 创建时间：2013-8-6
 * @author ztj
 *
 */
public class NumberUtil {
	
	/**
	 * 根据总数获取分页数
	 * @param limit
	 * @param total
	 * @return
	 */
	public static int getPageByTotal(int limit, int total) {
		int page = 0;
		int r = total % limit;
		int q = total / limit;
		if(r > 0) {
			page = q + 1;
		} else {
			page = q;
		}
		return page;
	}
	
	/**
	 * 随机获取分页
	 * (最小值+Math.random()*(最大值-最小值+1))
	 * 
	 * @param totalPage
	 * @return
	 */
	public static int getRandomPage(int totalPage) {
		int p = (int) (1 + Math.random() * (totalPage - 1 + 1));
		return p;
	}
	
	/**
	 * 获取随机索引，从0开始
	 * 
	 * @param len
	 */
	public static final int getRandomIndex(int len) {
		int p = (int) (Math.random() * len);
		return p;
	}
	
	/**
	 * 根据总数随机获取分页
	 * 
	 * @param limit
	 * @param total
	 * @return
	 */
	public static int getRandomPage(int limit, int total) {
		int totalPage = getPageByTotal(limit, total);
		return getRandomPage(totalPage);
	}
	
	/**
	 * 获取随机数字
	 * 
	 * @param min
	 * @param max
	 * @return
	 */
	public static int getRandomNum(int min, int max) {
		return (int)(min + Math.random() * (max - min + 1));
	}

	public static void main(String[] args) {
		for(int i = 0; i < 10; i++) {
			int k = i;
			int r = getRandomNum(k, 10);
			System.out.println(r);
		}
	}
	
	/**
	 * 获取随机索引
	 * 
	 * @param len
	 * @param usedIndex
	 * @return
	 */
	public static Integer getRandomIndex(int len, Set<Integer> usedIndex) {
		int index = NumberUtil.getRandomIndex(len);
		if(usedIndex.contains(index)) {
			index = getRandomIndex(len, usedIndex);
		}
		return index;
	}
	
	/**
	 * 获取不重复的随机索引
	 */
	public static Integer getUnRepeatRandomIndex(int len,Set<Integer> usedIndex){
		if(usedIndex.size()>=len)throw new IndexOutOfBoundsException("已经使用过的随机索引超出了总数");
		int index ;
		do{
			index = NumberUtil.getRandomIndex(len);
		}while(usedIndex.contains(index));
		return index;
	}
}
