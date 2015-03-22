package org.kyll.myserver.business.sysmanager.dao;

import org.kyll.myserver.base.BaseHibernateDao;
import org.kyll.myserver.business.sysmanager.entity.Employee;
import org.springframework.stereotype.Repository;

/**
 * User: Kyll
 * Date: 2014-11-05 13:33
 */
@Repository
public class EmployeeDao extends BaseHibernateDao<Employee, Long> {
}
