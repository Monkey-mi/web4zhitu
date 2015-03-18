package com.hts.web.ztworld.service;

import java.util.Map;

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
}
