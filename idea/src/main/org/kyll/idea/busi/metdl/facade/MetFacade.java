package org.kyll.idea.busi.metdl.facade;

import org.kyll.common.paginated.Dataset;
import org.kyll.common.paginated.Paginated;
import org.kyll.idea.busi.metdl.model.Met;
import org.kyll.idea.busi.metdl.model.MetImage;

public interface MetFacade {
	Dataset<Met> getMetList(Met met, Paginated paginated);

	void modifyMetStatus(Met met);

	void syncMet(String urlStr);

	MetImage getMetImage(String id);
}
