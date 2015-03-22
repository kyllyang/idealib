package org.kyll.myserver.business.sysmanager.service;

import net.sf.json.JSONArray;
import org.kyll.myserver.base.BaseService;
import org.kyll.myserver.base.paginated.Dataset;
import org.kyll.myserver.base.paginated.Paginated;
import org.kyll.myserver.business.sysmanager.QueryCondition;
import org.kyll.myserver.business.sysmanager.entity.Dict;

import java.util.Map;

/**
 * User: Kyll
 * Date: 2014-11-08 15:59
 */
public interface DictService extends BaseService<Dict, Long> {
	JSONArray getTreeJson(Long parentId);

	void save(Dict dict, Long parentId);

	void delete(Long[] ids);
}
