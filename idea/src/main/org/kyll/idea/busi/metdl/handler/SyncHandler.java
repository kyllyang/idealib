package org.kyll.idea.busi.metdl.handler;

import org.kyll.idea.busi.metdl.dao.MetDao;
import org.kyll.idea.busi.metdl.facade.MetCategoryFacade;

public interface SyncHandler {
	void setUrlStr(String url);
	void handler(MetDao metDao, MetCategoryFacade metCategoryFacade);
}
