package org.kyll.idea.busi.lotto.facade.impl;

import org.kyll.common.paginated.Dataset;
import org.kyll.common.paginated.Paginated;
import org.kyll.idea.busi.lotto.dao.DoubleColorDao;
import org.kyll.idea.busi.lotto.facade.DoubleColorFacade;
import org.kyll.idea.busi.lotto.model.DoubleColor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DoubleColorFacadeImpl implements DoubleColorFacade {
	@Autowired
	private DoubleColorDao doubleColorDao;

	@Override
	public Dataset<DoubleColor> getDoubleColorList(Paginated paginated) {
		return null;
	}
}
