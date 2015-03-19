package org.kyll.idea.busi.metdl.facade;

import org.kyll.common.paginated.Dataset;
import org.kyll.common.paginated.Paginated;
import org.kyll.idea.busi.metdl.model.MetCategory;

import java.util.List;

public interface MetCategoryFacade {
	Dataset<MetCategory> getMetCategoryList(Paginated paginated);

	List<MetCategory> getMetCategoryList();

	MetCategory createOrModifyCategory(String name);

	void modifyMetCategoryForSortUp(MetCategory metCategory);

	void modifyMetCategoryForSortDown(MetCategory metCategory);

	void mergeMetCategory(String name, String[] categorys);

	void removeMetCategory(MetCategory metCategory);
}
