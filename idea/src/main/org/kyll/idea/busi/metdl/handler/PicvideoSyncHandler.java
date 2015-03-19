package org.kyll.idea.busi.metdl.handler;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.HasChildFilter;
import org.htmlparser.filters.NotFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import org.kyll.common.util.ByteUtil;
import org.kyll.common.util.DateUtil;
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

public class PicvideoSyncHandler implements SyncHandler {
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
			urlStr = "http://xpf.blog128.fc2blog.net/";
		//	urlStr = "http://xpf.blog128.fc2blog.net/page-5.html";
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

			NodeFilter filter1 = new HasAttributeFilter("class", "content");
			NodeFilter filter2 = new TagNameFilter("DIV");
			NodeFilter filter3 = new HasAttributeFilter("class", "e_header");
			NodeFilter filter4 = new TagNameFilter("H2");
			NodeFilter filter5 = new HasAttributeFilter("class", "e_body");
			NodeFilter filter6 = new TagNameFilter("DIV");
			NodeFilter filter7 = new HasAttributeFilter("class", "e_footer");
			NodeFilter filter8 = new TagNameFilter("UL");
			NodeFilter filter9 = new HasChildFilter(new AndFilter(filter3, filter4));
			NodeFilter filter10 = new HasChildFilter(new AndFilter(filter5, filter6));
			NodeFilter filter11 = new HasChildFilter(new AndFilter(filter7, filter8));
			NodeFilter filter12 = new HasAttributeFilter("target", "_blank");
			NodeFilter filter13 = new TagNameFilter("A");
			NodeFilter filter14 = new NotFilter(new HasChildFilter(new TagNameFilter("IMG")));
			NodeFilter filter15 = new TagNameFilter("LI");
			NodeFilter filter16 = new TagNameFilter("IMG");
			NodeFilter filter17 = new HasAttributeFilter("class", "e_footer_back");
			NodeFilter filter18 = new TagNameFilter("DIV");
			NodeFilter filter19 = new HasAttributeFilter("class", "navi");
			NodeFilter filter20 = new TagNameFilter("DIV");

			NodeFilter filter01 = new AndFilter(new NodeFilter[]{filter1, filter2});
			NodeFilter filter02 = new AndFilter(filter3, filter4);
			NodeFilter filter03 = new AndFilter(filter5, filter6);
			NodeFilter filter04 = new AndFilter(new NodeFilter[]{filter12, filter13, filter14});
			NodeFilter filter05 = new AndFilter(filter7, filter8);
			NodeFilter filter06 = new AndFilter(filter17, filter18);
			NodeFilter filter07 = new AndFilter(new NodeFilter[]{filter19, filter20});

			boolean isContinue = true;

			NodeList divNodeList = parser.extractAllNodesThatMatch(filter01);
			for (int i = 0; i < divNodeList.size(); i++) {
				Node divNode = divNodeList.elementAt(i);
				NodeList childrenNodeList = divNode.getChildren();
				NodeList headerNodeList = childrenNodeList.extractAllNodesThatMatch(filter02);
				NodeList bodyNodeList = childrenNodeList.extractAllNodesThatMatch(filter03);
				NodeList footerNodeList = childrenNodeList.extractAllNodesThatMatch(filter05);

				String name = headerNodeList.elementAt(0).getFirstChild().getText();

				Met dbMet = metDao.selectMetByName(name);
				if (dbMet != null) {
				//	isContinue = false;
				//	break;
					isContinue = true;
					continue;
				}

				NodeList liNodeList = footerNodeList.elementAt(0).getChildren().elementAt(1).getChildren();
				String uploadTime = liNodeList.elementAt(1).getFirstChild().getText();
				Date uploadDate = DateUtil.parseDate(uploadTime.substring(0, 10), "yyyy/MM/dd");
				String categoryName = liNodeList.elementAt(3).getFirstChild().getFirstChild().getText();

				String tagStr = null;
				NodeList imgList = bodyNodeList.elementAt(0).getChildren().extractAllNodesThatMatch(filter16);
				if (imgList == null|| imgList.size() == 0) {
					try {
						imgList = bodyNodeList.elementAt(0).getChildren().elementAt(1).getChildren().extractAllNodesThatMatch(filter16);
					} catch (Exception e) {
					}
				}
				if (imgList != null && imgList.size() != 0) {
					tagStr = imgList.elementAt(0).getText();
				}

				String imageUrl = CommonUtil.getHtmlTagValueByAttributeName(tagStr, "src");
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
				met.setUploadTime(uploadDate);
				met.setUpdateTime(new Date());
				met.setStatus(Met.STATUS_NEW);
				met.setMetCategory(metCategoryFacade.createOrModifyCategory(categoryName));
				met.setImageUrl(imageUrl);
				met.setMetImage(metImage);
				met.setId(metDao.insertMet(met));
				System.out.println(met);

				NodeList aNodeList = bodyNodeList.elementAt(0).getChildren().extractAllNodesThatMatch(filter04);
				for (int j = 0; j < aNodeList.size(); j++) {
					String address = aNodeList.elementAt(j).getChildren().elementAt(0).getText();

					MetUrl metUrl = new MetUrl();
					metUrl.setUrl(address);
					metUrl.setMet(met);
					metDao.insertUrl(metUrl);
				}
			}

			if (isContinue) {
				parser.reset();
				NodeList aNodeList = parser.extractAllNodesThatMatch(filter07).elementAt(0).getChildren().extractAllNodesThatMatch(filter13);
				String aTitle = aNodeList.elementAt(aNodeList.size() - 1).getText();
				if ("下一页".equals(CommonUtil.getHtmlTagValueByAttributeName(aTitle, "title"))) {
					this.syncMet(CommonUtil.getHtmlTagValueByAttributeName(aTitle, "href"));
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
