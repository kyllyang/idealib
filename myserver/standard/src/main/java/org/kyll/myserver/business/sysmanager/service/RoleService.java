package org.kyll.myserver.business.sysmanager.service;

import net.sf.json.JSONArray;
import org.kyll.myserver.base.BaseService;
import org.kyll.myserver.base.paginated.Dataset;
import org.kyll.myserver.base.paginated.Paginated;
import org.kyll.myserver.business.sysmanager.QueryCondition;
import org.kyll.myserver.business.sysmanager.entity.Role;

/**
 * User: Kyll
 * Date: 2014-11-10 14:35
 */
public interface RoleService extends BaseService<Role, Long> {
	JSONArray getTreeJson(Long userId);

	Dataset<Role> get(QueryCondition qc, Paginated pg);

	void save(JSONArray jsonArray);

	void save(Long roleId, Long[] moduleIds);

	void delete(Long[] ids);
}
