package org.kyll.myserver.business.meaord.service.impl;

import org.apache.commons.lang.StringUtils;
import org.kyll.myserver.base.paginated.Dataset;
import org.kyll.myserver.base.paginated.Paginated;
import org.kyll.myserver.business.meaord.QueryCondition;
import org.kyll.myserver.business.meaord.dao.MeaordRestaurantDao;
import org.kyll.myserver.business.meaord.entity.MeaordRestaurant;
import org.kyll.myserver.business.meaord.service.MeaordRestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * User: Kyll
 * Date: 2014-11-14 13:30
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class MeaordRestaurantServiceImpl implements MeaordRestaurantService {
	@Autowired
	private MeaordRestaurantDao meaordRestaurantDao;

	@Override
	public MeaordRestaurant get(Long id) {
		return meaordRestaurantDao.get(id);
	}

	@Override
	public Dataset<MeaordRestaurant> get(QueryCondition qc, Paginated pg) {
		StringBuilder hql = new StringBuilder("from MeaordRestaurant t where 1 = 1");
		if (qc != null) {
			String name = qc.getName();
			if (StringUtils.isNotBlank(name)) {
				hql.append(" and lower(t.name) like lower('%").append(name).append("%')");
			}
			String linkman = qc.getLinkman();
			if (StringUtils.isNotBlank(linkman)) {
				hql.append(" and lower(t.linkman) like lower('%").append(linkman).append("%')");
			}
			String phone = qc.getPhone();
			if (StringUtils.isNotBlank(phone)) {
				hql.append(" and (t.phone1 like '%").append(phone).append("%' or t.phone2 like '%").append(phone).append("%')");
			}
		}
		hql.append(" order by t.sort asc");
		return meaordRestaurantDao.find(hql, pg);
	}

	@Override
	public void save(MeaordRestaurant meaordRestaurant) {
		meaordRestaurantDao.save(meaordRestaurant);
	}

	@Override
	public void delete(Long... ids) {
		meaordRestaurantDao.delete(ids);
	}
}
