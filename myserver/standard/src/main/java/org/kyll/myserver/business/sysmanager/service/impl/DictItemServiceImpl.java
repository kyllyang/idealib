package org.kyll.myserver.business.sysmanager.service.impl;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.kyll.myserver.base.paginated.Dataset;
import org.kyll.myserver.base.paginated.Paginated;
import org.kyll.myserver.business.sysmanager.QueryCondition;
import org.kyll.myserver.business.sysmanager.dao.DictDao;
import org.kyll.myserver.business.sysmanager.dao.DictItemDao;
import org.kyll.myserver.business.sysmanager.entity.DictItem;
import org.kyll.myserver.business.sysmanager.service.DictItemService;
import org.kyll.myserver.util.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * User: Kyll
 * Date: 2015-02-05 15:34
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class DictItemServiceImpl implements DictItemService {
	@Autowired
	private DictItemDao dictItemDao;
	@Autowired
	private DictDao dictDao;

	@Override
	public DictItem get(Long id) {
		return dictItemDao.get(id);
	}

	@Override
	public Dataset<DictItem> get(QueryCondition qc, Paginated pg) {
		StringBuilder hql = new StringBuilder("from DictItem t where 1 = 1");
		if (qc != null) {
			Long dictId = qc.getDictId();
			if (dictId != null) {
				hql.append(" and t.dict.id = '").append(dictId).append("'");
			}
			String invokeCode = qc.getInvokeCode();
			if (StringUtils.isNotBlank(invokeCode)) {
				hql.append(" and t.dict.invokeCode = '").append(invokeCode).append("'");
			}
		}
		hql.append(" order by t.sort asc");
		return dictItemDao.find(hql, pg);
	}

	@Override
	public void save(JSONArray jsonArray) {
		for (int i = 0, size = jsonArray.size(); i < size; i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);

			Long id = JsonUtils.getLong(jsonObject, "id");
			DictItem dictItem;
			if (id == null) {
				dictItem = new DictItem();
			} else {
				dictItem = this.get(id);
			}

			dictItem.setDict(dictDao.get(JsonUtils.getLong(jsonObject, "dictId")));
			dictItem.setKey(JsonUtils.getString(jsonObject, "key"));
			dictItem.setValue(JsonUtils.getString(jsonObject, "value"));
			dictItem.setSort(JsonUtils.getInteger(jsonObject, "sort"));

			dictItemDao.save(dictItem);
		}
	}

	@Override
	public void delete(Long[] ids) {
		dictItemDao.delete(ids);
	}

	@Override
	public Map<String, String> getMap(String invokeCode) {
		Map<String, String> map = new LinkedHashMap<>();

		List<DictItem> list = dictItemDao.find("from DictItem t where t.dict.invokeCode = '" + invokeCode + "' order by t.sort asc");
		for (DictItem dictItem : list) {
			map.put(dictItem.getKey(), dictItem.getValue());
		}

		return map;
	}
}
