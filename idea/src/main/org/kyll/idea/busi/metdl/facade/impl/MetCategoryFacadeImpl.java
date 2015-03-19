package org.kyll.idea.busi.metdl.facade.impl;

import org.kyll.common.paginated.Dataset;
import org.kyll.common.paginated.Paginated;
import org.kyll.common.paginated.PaginatedHelper;
import org.kyll.idea.busi.metdl.dao.MetCategoryDao;
import org.kyll.idea.busi.metdl.dao.MetDao;
import org.kyll.idea.busi.metdl.facade.MetCategoryFacade;
import org.kyll.idea.busi.metdl.model.MetCategory;
import org.kyll.idea.busi.metdl.model.MetCategoryHis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MetCategoryFacadeImpl implements MetCategoryFacade {
	@Autowired
	private MetCategoryDao metCategoryDao;
	@Autowired
	private MetDao metDao;

	@Override
	public Dataset<MetCategory> getMetCategoryList(Paginated paginated) {
		return PaginatedHelper.makeDateset(paginated, metCategoryDao.selectMetCategoryCount(), metCategoryDao.selectMetCategoryList(paginated));
	}

	@Override
	public List<MetCategory> getMetCategoryList() {
		return metCategoryDao.selectMetCategoryList(null);
	}

	public MetCategory createOrModifyCategory(String name) {
		MetCategory metCategory = null;
		MetCategoryHis metCategoryHis = metCategoryDao.selectMetCategoryHisByMergeName(name);
		if (metCategoryHis == null) {
			metCategory = metCategoryDao.selectMetCategoryByName(name);
			if (metCategory == null) {
				metCategory = new MetCategory();
				metCategory.setName(name);
				metCategory.setId(metCategoryDao.insertMetCategory(metCategory));
			}
		} else {
			metCategory = metCategoryHis.getMetCategory();
		}
		return metCategory;
	}

	@Override
	public void mergeMetCategory(String name, String[] categorys) {
		MetCategory metCategory = this.createOrModifyCategory(name);
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("newCategory", metCategory.getId());
		paramMap.put("oldCategories", categorys);
		metDao.updateMetCategoryByOldCategorys(paramMap);

		for (String category : categorys) {
			if (!metCategory.getId().equals(category)) {
				MetCategory mc = metCategoryDao.selectMetCategoryById(category);

				MetCategoryHis metCategoryHis = new MetCategoryHis();
				metCategoryHis.setMetCategory(metCategory);
				metCategoryHis.setMergeName(mc.getName());
				metCategoryDao.insertMetCategoryHis(metCategoryHis);

				this.removeMetCategory(mc);
			}
		}
	}

	@Override
	public void modifyMetCategoryForSortUp(MetCategory metCategory) {
		metCategory = metCategoryDao.selectMetCategoryById(metCategory.getId());
		MetCategory prevMetCategory = metCategoryDao.selectMetCategoryBySortLessThan(metCategory.getSort());
		this.switchCategoryForSort(metCategory,  prevMetCategory);
	}

	@Override
	public void modifyMetCategoryForSortDown(MetCategory metCategory) {
		metCategory = metCategoryDao.selectMetCategoryById(metCategory.getId());
		MetCategory nextMetCategory = metCategoryDao.selectMetCategoryBySortGreaterThan(metCategory.getSort());
		this.switchCategoryForSort(metCategory,  nextMetCategory);
	}

	private void switchCategoryForSort(MetCategory currentCategory, MetCategory anotherCategory) {
		if (currentCategory == null || anotherCategory == null) {
			return;
		}
		int sort = currentCategory.getSort();
		currentCategory.setSort(anotherCategory.getSort());
		anotherCategory.setSort(sort);
		metCategoryDao.updateMetCategoryById(currentCategory);
		metCategoryDao.updateMetCategoryById(anotherCategory);
	}

	public void removeMetCategory(MetCategory metCategory) {
		metCategoryDao.deleteMetCategoryHisByCategory(metCategory.getId());
		metCategoryDao.deleteMetCategoryById(metCategory.getId());
	}
}
