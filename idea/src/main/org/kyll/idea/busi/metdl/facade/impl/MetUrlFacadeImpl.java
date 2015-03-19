package org.kyll.idea.busi.metdl.facade.impl;

import org.kyll.idea.busi.metdl.facade.MetUrlFacade;
import org.kyll.idea.busi.metdl.handler.DownloadHandler;
import org.kyll.idea.busi.metdl.handler.DownloadHandlerFactory;
import org.kyll.idea.busi.metdl.model.MetUrl;
import org.springframework.stereotype.Service;

@Service
public class MetUrlFacadeImpl implements MetUrlFacade {
	@Override
	public void download(MetUrl metUrl) {
		DownloadHandler downloadHandler = DownloadHandlerFactory.createDownloadHandler(metUrl.getUrl());
		try {
			downloadHandler.handler();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
