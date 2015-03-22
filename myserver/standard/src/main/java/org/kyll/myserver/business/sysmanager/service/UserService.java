package org.kyll.myserver.business.sysmanager.service;

import org.kyll.myserver.base.BaseService;
import org.kyll.myserver.base.paginated.Dataset;
import org.kyll.myserver.base.paginated.Paginated;
import org.kyll.myserver.business.sysmanager.QueryCondition;
import org.kyll.myserver.business.sysmanager.entity.User;

/**
 * User: Kyll
 * Date: 2014-11-05 13:35
 */
public interface UserService extends BaseService<User, Long> {
	User login(User user);

	Dataset<User> get(QueryCondition qc, Paginated pg);

	boolean save(User user);

	void save(Long userId, Long[] roleIds);

	void delete(Long... ids);
}
