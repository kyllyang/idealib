package org.kyll.myserver.business.sysmanager.service.impl;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.kyll.myserver.base.paginated.Dataset;
import org.kyll.myserver.base.paginated.Paginated;
import org.kyll.myserver.business.sysmanager.QueryCondition;
import org.kyll.myserver.business.sysmanager.dao.DictDao;
import org.kyll.myserver.business.sysmanager.dao.DictItemDao;
import org.kyll.myserver.business.sysmanager.entity.Dict;
import org.kyll.myserver.business.sysmanager.entity.DictItem;
import org.kyll.myserver.business.sysmanager.service.DictService;
import org.kyll.myserver.util.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * User: Kyll
 * Date: 2014-11-08 15:59
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class DictServiceImpl implements DictService {
	@Autowired
	private DictDao dictDao;
	@Autowired
	private DictItemDao dictItemDao;

	@Override
	public Dict get(Long id) {
		return dictDao.get(id);
	}

	@Override
	public JSONArray getTreeJson(Long parentId) {
		List<Dict> list = dictDao.find("from Dict t order by t.sort");
		return this.recursiveTree(null, list);
	}

	private JSONArray recursiveTree(Long parentId, List<Dict> list) {
		JSONArray ja = new JSONArray();

		for (Dict dict : list) {
			Dict parent = dict.getParent();
			if (parentId == null ? parent == null : parent != null && Objects.equals(parentId, parent.getId())) {
				Long id = dict.getId();

				JSONObject jo = new JSONObject();
				jo.put("id", id);
				jo.put("text", dict.getName() + "(" + dict.getInvokeCode() + ")");
				jo.put("name", dict.getName());

				JSONArray children = this.recursiveTree(id, list);
				jo.put("leaf", children.isEmpty());
				jo.put("children", children);

				ja.add(jo);
			}
		}
		return ja;
	}

	@Override
	public void save(Dict dict, Long parentId) {
		if (parentId != null) {
			dict.setParent(this.get(parentId));
		}
		dictDao.save(dict);
	}

	@Override
	public void delete(Long[] ids) {
		for (Long id : ids) {
			dictItemDao.delete(dictItemDao.find("from DictItem t where t.dict.id = '" + id + "'"));

			List<Long> dictItemIds = dictDao.find("from Dict t where t.parent.id = '" + id + "'").stream().map(Dict::getId).collect(Collectors.toList());
			this.delete(dictItemIds.toArray(new Long[dictItemIds.size()]));
		}
		dictDao.delete(ids);
	}
}
