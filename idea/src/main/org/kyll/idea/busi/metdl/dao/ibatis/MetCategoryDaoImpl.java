package org.kyll.idea.busi.metdl.dao.ibatis;

import org.kyll.common.base.dao.IbatisDao;
import org.kyll.common.paginated.Paginated;
import org.kyll.idea.busi.metdl.dao.MetCategoryDao;
import org.kyll.idea.busi.metdl.model.MetCategory;
import org.kyll.idea.busi.metdl.model.MetCategoryHis;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MetCategoryDaoImpl extends IbatisDao implements MetCategoryDao {
	@Override
	public Integer selectMetCategoryCount() {
		return (Integer) sqlMapClientTemplate.queryForObject("MetCategory.selectMetCategoryCount");
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MetCategory> selectMetCategoryList(Paginated paginated) {
		return sqlMapClientTemplate.queryForList("MetCategory.selectMetCategoryList", paginated);
	}

	@Override
	public MetCategory selectMetCategoryById(String id) {
		return (MetCategory) sqlMapClientTemplate.queryForObject("MetCategory.selectMetCategoryById", id);
	}

	@Override
	public MetCategory selectMetCategoryByName(String name) {
		return (MetCategory) sqlMapClientTemplate.queryForObject("MetCategory.selectMetCategoryByName", name);
	}

	@Override
	public MetCategoryHis selectMetCategoryHisByMergeName(String name) {
		return (MetCategoryHis) sqlMapClientTemplate.queryForObject("MetCategory.selectMetCategoryHisByName", name);
	}

	@Override
	public MetCategory selectMetCategoryBySortLessThan(Integer sort) {
		return (MetCategory) sqlMapClientTemplate.queryForObject("MetCategory.selectMetCategoryBySortLessThan", sort);
	}

	@Override
	public MetCategory selectMetCategoryBySortGreaterThan(Integer sort) {
		return (MetCategory) sqlMapClientTemplate.queryForObject("MetCategory.selectMetCategoryBySortGreaterThan", sort);
	}

	@Override
	public String insertMetCategory(MetCategory metCategory) {
		return (String) sqlMapClientTemplate.insert("MetCategory.insertMetCategory", metCategory);
	}

	@Override
	public String insertMetCategoryHis(MetCategoryHis metCategoryHis) {
		return (String) sqlMapClientTemplate.insert("MetCategory.insertMetCategoryHis", metCategoryHis);
	}

	@Override
	public void updateMetCategoryById(MetCategory metCategory) {
		sqlMapClientTemplate.update("MetCategory.updateMetCategoryById", metCategory);
	}

	@Override
	public void deleteMetCategoryById(String id) {
		sqlMapClientTemplate.delete("MetCategory.deleteMetCategoryById", id);
	}

	@Override
	public void deleteMetCategoryHisByCategory(String id) {
		sqlMapClientTemplate.delete("MetCategory.deleteMetCategoryHisByCategory", id);
	}
}
