package org.kyll.idea.busi.metdl.dao;

import org.kyll.common.paginated.Paginated;
import org.kyll.idea.busi.metdl.model.MetCategory;
import org.kyll.idea.busi.metdl.model.MetCategoryHis;

import java.util.List;

public interface MetCategoryDao {
	Integer selectMetCategoryCount();

	List<MetCategory> selectMetCategoryList(Paginated paginated);

	MetCategory selectMetCategoryById(String id);

	MetCategory selectMetCategoryByName(String name);

	MetCategoryHis selectMetCategoryHisByMergeName(String name);

	MetCategory selectMetCategoryBySortLessThan(Integer sort);

	MetCategory selectMetCategoryBySortGreaterThan(Integer sort);

	String insertMetCategory(MetCategory metCategory);

	String insertMetCategoryHis(MetCategoryHis metCategoryHis);

	void updateMetCategoryById(MetCategory currentCategory);

	void deleteMetCategoryById(String id);

	void deleteMetCategoryHisByCategory(String id);
}
