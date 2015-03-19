package org.kyll.idea.busi.metdl.facade.impl;

import org.kyll.common.paginated.Dataset;
import org.kyll.common.paginated.Paginated;
import org.kyll.common.paginated.PaginatedHelper;
import org.kyll.idea.busi.metdl.dao.MetDao;
import org.kyll.idea.busi.metdl.facade.MetCategoryFacade;
import org.kyll.idea.busi.metdl.facade.MetFacade;
import org.kyll.idea.busi.metdl.handler.SyncHandler;
import org.kyll.idea.busi.metdl.handler.SyncHandlerFactory;
import org.kyll.idea.busi.metdl.model.Met;
import org.kyll.idea.busi.metdl.model.MetImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class MetFacadeImpl implements MetFacade {
	@Autowired
	private MetDao metDao;
	@Autowired
	private MetCategoryFacade metCategoryFacade;

	@Override
	public Dataset<Met> getMetList(Met met, Paginated paginated) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("met", met);
		paramMap.put("paginated", paginated);
		return PaginatedHelper.makeDateset(paginated, metDao.selectMetCount(met), metDao.selectMetList(paramMap));
	}

	@Override
	public MetImage getMetImage(String id) {
		return metDao.selectMetImageById(id);
	}

	@Override
	public void modifyMetStatus(Met met) {
		Met dbMet = metDao.selectMetById(met.getId());
		dbMet.setStatus(met.getStatus());
		metDao.updateMetById(dbMet);
	}

	@Override
	public void syncMet(String urlStr) {
		SyncHandler syncHandler = SyncHandlerFactory.createSyncHandler(urlStr);
		syncHandler.handler(metDao, metCategoryFacade);

		metDao.updateMetUnfinishedStatus();
	}
}
