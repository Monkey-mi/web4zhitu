package com.hts.web.ztworld.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.BaseTest;

public class SubtitleServiceTest extends BaseTest {

	private static Logger log = Logger.getLogger(SubtitleServiceTest.class);
	
	
	@Autowired
	private SubtitleService service;
	
	@Test
	public void buildSubtitleTest() throws Exception {
		logNumberList(log, new NumberListAdapter() {

			@Override
			public void buildNumberList(Map<String, Object> jsonMap)
					throws Exception {
				service.buildSubtitle(0, 1, 10, jsonMap);
			}
		});
	}
	
	@Test
	public void test() throws Exception {
		File file = new File("/home/lynch/zhituinfo.json");
		BufferedReader reader = null;
		reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
		String line = null;
		while((line = reader.readLine()) != null) {
			JSONObject jso = JSONObject.fromObject(line);
			System.out.println(jso.getString("android"));
		}
		reader.close();
	}
}
