package com.hts.web.common.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.UUID;

import net.sf.xsshtmlfilter.HTMLFilter;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import com.hts.web.base.HTSException;

/**
 * <p>
 * 字符串工具类
 * </p>
 * 
 * 创建时间：2012-11-06
 * 
 * @author ztj
 * 
 */
public class StringUtil {

	private static final String QINIU_DOMAIN = "imzhitu.qiniudn";
	
	private static final String ZHITU_DOMAIN = "static.imzhitu";

	/**
	 * 将字符串转换为整数数组
	 * 
	 * @param idsStr
	 * @return
	 */
	public static Integer[] convertStringToIds(String idsStr) {
		String[] strs = idsStr.split(",");
		Integer[] ids = new Integer[strs.length];
		for (int i = 0; i < strs.length; i++) {
			ids[i] = Integer.parseInt(strs[i].trim());
		}
		return ids;
	}

	/**
	 * 将字符串转换为字符数组，并过滤空格
	 * 
	 * @param strs
	 * @return
	 */
	public static String[] convertStringToStrs(String strs) {
		String[] strs1 = strs.split(",");
		String[] strs2 = new String[strs1.length];
		for (int i = 0; i < strs1.length; i++) {
			strs2[i] = strs1[i].trim();
		}
		return strs2;
	}

	/**
	 * 解决中文乱码，转码为“UTF-8"
	 * 
	 * @param source
	 * @return
	 */
	public static String encodeToUTF8(String source) {
		try {
			source = new String(source.getBytes("iso-8859-1"), "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return source;
	}

	/**
	 * 获取字符串缩略图,例如：由testttt转成test...
	 * 
	 * @param src
	 * @param length
	 * @return
	 */
	public static String getShortCut(String src, int length) {
		if (src.length() > length) {
			return src.substring(0, length) + "...";
		}
		return src;
	}

	/**
	 * 获取标志
	 * 
	 * @param src
	 *            被分割的字符串
	 * @param split
	 *            从何处开始分割
	 * @return
	 * @throws HTSException
	 */
	public static String getTag(String src, String split) throws HTSException {
		if (!src.contains(split)) {
			throw new HTSException(new NullPointerException());
		}
		int index = src.lastIndexOf(split);
		String tag = src.substring(index + 1);
		return tag;
	}

	/**
	 * 获取文件后缀名
	 * 
	 * @param fileName
	 */
	public static String getSuffix(String fileName) {
		int index = fileName.lastIndexOf(".");
		return fileName.substring(index);
	}

	/**
	 * 将文件名重新用时间戳表示
	 * 
	 * @param fileName
	 * @return
	 */
	public static String renameFileNameToDateTime(String fileName) {
		return new Date().getTime() + getSuffix(fileName);
	}

	/**
	 * 生成UUID字符串,转换大写并去除下划线
	 * 
	 * @return
	 */
	public static String randomUUIDWithoutUnderLine() {
		return UUID.randomUUID().toString().replace("-", "");
	}

	/**
	 * 检测是否为空
	 * 
	 * @param src
	 * @return
	 */
	public static boolean checkIsNULL(String src) {
		if (src == null || "".equals(src) || "null".equals(src)
				|| "(null)".equals(src)) {
			return true;
		}
		return false;
	}

	/**
	 * 将空字符串""转换成NULL
	 * 
	 * @param src
	 * @return
	 */
	public static String convertEmpty2NULL(String src) {
		if (checkIsNULL(src)) {
			return null;
		} else {
			return src;
		}
	}

	/**
	 * 获取拼音
	 * 
	 * @param zhongwen
	 * @return
	 * @throws BadHanyuPinyinOutputFormatCombination
	 */
	public static String getPinYin(String zhongwen) {

		String zhongWenPinYin = "";
		try {
			char[] chars = zhongwen.toCharArray();

			for (int i = 0; i < chars.length; i++) {
				String[] pinYin = PinyinHelper.toHanyuPinyinStringArray(
						chars[i], getDefaultOutputFormat());
				// 当转换不是中文字符时,返回null
				if (pinYin != null) {
					zhongWenPinYin += pinYin[0];
				} else {
					zhongWenPinYin += chars[i];
				}
			}
		} catch (BadHanyuPinyinOutputFormatCombination e) {

		}

		return zhongWenPinYin;
	}

	/**
	 * Default Format 默认输出格式
	 * 
	 * @return
	 */
	public static HanyuPinyinOutputFormat getDefaultOutputFormat() {
		HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
		format.setCaseType(HanyuPinyinCaseType.LOWERCASE);// 小写
		format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);// 没有音调数字
		format.setVCharType(HanyuPinyinVCharType.WITH_U_AND_COLON);// u显示

		return format;
	}

	/**
	 * Capitalize 首字母大写
	 * 
	 * @param s
	 * @return
	 */
	public static String capitalize(String s) {
		char ch[];
		ch = s.toCharArray();
		if (ch[0] >= 'a' && ch[0] <= 'z') {
			ch[0] = (char) (ch[0] - 32);
		}
		String newString = new String(ch);
		return newString;
	}

	/**
	 * 获取文件字符编码
	 * 
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	public static String getCharset(File file) throws IOException {
		BufferedInputStream bin = new BufferedInputStream(new FileInputStream(
				file));
		int p = (bin.read() << 8) + bin.read();
		String code = null;
		switch (p) {
		case 0xefbb:
			code = "UTF-8";
			break;
		case 0xfffe:
			code = "Unicode";
			break;
		case 0xfeff:
			code = "UTF-16BE";
			break;
		default:
			code = "GBK";
		}
		bin.close();
		return code;
	}

	public static String convertArrayToString(Object[] a) {
		if (a == null)
			return "";
		int iMax = a.length - 1;
		if (iMax == -1)
			return "";
		StringBuilder b = new StringBuilder();
		for (int i = 0;; i++) {
			b.append(String.valueOf(a[i]));
			if (i == iMax)
				return b.toString();
			b.append(",");
		}
	}

	/**
	 * 过滤XSS注入
	 * 
	 * @param input
	 * @return
	 */
	public static String filterXSS(String input) {
		if (!StringUtil.checkIsNULL(input)) {
			return new HTMLFilter().filter(input);
		}
		return input;
	}
	
	public static String subSHIFromCity(String city) {
		int idx = city.lastIndexOf("市");
		if(idx > 0) {
			return city.substring(0, idx);
		}
		return city;
	}
	
	public static String subShengFromProvince(String province) {
		int idx = province.lastIndexOf("省");
		if(idx > 0) {
			return province.substring(0, idx);
		}
		return province;
	}
	

	/**
	 * 替换七牛域名到织图域名
	 * @param path
	 * @return
	 */
	public static String replaceQiniuDomain(String path) {
		if(!StringUtil.checkIsNULL(path) && path.contains(QINIU_DOMAIN)) {
			return path.replace(QINIU_DOMAIN, ZHITU_DOMAIN);
		}
		return path;
	}
	
	
}
