package com.hts.web.userinfo.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import net.sf.json.JSONObject;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.BaseTest;
import com.hts.web.base.constant.PhoneType;
import com.hts.web.base.constant.PlatFormCode;
import com.hts.web.base.constant.Tag;
import com.hts.web.common.pojo.UserInfo;
import com.hts.web.common.util.Log;

/**
 * <p>
 * 用户信息业务逻辑访问接口单元测试
 * </p>
 * 
 * 创建时间：2013-6-21
 * @author ztj
 *
 */
public class UserInfoServiceTest extends BaseTest {

	@Autowired
	private UserInfoService service;
	
	@Test
	public void testRegister() throws Exception {
		String loginCode = "12s3462" + Math.random() * 100000;
		String userName = "tom" + Math.random() * 100000;
		UserInfo userInfo = service.register(loginCode, "123451s6", userName,
				"http://imzhitu.qiniudn.com/avatar/s/2014/01/23/22/WbEic9FJoq06AicJTn8Dp68yYQJ8pDUXZDLDmIibg.jpg",
				"http://imzhitu.qiniudn.com/avatar/m/2014/01/23/22/WTXodupl9CeIAxbVWEHorV1C0RsyJpr65ZV9bw.jpg", 
				Tag.SEX_FEMALE, null, null,"广东省", "湛江市", 123.444d, 1211.33d,0, "互联网工程师", "push_token_test", 
				PhoneType.PHONE_TYPE_ANDROID, null, "4.01", 2098700.0f);
		logObj(userInfo);
		
	}
	
	@Test
	public void testCheckUserNameExists() throws Exception {
		boolean flag = service.checkUserNameExists("_天杰2");
		Log.debug(flag);
	}
	
	@Test
	public void testLogin() throws Exception {
		UserInfo userInfo = service.login("12s346213563.673693967015", "123451s6", 
				"1111",PhoneType.PHONE_TYPE_ANDROID, null, "4.01", 2.084f);
		JSONObject jsObj = JSONObject.fromObject(userInfo);
		Log.debug(jsObj);
	}
	
	@Test
	public void testLoginBySocialAccount() throws Exception	 {
		UserInfo userInfo = service.loginBySocialAccount(PlatFormCode.SINA, 
				"OezXcEiiBSKSxW0eoylIeCepcSueXAZW35TQjEJF5W24SogjYXrlXX2dxpGWvBbICO0B2FZZ3KwuRkrqjJvYFLS4Wupp0_yPIB2gfs08CX9kj2k1pje6yubQkOFYRdfWYyKfZPHmQOfHdHzkGd38Vw",
				1412276264337l, "呵呵",1, null,
				"2097226533", "天天天杰",
				"http://imzhitu.qiniudn.com/avatar/s/2014/02/05/15/23d241de6d2eb98675e4ee89c46f044c.jpg",
				"http://imzhitu.qiniudn.com/avatar/m/2014/02/05/15/23d241de6d2eb98675e4ee89c46f044c.jpg",
				Tag.SEX_UNKNOWN,
				"c66202b9e888a76b66fe63573a396121", Tag.ANDROID, null, "4.01", 2.0981f, null);
		JSONObject jsObj = JSONObject.fromObject(userInfo);
		Log.debug(jsObj);
	}
	
	@Test
	public void testLogout() throws Exception {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		service.logout(114,"",jsonMap);
	}
	
	@Test
	public void testUpdateupdateSocialAccount() throws Exception {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
//		String platformId = "1123" + Math.random() * 100000;
		String platformId = "1234567";
//		service.deleteSocialAccount(740, 1);
		service.saveOrUpdateSocialAccount(743, 1, "12322", 123l, platformId, "呵呵",
				"http://imzhitu.qiniudn.com/avatar/s/2014/01/23/22/WbEic9FJoq06AicJTn8Dp68yYQJ8pDUXZDLDmIibg.jpg",
				"http://imzhitu.qiniudn.com/avatar/m/2014/01/23/22/WTXodupl9CeIAxbVWEHorV1C0RsyJpr65ZV9bw.jpg", 
				"呵呵", 21, null,
				jsonMap);
		Log.debug(jsonMap);
	}
	
	@Test
	public void testDeleteSocialAccount() throws Exception {
		service.deleteSocialAccount(145, 1);
	}
	
	@Test
	public void testUpdateProfile() throws Exception {
		service.updateProfile(1559, "k2kk2", "aa", "aa", 2, "email", "address", new Date(),  null, null, null,null, "123456","123456");
	}
	
	@Test
	public void testUpdatePassword() throws Exception {
		service.updatePassword(1559, "123456", "123456");
	}
	
	@Test
	public void testUpdateSignature() throws Exception {
		service.updateSignature(114, "eee");
	}
	
	@Test
	public void testQueryUserInfo() throws Exception {
		UserInfo userInfo = service.getUserInfoById(114, 527, true, false);
		logObj(userInfo);
	}
	
	@Test
	public void testUpdateAcceptPush() throws Exception {
		service.updateAcceptPushDisable(114, "1,2,3");
	}
	
	@Test
	public void testUpdateUserLabel() throws Exception {
		service.updateUserLabel(527, "吃货,哈哈哈,嘢");
	}
	
	@Test
	public void testUpdateUserLabel2() throws Exception {
		service.updateUserLabel(527, "15,16", "演唱会,运动");
	}
	
	@Test
	public void testUpdateUserName() throws Exception {
		int i = new Random().nextInt(1000000);
		service.updateUserName(485, "天杰1"+i);
	}
	
	@Test
	public void testUpdateAvatar() throws Exception {
		service.updateAvatar(485, 
				"http://imzhitu.qiniudn.com/avatar/s/2014/02/05/15/23d241de6d2eb98675e4ee89c46f044c.jpg", 
				"http://imzhitu.qiniudn.com/avatar/m/2014/02/05/15/23d241de6d2eb98675e4ee89c46f044c.jpg");
	}
	
	@Test
	public void testUpdateSex() throws Exception {
		service.updateSex(485, Tag.SEX_FEMALE);
	}
	
	@Test
	public void testUpdateAddress() throws Exception {
		service.updateAddress(485, "广东", "深圳", null, null, "广东 深圳");
	}
	
	@Test
	public void testUpdateEmail() throws Exception {
		service.updateEmail(485, "zhitu@gmail.com");
	}
	
	@Test
	public void testUpdateVerAndPushPuToken() throws Exception {
		service.updateVerAndPushToken(485, 2.0981f, "c66202b9e888a76b66fe63573a396121", "三星123", "4.01");
	}
	
	@Test
	public void testPushRecommandUser()throws Exception{
		List<String> list  = new ArrayList<String>();
//		list.add("3274321552");
		service.pushRecommandUser("2097226533", "2.0067kvRCFZjt7D65af25eb320HQTHF",
				1278, "天杰天杰天杰天杰天杰天杰");
//		Thread.sleep(10000);
	}
	
}
