package org.kyll.myserver.business.sysmanager.service.impl;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.kyll.myserver.base.paginated.Dataset;
import org.kyll.myserver.base.paginated.Paginated;
import org.kyll.myserver.business.sysmanager.QueryCondition;
import org.kyll.myserver.business.sysmanager.dao.RoleDao;
import org.kyll.myserver.business.sysmanager.dao.UserDao;
import org.kyll.myserver.business.sysmanager.entity.Role;
import org.kyll.myserver.business.sysmanager.entity.User;
import org.kyll.myserver.business.sysmanager.service.UserService;
import org.kyll.myserver.util.HqlUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * User: Kyll
 * Date: 2014-11-05 13:35
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl implements UserService {
	@Autowired
	private UserDao userDao;
	@Autowired
	private RoleDao roleDao;

	@Override
	public User login(User user) {
		List<User> userList = userDao.find("from User t where t.username = '" + user.getUsername() + "' and t.password = '" + user.getPassword() + "' and t.freeze <> '1'");
		return userList.isEmpty() ? null : userList.get(0);
	}

	@Override
	public User get(Long id) {
		return userDao.get(id);
	}

	@Override
	public Dataset<User> get(QueryCondition qc, Paginated pg) {
		StringBuilder hql = new StringBuilder("from User t where 1 = 1");
		if (qc != null) {
			String username = qc.getUsername();
			if (StringUtils.isNotBlank(username)) {
				hql.append(" and lower(t.username) like lower('%").append(username).append("%')");
			}
			String email = qc.getEmail();
			if (StringUtils.isNotBlank(email)) {
				hql.append(" and lower(t.email) like lower('%").append(email).append("%')");
			}
		}
		HqlUtils.appendOrderBy(hql, "t", pg);
		return userDao.find(hql, pg);
	}

	@Override
	public boolean save(User user) {
		List<User> userList = userDao.find("from User t where t.username = '" + user.getUsername() + "'");
		boolean result = userList.isEmpty();
		if (!result) {
			result = Objects.equals(userList.get(0).getId(), user.getId());
		}
		if (result) {
			userDao.save(user);
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void save(Long userId, Long[] roleIds) {
		Query query = roleDao.createQuery("from Role t where t.id in (:roleIds)");
		query.setParameterList("roleIds", roleIds);
		List<Role> roleList = query.list();

		User user = userDao.get(userId);
		Set<Role> roleSet = user.getRoleSet();
		roleSet.clear();
		roleSet.addAll(roleList);
		userDao.save(user);
	}

	@Override
	public void delete(Long... ids) {
		userDao.delete(ids);
	}
}
