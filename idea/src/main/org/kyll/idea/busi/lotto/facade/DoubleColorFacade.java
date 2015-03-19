package org.kyll.idea.busi.lotto.facade;

import org.kyll.common.paginated.Dataset;
import org.kyll.common.paginated.Paginated;
import org.kyll.idea.busi.lotto.model.DoubleColor;

public interface DoubleColorFacade {
	Dataset<DoubleColor> getDoubleColorList(Paginated paginated);
}
