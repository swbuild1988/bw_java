package com.bandweaver.tunnel.common.platform.util;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;


/**
 * ClassName: xml工具类
 * 
 * @Description: TODO
 * @author shaosen
 * @date 2018年5月25日
 */
public class DocumentUtil {

	private static final String URL_ADDRESS = "/LogConfig.xml";
	
	/**
	 * @Description: 读取xml文档
	 * @param @return
	 * @return Document
	 * @throws ParserConfigurationException
	 * @throws IOException
	 * @throws SAXException
	 * @throws                              
	 * @author shaosen
	 * @date 2018年5月25日
	 */
	public static Document getDocument() throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		// 获取xml文档位置
		InputStream inputStream = DocumentUtil.class.getResourceAsStream(URL_ADDRESS);
		return builder.parse(inputStream);
	}

	
}
