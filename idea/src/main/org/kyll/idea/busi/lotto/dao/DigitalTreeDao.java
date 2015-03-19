package org.kyll.idea.busi.lotto.dao;

import org.kyll.common.paginated.Paginated;
import org.kyll.idea.busi.lotto.model.DigitalThree;

import java.util.List;

public interface DigitalTreeDao {
	Integer selectDigitalTreeCount();

	List<DigitalThree> selectDigitalTreeList(Paginated paginated);

	String insertDigitalTree(DigitalThree digitalThree);

	DigitalThree selectDigitalTreeByTerm(String term);
}
