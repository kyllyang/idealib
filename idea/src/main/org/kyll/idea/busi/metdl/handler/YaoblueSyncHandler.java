package org.kyll.idea.busi.metdl.handler;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import org.kyll.common.util.ByteUtil;
import org.kyll.common.util.StringUtil;
import org.kyll.idea.busi.metdl.CommonUtil;
import org.kyll.idea.busi.metdl.dao.MetDao;
import org.kyll.idea.busi.metdl.facade.MetCategoryFacade;
import org.kyll.idea.busi.metdl.model.Met;
import org.kyll.idea.busi.metdl.model.MetImage;
import org.kyll.idea.busi.metdl.model.MetUrl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;

public class YaoblueSyncHandler implements SyncHandler {
	private MetDao metDao;
	private MetCategoryFacade metCategoryFacade;
	private String urlStr;
	private static int currentCount = 1;
	private final static int maxCount = 1;
	private static String prevUrlStr = null;

	@Override
	public void setUrlStr(String url) {
		this.urlStr = url;
	}

	@Override
	public void handler(MetDao metDao, MetCategoryFacade metCategoryFacade) {
		if (StringUtil.isEmpty(urlStr)) {
		//	urlStr = "http://yaoblue.over-blog.com/";
			urlStr = "http://yaoblue.over-blog.com/96-index.html";
		}

		if (StringUtil.isNotEmpty(prevUrlStr)) {
			urlStr = prevUrlStr;
		}

		this.metDao = metDao;
		this.metCategoryFacade = metCategoryFacade;

		this.syncMet(urlStr);
	}

	private void syncMet(String urlStr) {
		if (currentCount++ > maxCount){
			prevUrlStr = urlStr;
			currentCount = 1;
			System.out.println("continue");
			return;
		} else {
			prevUrlStr = null;
		}

		System.out.println(urlStr);

		try {
			URL url = new URL(urlStr);
			URLConnection conn = url.openConnection();
			Parser parser = new Parser(conn);
			parser.setEncoding("UTF-8");

			NodeFilter filter1 = new TagNameFilter("DIV");
			NodeFilter filter2 = new HasAttributeFilter("class", "divTitreArticle");

			NodeFilter filter3 = new TagNameFilter("B");
			NodeFilter filter4 = new HasAttributeFilter("class", "currentPage");

			NodeFilter filter5 = new TagNameFilter("A");
			NodeFilter filter6 = new TagNameFilter("SPAN");

			NodeFilter filter01 = new AndFilter(new NodeFilter[]{filter1, filter2});
			NodeFilter filter02 = new AndFilter(filter3, filter4);

			boolean isContinue = true;

			NodeList divNodeList = parser.extractAllNodesThatMatch(filter01);
			for (int i = 0; i < divNodeList.size(); i++) {
				Node divNode = divNodeList.elementAt(i);
				String name = divNode.getChildren().elementAt(1).getChildren().elementAt(1).getFirstChild().getText().trim();

				Met dbMet = metDao.selectMetByName(name);
				if (dbMet != null) {
				//	isContinue = false;
				//	break;
					isContinue = true;
					continue;
				}

				String categoryName = divNode.getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getChildren().elementAt(3).getChildren().elementAt(3).getFirstChild().getText().trim();
				// html...<div class="contenuArticle"> / <>
				String tagStr = null;
				try {
					tagStr = divNode.getNextSibling().getNextSibling().getChildren().elementAt(1).getChildren().elementAt(1).getLastChild().getText().trim();
				} catch (Exception e) {
					try {
						tagStr = divNode.getNextSibling().getNextSibling().getChildren().elementAt(5).getChildren().elementAt(1).getLastChild().getText().trim();
					} catch (Exception e1) {
						tagStr = divNode.getNextSibling().getNextSibling().getChildren().elementAt(3).getChildren().elementAt(1).getLastChild().getText().trim();
					}
				}
				String imageUrl = CommonUtil.getHtmlTagValueByAttributeName(tagStr, "src");

				MetImage metImage = null;
				if (StringUtil.isNotEmpty(imageUrl)) {
					String website = "http://img.over-blog.com/";
					if (imageUrl.contains(website)) {
						String replace = "http://idata.over-blog.com/";
						imageUrl = imageUrl.substring(imageUrl.indexOf(website) + website.length());
						imageUrl = imageUrl.substring(imageUrl.indexOf('/') + 1);
						imageUrl = replace + imageUrl;
					}

					byte[] image = ByteUtil.fromHttp(imageUrl);
					if (image == null) {
						System.out.println(imageUrl);
					} else {
						metImage = new MetImage();
						metImage.setContent(image);
						metImage.setId(metDao.insertMetImage(metImage));
					}
				}

				Met met = new Met();
				met.setName(name);
				met.setUploadTime(new Date());// upload date parse is hard, ignore!
				met.setUpdateTime(new Date());
				met.setStatus(Met.STATUS_NEW);
				met.setMetCategory(metCategoryFacade.createOrModifyCategory(categoryName));
				met.setImageUrl(imageUrl);
				met.setMetImage(metImage);
				met.setId(metDao.insertMet(met));
				System.out.println(met);

				NodeList aNodeList = null;
				try {
					aNodeList = divNode.getNextSibling().getNextSibling().getChildren().elementAt(5).getChildren().elementAt(1).getChildren();
				} catch (Exception e) {
					aNodeList = divNode.getNextSibling().getNextSibling().getChildren().elementAt(7).getChildren().elementAt(1).getChildren();
				}

				if (aNodeList == null) {
				//	divNode.getNextSibling().getNextSibling().getChildren().extractAllNodesThatMatch()
					aNodeList = divNode.getNextSibling().getNextSibling().getChildren().elementAt(7).getChildren().elementAt(1).getChildren();
				}

				for (int j = 0; j < aNodeList.size(); j++) {
					String address = aNodeList.elementAt(j).getText();

					MetUrl metUrl = new MetUrl();
					metUrl.setUrl(CommonUtil.getHtmlTagValueByAttributeName(address, "href"));
					metUrl.setMet(met);
					metDao.insertUrl(metUrl);
				}
			}

			if (isContinue) {
				parser.reset();

				NodeList currentNodeList = parser.extractAllNodesThatMatch(filter02);
				NodeList nodeList = currentNodeList.elementAt(0).getParent().getChildren();
				for (int i = 0; i < nodeList.size(); i++) {
					if ("next page".equals(nodeList.elementAt(i).getText().trim())) {
						Node nextNode = nodeList.elementAt(i).getNextSibling().getNextSibling();
						this.syncMet(CommonUtil.getHtmlTagValueByAttributeName(nextNode.getText(), "href"));
						break;
					}
				}
			} else {
				System.out.println("finished");
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParserException e) {
			e.printStackTrace();
		}
	}
}
