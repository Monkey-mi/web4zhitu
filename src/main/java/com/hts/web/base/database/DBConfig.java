package com.hts.web.base.database;

import org.w3c.dom.Element;

import com.hts.web.common.util.XmlHelper;

/**
 * 数据库配置抽象，弃用，使用Spring框架进行统一配置管理
 * 
 * @author ztj
 *
 */
@Deprecated
public class DBConfig {
	final String name;
	final String dirver;
	final String url;
	final String user;
	final String pwd;

	public DBConfig(String name,String dirver, String url, String user, String pwd) {
		this.name = name;
		this.dirver = dirver;
		this.url = url;
		this.user = user;
		this.pwd = pwd;
	}

	/**
	 * 解析数据库配置文件
	 * 
	 * @param e
	 * @return
	 */
	public static DBConfig parse(Element e) {
		String name = XmlHelper.getChildEleContent(e, "name");
		String driver = XmlHelper.getChildEleContent(e, "driver");
		String url = XmlHelper.getChildEleContent(e, "url");
		String user = XmlHelper.getChildEleContent(e, "user");
		String pwd = XmlHelper.getChildEleContent(e, "pwd");
		return new DBConfig(name,driver, url, user, pwd);
	}
}
