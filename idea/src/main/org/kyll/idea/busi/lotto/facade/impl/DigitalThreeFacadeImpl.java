package org.kyll.idea.busi.lotto.facade.impl;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.OrFilter;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import org.kyll.common.paginated.Dataset;
import org.kyll.common.paginated.Paginated;
import org.kyll.common.paginated.PaginatedHelper;
import org.kyll.common.util.DateUtil;
import org.kyll.common.util.StringUtil;
import org.kyll.idea.busi.lotto.dao.DigitalTreeDao;
import org.kyll.idea.busi.lotto.facade.DigitalThreeFacade;
import org.kyll.idea.busi.lotto.model.DigitalThree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

@Service
public class DigitalThreeFacadeImpl implements DigitalThreeFacade {
	@Autowired
	private DigitalTreeDao digitalTreeDao;

	@Override
	public Dataset<DigitalThree> getDigitalThreeList(Paginated paginated) {
		return PaginatedHelper.makeDateset(paginated, digitalTreeDao.selectDigitalTreeCount(), digitalTreeDao.selectDigitalTreeList(paginated));
	}

	@Override
	public void syncDigitalThree(String urlStr) {
		try {
			URL url = new URL(urlStr);
			URLConnection conn = url.openConnection();
			Parser parser = new Parser(conn);
			parser.setEncoding("UTF-8");

			NodeFilter filter5 = new HasAttributeFilter("id", "blue");
			NodeFilter filter6 = new HasAttributeFilter("class", "qh4 bjcolor1 td_b  td_rb");
			NodeFilter filter7 = new HasAttributeFilter("class", "qh4 bjcolor1 td_bb  td_rb");

			NodeFilter filter03 = new AndFilter(filter5, new OrFilter(new NodeFilter[]{filter6, filter7}));

			NodeList tdNodeList = parser.extractAllNodesThatMatch(filter03);
			for (int i = 0; i < tdNodeList.size(); i++) {
				Node tdNode = tdNodeList.elementAt(i);

				String term = "20" + tdNode.getFirstChild().getFirstChild().getText();
				DigitalThree digitalThree = digitalTreeDao.selectDigitalTreeByTerm(term);
				if (digitalThree == null) {
					String aText = tdNode.getFirstChild().getText();
					String date = aText.substring(aText.length() - 10);
					String digital = tdNode.getNextSibling().getNextSibling().getFirstChild().getFirstChild().getText().replace("&nbsp;&nbsp;", ",");
					String[] digitals = StringUtil.split(digital, ",");

					digitalThree = new DigitalThree();
					digitalThree.setDate(DateUtil.parseDate(date));
					digitalThree.setTerm(term);
					digitalThree.setDigital0(Integer.parseInt(digitals[0]));
					digitalThree.setDigital1(Integer.parseInt(digitals[1]));
					digitalThree.setDigital2(Integer.parseInt(digitals[2]));

					digitalTreeDao.insertDigitalTree(digitalThree);
				}
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
