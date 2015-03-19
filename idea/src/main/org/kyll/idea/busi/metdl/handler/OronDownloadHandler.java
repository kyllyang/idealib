package org.kyll.idea.busi.metdl.handler;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.util.NodeList;
import org.kyll.idea.busi.metdl.CommonUtil;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class OronDownloadHandler implements DownloadHandler {
	private String urlStr;

	public void setUrlStr(String urlStr) {
		this.urlStr = urlStr;
	}

	@Override
	public void handler() throws Exception {
		URL url = new URL(urlStr);
		URLConnection conn = url.openConnection();
		Parser parser = new Parser(conn);
		parser.setEncoding("UTF-8");

		NodeFilter filter1 = new HasAttributeFilter("method", "POST");
		NodeFilter filter2 = new HasAttributeFilter("action", "");
		NodeFilter filter3 = new TagNameFilter("FORM");
		NodeFilter filter01 = new AndFilter(new NodeFilter[]{filter1, filter2, filter3});

		List<NameValuePair> formparams = new ArrayList<NameValuePair>();

		NodeList formNodeList = parser.extractAllNodesThatMatch(filter01);
		for (int i = 0; i < formNodeList.size(); i++) {
			Node formNode = formNodeList.elementAt(i);
			NodeList inputNodeList = formNode.getChildren();
			for (int j = 0; j < inputNodeList.size(); j++) {
				Node inputNode = inputNodeList.elementAt(j);
				String desc = inputNode.toString();
				if (!desc.startsWith("Txt")) {
					String tagStr = inputNode.getText();
					String attrName = CommonUtil.getHtmlTagValueByAttributeName(tagStr, "name");
					String attrValue = CommonUtil.getHtmlTagValueByAttributeName(tagStr, "value");
					formparams.add(new BasicNameValuePair(attrName, attrValue));
				}
			}
		}

		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, "UTF-8");
		HttpPost httpPost = new HttpPost(urlStr);
		httpPost.setEntity(entity);

		HttpClient httpClient = new DefaultHttpClient();

		ResponseHandler<byte[]> byteArrayResponseHandler = new ResponseHandler<byte[]>() {
			@Override
			public byte[] handleResponse(HttpResponse httpResponse) throws ClientProtocolException, IOException {
				return EntityUtils.toByteArray(httpResponse.getEntity());
			}
		};
		byte[] responseBytes = httpClient.execute(httpPost, byteArrayResponseHandler);

		parser = new Parser(new String(responseBytes));

		NodeFilter filter4 = new HasAttributeFilter("name", "F1");
		NodeFilter filter5 = new HasAttributeFilter("method", "POST");
		NodeFilter filter6 = new HasAttributeFilter("action", "");
		NodeFilter filter7 = new TagNameFilter("FORM");
		NodeFilter filter02 = new AndFilter(new NodeFilter[]{filter4, filter5, filter6, filter7});

		formparams.clear();
		formparams.add(new BasicNameValuePair("down_direct", "1"));

		formNodeList = parser.extractAllNodesThatMatch(filter02);
		for (int i = 0; i < formNodeList.size(); i++) {
			Node formNode = formNodeList.elementAt(i);
			NodeList inputNodeList = formNode.getChildren();
			for (int j = 0; j < inputNodeList.size(); j++) {
				Node inputNode = inputNodeList.elementAt(j);
				String desc = inputNode.toString();
				if (!desc.startsWith("Txt") && desc.contains("input type=")) {
					String tagStr = inputNode.getText();
					String attrName = CommonUtil.getHtmlTagValueByAttributeName(tagStr, "name");
					String attrValue = CommonUtil.getHtmlTagValueByAttributeName(tagStr, "value");
					formparams.add(new BasicNameValuePair(attrName, attrValue));
				}
			}
		}

		entity = new UrlEncodedFormEntity(formparams, "UTF-8");
		httpPost = new HttpPost(urlStr);
		httpPost.setEntity(entity);
		responseBytes = httpClient.execute(httpPost, byteArrayResponseHandler);
		System.out.println(new String(responseBytes));
	}
}
