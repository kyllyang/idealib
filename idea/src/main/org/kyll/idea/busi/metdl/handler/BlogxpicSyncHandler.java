package org.kyll.idea.busi.metdl.handler;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.*;
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

public class BlogxpicSyncHandler implements SyncHandler {
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
			urlStr = "http://xpic.ucoz.com/blog";
		//	urlStr = "http://xpic.ucoz.com/blog/2";
		}

		if (StringUtil.isNotEmpty(prevUrlStr)) {
			urlStr = prevUrlStr;
		}

		this.metDao = metDao;
		this.metCategoryFacade = metCategoryFacade;

		this.syncMet(urlStr);
	}

	private synchronized void syncMet(String urlStr) {
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

			NodeFilter divTagFilter = new TagNameFilter("DIV");
			NodeFilter imgTagFilter = new TagNameFilter("IMG");
			NodeFilter aTagFilter = new TagNameFilter("A");
			NodeFilter filter1 = new HasAttributeFilter("id", "allEntries");
			NodeFilter filter2 = new HasChildFilter(imgTagFilter);

			NodeFilter filter01 = new AndFilter(new NodeFilter[]{divTagFilter, filter1});
			NodeFilter filter02 = new NotFilter(filter2);
			NodeFilter filter03 = new AndFilter(new NodeFilter[]{aTagFilter, filter02});

			boolean isContinue = true;

			NodeList entryNodeList = parser.extractAllNodesThatMatch(filter01).elementAt(0).getChildren();
			for (int i = 0; i < entryNodeList.size(); i++) {
				Node entryNode = entryNodeList.elementAt(i);
				String divTagStr = entryNode.getText();

				String divId = CommonUtil.getHtmlTagValueByAttributeName(divTagStr, "id");
				if (divId.matches("^entryID[0-9]+$")) {
					NodeList nodeList = entryNode.getFirstChild().getFirstChild().getFirstChild().getChildren();
					Node eTitleNode = nodeList.elementAt(1);
					Node eMessageNode = nodeList.elementAt(3);
					Node eDetailsNode = nodeList.elementAt(5);

					String name = eTitleNode.getFirstChild().getFirstChild().getText();
					Met dbMet = metDao.selectMetByName(name);
					if (dbMet != null) {
						isContinue = false;
					//	isContinue = true;
						break;
					}

					String categoryName = eDetailsNode.getChildren().elementAt(3).getFirstChild().getText();
					String imgTag;
					NodeList imgNodeList = eMessageNode.getChildren().extractAllNodesThatMatch(imgTagFilter);
					if (imgNodeList.size() == 0) {
						NodeList imgNodeList2 = eMessageNode.getChildren().extractAllNodesThatMatch(filter2);
						imgTag = imgNodeList2.elementAt(0).getFirstChild().getText();
					} else {
						imgTag = imgNodeList.elementAt(0).getText();
					}
					String imageUrl = CommonUtil.getHtmlTagValueByAttributeName(imgTag, "src");

					MetImage metImage = null;
					if (StringUtil.isNotEmpty(imageUrl)) {
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
					met.setUploadTime(new Date());
					met.setUpdateTime(new Date());
					met.setStatus(Met.STATUS_NEW);
					met.setMetCategory(metCategoryFacade.createOrModifyCategory(categoryName));
					met.setImageUrl(imageUrl);
					met.setMetImage(metImage);
					met.setId(metDao.insertMet(met));
					System.out.println(met);

					NodeList aNodeList = eMessageNode.getChildren().extractAllNodesThatMatch(filter03);
					for (int j = 0; j < aNodeList.size(); j++) {
						String aTag = aNodeList.elementAt(j).getText();
						String href = CommonUtil.getHtmlTagValueByAttributeName(aTag, "href");

						MetUrl metUrl = new MetUrl();
						metUrl.setUrl(href);
						metUrl.setMet(met);
						metDao.insertUrl(metUrl);
					}
				}
			}

			if (isContinue) {
				Node pageDivNode = entryNodeList.elementAt(entryNodeList.size() - 1);
				NodeList aNodeList = pageDivNode.getChildren().extractAllNodesThatMatch(aTagFilter);
				if (aNodeList.size() > 0) {
					String pageStr = aNodeList.elementAt(aNodeList.size() - 1).getFirstChild().getFirstChild().getText();
					if ("&raquo;".equals(pageStr)) {
						String nextAStr = pageDivNode.getLastChild().getText();
						String href = CommonUtil.getHtmlTagValueByAttributeName(nextAStr, "href");
						this.syncMet(href);
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
