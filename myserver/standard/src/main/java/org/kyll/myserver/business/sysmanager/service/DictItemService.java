package org.kyll.myserver.business.sysmanager.service;

import net.sf.json.JSONArray;
import org.kyll.myserver.base.BaseService;
import org.kyll.myserver.base.paginated.Dataset;
import org.kyll.myserver.base.paginated.Paginated;
import org.kyll.myserver.business.sysmanager.QueryCondition;
import org.kyll.myserver.business.sysmanager.entity.DictItem;

import java.util.Map;

/**
 * User: Kyll
 * Date: 2015-02-05 15:34
 */
public interface DictItemService extends BaseService<DictItem, Long> {
	Dataset<DictItem> get(QueryCondition qc, Paginated pg);

	void save(JSONArray jsonArray);

	void delete(Long[] ids);

	Map<String, String> getMap(String invokeCode);
}
