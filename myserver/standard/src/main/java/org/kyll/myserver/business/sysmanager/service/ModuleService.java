package org.kyll.myserver.business.sysmanager.service;

import net.sf.json.JSONArray;
import org.kyll.myserver.base.BaseService;
import org.kyll.myserver.base.paginated.Dataset;
import org.kyll.myserver.base.paginated.Paginated;
import org.kyll.myserver.business.sysmanager.QueryCondition;
import org.kyll.myserver.business.sysmanager.entity.Module;

import java.util.List;

/**
 * User: Kyll
 * Date: 2014-11-07 13:07
 */
public interface ModuleService extends BaseService<Module, Long> {
	List<Module> getTopMenu(Long userId);

	JSONArray getLeftMenu(Long userId, Long moduleId);

	Dataset<Module> get(QueryCondition qc, Paginated pg);

	JSONArray getTreeJson(Boolean checked, Long roleId);

	void save(Module module, Long parentId);

	void delete(Long[] ids);
}
