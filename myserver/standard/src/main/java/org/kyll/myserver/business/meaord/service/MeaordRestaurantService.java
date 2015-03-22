package org.kyll.myserver.business.meaord.service;

import org.kyll.myserver.base.BaseService;
import org.kyll.myserver.base.paginated.Dataset;
import org.kyll.myserver.base.paginated.Paginated;
import org.kyll.myserver.business.meaord.QueryCondition;
import org.kyll.myserver.business.meaord.entity.MeaordRestaurant;

/**
 * User: Kyll
 * Date: 2014-11-14 13:30
 */
public interface MeaordRestaurantService extends BaseService<MeaordRestaurant, Long> {
	Dataset<MeaordRestaurant> get(QueryCondition qc, Paginated pg);

	void save(MeaordRestaurant meaordRestaurant);

	void delete(Long... ids);
}
