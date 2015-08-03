package com.hts.web.ztworld.service;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.BaseTest;

public class ZTWorldStickerTest extends BaseTest {

	private static Logger log = Logger.getLogger(ZTWorldStickerTest.class);

	@Autowired
	private ZTWorldStickerService service;

	@Test
	public void buildTopStickerTest() throws Exception {
		logNumberList(log, new NumberListAdapter() {

			@Override
			public void buildNumberList(Map<String, Object> jsonMap) throws Exception {
				service.buildTopSticker(485, jsonMap);
			}

		});
	}

	@Test
	public void buildRecommendStickerTest() throws Exception {
		logNumberList(log, new NumberListAdapter() {

			@Override
			public void buildNumberList(Map<String, Object> jsonMap) throws Exception {
				service.buildRecommendSticker(1595, jsonMap);
			}

		});
	}

	@Test
	public void buildStickerTest() throws Exception {
		logNumberList(log, new NumberListAdapter() {

			@Override
			public void buildNumberList(Map<String, Object> jsonMap) throws Exception {
				service.buildSticker(485, 1, 100, 1, 10, jsonMap);
				service.buildSticker(485, 1, 0, 1, 10, jsonMap);
			}

		});
	}

	@Test
	public void buildMaxStickerIdTest() throws Exception {
		logNumberList(log, new NumberListAdapter() {

			@Override
			public void buildNumberList(Map<String, Object> jsonMap) throws Exception {
				service.buildMaxStickerId(jsonMap);
			}

		});
	}

	@Test
	public void unlockTest() throws Exception {
		service.unlock(2, 22);
	}

	@Test
	public void usedTest() throws Exception {
		service.saveStickerUsed(485, 11);
	}

	@Test
	public void buildLibTest() throws Exception {
		logNumberList(log, new NumberListAdapter() {

			@Override
			public void buildNumberList(Map<String, Object> jsonMap) throws Exception {
				service.buildLib(9, 1, 10, jsonMap);
			}

		});
	}

	// public static void main(String[] args) {
	// try {
	// // 创建HttpClient实例
	// HttpClient httpclient = new DefaultHttpClient();
	// Date date = new Date();
	// String url =
	// "http://abj-stat-1.yunba.io:8100/stat/history/?appkey=540bbe74699da7376d411ddc&seckey=sec-M2Gm429DC29BEW17roszW3rBCkiB7tUFaskboMC0LwUdfckn&start="
	// + date.getTime();
	// // 创建Get方法实例
	// HttpGet httpgets = new HttpGet(url);
	// HttpResponse response = httpclient.execute(httpgets);
	// HttpEntity entity = response.getEntity();
	// System.out.println(entity);
	// new Buffer(payload).toString("utf-8");
	// if (entity != null) {
	// InputStream instreams = entity.getContent();
	// String str = convertStreamToString(instreams);
	// System.out.println("Do something");
	// System.out.println(str);
	// httpgets.abort();
	// }
	// } catch (Exception e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// }
	final static long timecha = 30 * 60 * 1000;

	/**
	 * @param args
	 * @author zhangbo 2015年7月30日
	 */
	public static void main(String[] args) {
		try {// post请求返回结果
			DefaultHttpClient httpClient = new DefaultHttpClient();
			JSONObject jsonResult = null;
			// Date date = getBeforeDate(new Date());
			Date date = new Date();
			System.out.println(date.getTime() - timecha);
			System.out.println(date.getTime());
			String url = "http://abj-stat-1.yunba.io:8100/stat/history/?appkey=54255092ee00dba45fb0d9a2&seckey=sec-6Joi9fj11czDXUSBMLTLN2Ztl3Jm1kTrtxrlwoVj2uN008R2&start="
					+ (date.getTime() - timecha) + "&end=" + date.getTime();
			// String url =
			// "http://abj-stat-1.yunba.io:8100/stat/history/?appkey=54255092ee00dba45fb0d9a2&seckey=sec-6Joi9fj11czDXUSBMLTLN2Ztl3Jm1kTrtxrlwoVj2uN008R2&start=1438157846708&end=1438157906708";

			url = URLDecoder.decode(url, "UTF-8");
			HttpGet method = new HttpGet(url);

			HttpResponse result = httpClient.execute(method);
			System.out.println(result);
			/** 请求发送成功，并得到响应 **/
			if (result.getStatusLine().getStatusCode() == 200) {
				String str = "";
				try {
					/** 读取服务器返回过来的json字符串数据 **/
					str = EntityUtils.toString(result.getEntity());
					// System.out.println(str);
					JSONArray array = new JSONArray(str);
					System.out.println(array.length());
					for (int i = 0; i < array.length(); i++) {
						JSONObject jsonObject = array.getJSONObject(i);
						byte[] stringToByte = byteStr2Byte(jsonObject.getString("payload"));
						System.out.println(i + "------" + new String(stringToByte, "utf-8"));
						// System.out.println(i+"------" + new
						// StringBuffer(jsonObject.get("payload")).toString("utf-8"));
					}
				} catch (Exception e) {
					System.out.println(e);
				}
			}
		} catch (IOException e) {
		}
	}

	private static Date getBeforeDate(Date now) {
		Date dBefore = new Date();
		Calendar calendar = Calendar.getInstance(); // 得到日历
		calendar.setTime(now); // 把当前时间赋给日历
		calendar.add(Calendar.DAY_OF_MONTH, -1); // 设置为前一天
		dBefore = calendar.getTime(); // 得到前一天的时间
		return dBefore;
	}

	public static byte[] byteStr2Byte(String str) {
		String s1 = str.substring(1, str.length() - 1);
		String[] s2 = s1.split(",");
		byte[] b1 = new byte[s2.length];
		for(int i = 0; i < s2.length; i++) {
			b1[i] = (byte)Integer.parseInt(s2[i]);
		}
		return b1;
    }

}
