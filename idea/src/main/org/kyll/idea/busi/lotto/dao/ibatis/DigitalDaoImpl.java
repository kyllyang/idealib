package org.kyll.idea.busi.lotto.dao.ibatis;

import org.kyll.common.base.dao.IbatisDao;
import org.kyll.common.paginated.Paginated;
import org.kyll.idea.busi.lotto.dao.DigitalTreeDao;
import org.kyll.idea.busi.lotto.model.DigitalThree;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DigitalDaoImpl extends IbatisDao implements DigitalTreeDao {
	@Override
	public Integer selectDigitalTreeCount() {
		return (Integer) sqlMapClientTemplate.queryForObject("DigitalThree.selectDigitalTreeCount");
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DigitalThree> selectDigitalTreeList(Paginated paginated) {
		return sqlMapClientTemplate.queryForList("DigitalThree.selectDigitalTreeList", paginated);
	}

	@Override
	public DigitalThree selectDigitalTreeByTerm(String term) {
		return (DigitalThree) sqlMapClientTemplate.queryForObject("DigitalThree.selectDigitalTreeByTerm", term);
	}

	@Override
	public String insertDigitalTree(DigitalThree digitalThree) {
		return (String) sqlMapClientTemplate.insert("DigitalThree.insertDigitalThree", digitalThree);
	}
}
