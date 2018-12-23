package com.bandweaver.tunnel.common.platform.log;

import java.io.IOException;
import java.text.MessageFormat;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.alibaba.fastjson.JSONObject;
import com.bandweaver.tunnel.common.biz.pojo.OperationLog;
import com.bandweaver.tunnel.common.platform.util.DocumentUtil;

public class LogHandler {

	/**
	 * @Description: 解析xml并获取数据
	 * @param @param value logConfig.xml operation标签的id 也是@writeLog注解的属性值
	 * @param @return
	 * @param @throws ParserConfigurationException
	 * @param @throws SAXException
	 * @param @throws IOException   
	 * @return JSONObject  
	 * @throws
	 * @author shaosen
	 * @date 2018年5月25日
	 */
	public static JSONObject parseXML(String id) 
			throws ParserConfigurationException, SAXException, IOException {
		
		String LogOperationCategoryID = id.substring(0,3);	//LogOperationCategory标签的id
		
		Document document = DocumentUtil.getDocument();
		NodeList nodeList = document.getElementsByTagName("LogOperationCategory");	//获取LogOperationCategory标签

		JSONObject jsonObject = new JSONObject();
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			if (node instanceof Element) {
				Element e = (Element) node;
				if (e.getAttribute("cid").equals(LogOperationCategoryID)) {
					
					String cname = e.getAttribute("cname");	//获取标签<LogOperationCategory>的cname属性值
					jsonObject.put("cname", cname);
					
					NodeList operationNodeList = e.getElementsByTagName("Operation");	//获取operation标签
					for (int j = 0; j < operationNodeList.getLength(); j++) {
						Node operationNode = operationNodeList.item(j);
						if (operationNode instanceof Element) {
							Element el = (Element) operationNode;
							if(el.getAttribute("oid").equals(id)) {
								String desc = el.getAttribute("desc");	//描述
								jsonObject.put("desc", desc);
								String oname = el.getAttribute("oname");
								jsonObject.put("oname", oname);
							}
						}
					}

				}
			}

		}

		return jsonObject;
	}

	/**
	 * @Description: 根据模块类别不同分别处理
	 * @param @param id
	 * @param @param optLog
	 * @param @return   
	 * @return String  
	 * @throws
	 * @author shaosen
	 * @date 2018年5月25日
	 * 
	 * 
	 * {0}:请求用户
	 * {1}:请求ip
	 * {2}:请求参数
	 * {3}：请求方法
	 * {4}：操作时间
	 * {5}：模块类型
	 * {6}：
	 */
	public static String getDesc(String id,OperationLog optLog) {
		return MessageFormat.format(optLog.getDesc(), 
				optLog.getReqUser(),
				optLog.getReqIp(),
				optLog.getParams(),
				optLog.getMethod(),
				optLog.getCrtTime(),
				optLog.getModuleType());
	}
	
}
