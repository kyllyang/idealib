package org.kyll.common.util;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public final class XmlUtil {
	public static Document read(String str) {
		return XmlUtil.read(new ByteArrayInputStream(str.getBytes()));
	}

	public static Document read(InputStream in) {
		try {
			return new SAXReader().read(in, "UTF-8");
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String clearXmlHeader(String xml) {
		return xml.replaceAll("[<][\u003F].*[\u003F][>]", "");
	}

	private XmlUtil() {
	}
}
