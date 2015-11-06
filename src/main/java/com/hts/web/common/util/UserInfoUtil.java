package com.hts.web.common.util;

import com.hts.web.base.constant.Tag;

public class UserInfoUtil {

	/**
	 * 判断是否为IM的版本
	 * 
	 * @param ver
	 * @return
	 */
	public static Boolean checkIsImVersion(Float ver) {
		if(ver != null && ver >= Tag.VERSION_2_9_82) {
			return true;
		}
		return false;
	}
	
	/**
	 * 判断是否为AT版本
	 * 
	 * @param ver
	 * @return
	 */
	public static Boolean checkIsAtVersion(Float ver) {
		if(ver != null && ver >= Tag.VERSION_3_0_2) {
			return true;
		}
		return false;
	}
	
	/**
	 * 根据用户版本获取系统消息代号<br>
	 * 因为万恶的Android 2.9.83之前的版本没有做消息代号兼容，直接闪退。
	 * 
	 * @param ver
	 * @param msgCode
	 * @return
	 */
	public static int getSysMsgCode(Float ver, Integer msgCode) {
		if(ver <= Tag.VERSION_2_9_83 && msgCode >= Tag.USER_MSG_CHANNEl_TOP_ONE) {
			return Tag.USER_MSG_SYS;
		}
		return msgCode;
	}
	
	/**
	 * left shift
	 * low 31 bit circle left shift
	 * return result
	 * eg. IF uShiltByte is 3,uByteCount is 31
	 * 0x7FFFFFF7 -> 0x7FFFFFBB
	 * 0111 1111 1111 1111 1111 1111 1111 1001B -> 0111 1111 1111 1111 1111 1111 1100 1111B
	 * This way can deal with the symbol bit
	 * 
	 * @param iSrc 输入
	 * @param uBitCount 位数
	 * @param uShiltBit 左移的位数
	 * @return
	 * @author zxx
	 * @since 2015年11月3日 11:42:08
	 */
	public static int lshift(int iSrc, int uBitCount,int uShiltBit){
		int iSrcHighBit  = iSrc >> uBitCount << uBitCount;
		int iSrcLow31Bit = iSrc & 0x7FFFFFFF;
		int iHigh2LowBit = iSrcLow31Bit >> (uBitCount - uShiltBit);
		int iLow2HighBit = (iSrcLow31Bit << uShiltBit) & 0x7FFFFFFF;
		return iHigh2LowBit | iLow2HighBit | iSrcHighBit;
	}

	/**
	 * right shift
	 * low 31 bite circle right shift
	 * see lshift
	 * 
	 * @param iSrc 输入
	 * @param uBitCount
	 * @param uShiltBit 右移的位数
	 * @return
	 * @author zxx
	 * @since 2015年11月3日 11:43:17
	 */
	public static int rshift(int iSrc, int uBitCount, int uShiltBit){
		int iSrcHighBit  = iSrc >> uBitCount << uBitCount;
		int iSrcLow31Bit = iSrc & 0x7FFFFFFF;
		int iLow2HighBit = iSrcLow31Bit >> uShiltBit;
		int iHigh2LowBit = (iSrcLow31Bit << uBitCount - uShiltBit) & 0x7FFFFFFF;
		return iHigh2LowBit | iLow2HighBit | iSrcHighBit;
	}

	
	
	/**
	 * encode. return the result.
	 * 1. low 31 bit xor.
	 * 2. low 31 bit left circle shift 3 bit
	 * @author zxx
	 * @param iUserId 用户id
	 * @since 2015年11月3日 11:45:30
	 */
	public static int encode(int iUserId){
		int iSrcLow31Bit  = (~iUserId) & 0x7FFFFFFF; 
		int iSrcHighBit   = iUserId >> 31 << 31; 
		int rs            = lshift(iSrcLow31Bit, 31, 3);
		return rs | iSrcHighBit;
	}

	/**
	 * decode. return the result.
	 * 1. get low 31 bit.
	 * 2. get high bit.
	 * 3. right circle shift 3 bit.
	 * 4. low 31 bit xor.
	 * @author zxx
	 * @param iUserId 用户id
	 * @since 2015年11月3日 11:46:31
	 */
	public static int decode(int iUserId){
		int iSrcLow31Bit  = iUserId & 0x7FFFFFFF;
		int iSrcHighBit   = iUserId >> 31 << 31;
		int rs            = rshift(iSrcLow31Bit, 31, 3);
		rs                = (~rs) & 0x7FFFFFFF;
		return rs | iSrcHighBit;
	}
}
