package com.hts.web.common.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;

/**
 * 该类将通过MD5对用户所输入的密码进行加密
 * 
 * @version 1.0
 */
public class MD5Encrypt {
	/**
	 * 该方法实现MD5加密
	 * 
	 * @param password
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	public static byte[] encryptByMD5(String password)
			throws NoSuchAlgorithmException {
		SecureRandom random = new SecureRandom();
		byte[] salt = new byte[12];
		byte[] encryptPassword = null;
		// 生成12位的随机值
		random.nextBytes(salt);
		MessageDigest messageDigest = MessageDigest.getInstance("MD5");
		// 通过update()方法依次对salt盐及用户输入的密码password进行加密
		messageDigest.update(salt);
		messageDigest.update(password.getBytes());
		byte[] digest = messageDigest.digest();
		encryptPassword = new byte[digest.length + 12];
		// 数据库中所保存的密码由salt及digest组成
		System.arraycopy(salt, 0, encryptPassword, 0, 12);
		System.arraycopy(digest, 0, encryptPassword, 12, digest.length);
		return encryptPassword;
	}

	/**
	 * 该方法完成登录时密码的验证
	 * 
	 * @param password
	 * @param encryptPassword
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	public static boolean validatePassword(String password,
			byte[] encryptPassword) throws NoSuchAlgorithmException {
		byte[] salt = new byte[12];
		// 从encryptPassword这一数据库中保存的密码中取得12位的随机值
		System.arraycopy(encryptPassword, 0, salt, 0, 12);
		MessageDigest messageDigest = MessageDigest.getInstance("MD5");
		messageDigest.update(salt);
		messageDigest.update(password.getBytes());
		byte[] digest = messageDigest.digest();
		byte[] digestInDB = new byte[encryptPassword.length - 12];
		System.arraycopy(encryptPassword, 12, digestInDB, 0,
				encryptPassword.length - 12);
		// 比较重新加密后的值与数据库中保存的密码（去掉salt之后的值）是否相等
		if (Arrays.equals(digest, digestInDB)) {
			return true;
		} else {
			return false;
		}
	}

	public static String shortUrl(long num) {
		String shortLink = hex10ToHex64(num + 100000000000l);
		return shortLink;
	}
	
	public static String shortUrl(long num, long base) {
		String shortLink = hex10ToHex36(num + base);
		return shortLink;
	}
	
	private static char[] array = {
		'q','w','e','r','t','y','u','i','o','p',
		'a','s','d','f','g','h','j','k','l','z',
		'x','c','v','b','n','m','0','1','2','3',
		'4','5','6','7','8','9','Q','W','E','R',
		'T','Y','U','I','O','P','A','S','D','F',
		'G','H','J','K','L','Z','X','C','V','B','N','M'};
	
	
	private static char[] array36 = {
			'q','w','e','r','t','y','u','i','o','p',
			'a','s','d','f','g','h','j','k','l','z',
			'x','c','v','b','n','m','0','1','2','3',
			'4','5','6','7','8','9'};
	
	
	/**
	 * 10进制转64进制
	 * 
	 * @param number
	 * @return
	 */
	public static String hex10ToHex64(long number){
        Long rest = number;
        StringBuilder result=new StringBuilder(0);
        while(rest != 0){
        	result.append(array[new Long((rest - (rest / 62) * 62)).intValue()]);
            rest = rest / 62;
        }
        return result.toString();
	}
	
	/**
	 * 10进制转36进制
	 * 
	 * @param number
	 * @return
	 */
	public static String hex10ToHex36(long number){
		int length = array36.length;
        Long rest = number;
        StringBuilder result=new StringBuilder(0);
        while(rest != 0){
        	result.append(array36[new Long((rest - (rest / length) * length)).intValue()]);
            rest = rest / length;
        }
        return result.reverse().toString();
	}
	
	
}
