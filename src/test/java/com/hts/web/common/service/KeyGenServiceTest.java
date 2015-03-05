package com.hts.web.common.service;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.BaseTest;

public class KeyGenServiceTest extends BaseTest {

	private static Logger log = Logger.getLogger(KeyGenServiceTest.class);
	
	@Autowired
	private KeyGenService service;
	
	@Test
	public void generateIdTest() throws Exception {
//		final Set<Integer> s = new HashSet<Integer>();
//		for(int i = 0; i < 10000; i++) {
//			final int count = i;
//			new Thread(new Runnable() {
//
//				@Override
//				public void run() {
//					for(int i = 0; i < 100; i++) {
//						Integer id = service.generateId(2);
//						log.info("thread" + count + " : " + id);
//						if(!s.contains(id)) {
//							s.add(id);
//						} else {
//							log.warn("草泥马");
//						}
//					}
//				}
//			}).start();
//		}
//		Thread.sleep(10000);
		Integer id = service.generateId(2);
		System.out.println(id);
	}
}
