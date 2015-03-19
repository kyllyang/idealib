package org.kyll.idea.busi.lotto.facade;

import org.kyll.common.paginated.Dataset;
import org.kyll.common.paginated.Paginated;
import org.kyll.idea.busi.lotto.model.DigitalThree;

public interface DigitalThreeFacade {
	Dataset<DigitalThree> getDigitalThreeList(Paginated paginated);

	void syncDigitalThree(String urlStr);
}
