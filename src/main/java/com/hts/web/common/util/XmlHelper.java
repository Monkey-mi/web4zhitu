package com.hts.web.common.util;

import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * XML解析类
 * 
 * 创建时间：2012-10-19
 * @author ztj
 *
 */
public class XmlHelper {
	static DocumentBuilder builder = null;
	
	static{
		try{
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance(); 
			builder = factory.newDocumentBuilder();
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}
	
	public static Document buildDocument(String file){
		try{
			synchronized(builder){
				return builder.parse(file);
			}
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}
	
	public static List<Element> getChildEles(Element e, String nodeName){
		NodeList nl = e.getChildNodes();
		ArrayList<Element> list = new ArrayList<Element>();
		for(int i = 0; i < nl.getLength(); i ++){
			Node n = nl.item(i);
			if(n instanceof Element){
				Element et = (Element) n;
				if(nodeName != null && !nodeName.isEmpty()){
					if(et.getTagName().equals(nodeName)){
						list.add(et);
					}
				}else{
					list.add(et);
				}
			}
		}
		return list;
	}
	
	public static List<Element> getChildEles(Element e){
		return getChildEles(e, null);
	}
	
	public static Element getChildEle(Element e, String nodeName){
		List<Element> list = getChildEles(e, nodeName);
		if(list.size() > 0)
			return list.get(0);
		else
			return null;
	}
	
	public static Element getChildEle(Element e){
		return getChildEle(e, null);
	}
	
	public static String getChildEleAttr(Element e, String childEleNN, String attr){
		Element child = getChildEle(e, childEleNN);
		if(child == null)
			return null;
		return child.getAttribute(attr);
	}
	
	public static Integer getChildEleAttr_Int(Element e, String childEleNN, String attr){
		String s = getChildEleAttr(e, childEleNN, attr);
		if(s == null)
			return null;
		else
			return Integer.parseInt(s);
	}
	
	public static Long getChildEleAttr_Long(Element e, String childEleNN, String attr){
		String s = getChildEleAttr(e, childEleNN, attr);
		if(s == null)
			return null;
		else
			return Long.parseLong(s);
	}
	
	public static Double getChildEleAttr_Double(Element e, String childEleNN, String attr){
		String s = getChildEleAttr(e, childEleNN, attr);
		if(s == null)
			return null;
		else
			return Double.parseDouble(s);
	}
	
	public static String getChildEleContent(Element e, String childEleNN){
		Element child = getChildEle(e, childEleNN);
		if(child == null)
			return null;
		return child.getNodeName();
	}
	
	public static Integer getChildEleContent_Int(Element e, String childEleNN){
		String s = getChildEleContent(e, childEleNN);
		if(s == null)
			return null;
		return Integer.parseInt(s);
	}
	
	public static Long getChildEleContent_Long(Element e, String childEleNN){
		String s = getChildEleContent(e, childEleNN);
		if(s == null)
			return null;
		return Long.parseLong(s);
	}
	
	public static Double getChildEleContent_Double(Element e, String childEleNN){
		String s = getChildEleContent(e, childEleNN);
		if(s == null)
			return null;
		return Double.parseDouble(s);
	}
	
	public static String getNodeAttr(NamedNodeMap attrMap, String name) throws NullPointerException {
		Node attrNode = attrMap.getNamedItem(name);
		if(attrNode != null) {
			String attr = attrNode.getNodeValue();
			if(attr != null && !attr.equals("")) {
				return attr;
			}
		}
		throw new NullPointerException("没有设置" + name + "属性");
	}
	
}
