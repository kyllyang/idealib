package org.kyll.common.base.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DaoSupport;
import org.springframework.orm.hibernate3.HibernateTemplate;

import java.util.List;

public abstract class HibernateDao extends DaoSupport {
	@Autowired
	protected HibernateTemplate hibernateTemplate;

	@Override
	protected void checkDaoConfig() throws IllegalArgumentException {
		if (this.hibernateTemplate == null) {
			throw new IllegalArgumentException("'sessionFactory' or 'hibernateTemplate' is required");
		}
	}

	protected List find(String sql, Object... args) {
		return this.hibernateTemplate.find(sql, args);
	}

	protected Object findFirst(String sql, Object... args) {
		List list = this.find(sql, args);
		return list.isEmpty() ? null : list.get(0);
	}
}
