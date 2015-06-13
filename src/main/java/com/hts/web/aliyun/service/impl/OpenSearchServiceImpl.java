package com.hts.web.aliyun.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hts.web.aliyun.dao.OsMishuDao;
import com.hts.web.aliyun.service.OpenSearchService;

@Service("OpenSearchService")
public class OpenSearchServiceImpl implements OpenSearchService{
	
	@Autowired
	private OsMishuDao mishuDao;
	
	@Override
	public String searchAnswer(String question) throws Exception {
		if(null == question || "".equals(question.trim())){
			return null;
		}
		return mishuDao.searchAnswer(question);
	}

}
