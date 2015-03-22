package org.kyll.myserver.business.sysmanager.service.impl;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.kyll.myserver.base.paginated.Dataset;
import org.kyll.myserver.base.paginated.Paginated;
import org.kyll.myserver.business.sysmanager.QueryCondition;
import org.kyll.myserver.business.sysmanager.dao.ModuleDao;
import org.kyll.myserver.business.sysmanager.dao.RoleDao;
import org.kyll.myserver.business.sysmanager.dao.UserDao;
import org.kyll.myserver.business.sysmanager.entity.Module;
import org.kyll.myserver.business.sysmanager.entity.Role;
import org.kyll.myserver.business.sysmanager.service.RoleService;
import org.kyll.myserver.util.HqlUtils;
import org.kyll.myserver.util.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * User: Kyll
 * Date: 2014-11-10 14:36
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class RoleServiceImpl implements RoleService {
	@Autowired
	private RoleDao roleDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private ModuleDao moduleDao;

	@Override
	public Role get(Long id) {
		return roleDao.get(id);
	}

	@Override
	public JSONArray getTreeJson(Long userId) {
		Set<Role> roleSet;
		if (userId == null) {
			roleSet = new HashSet<>();
		} else {
			roleSet = userDao.get(userId).getRoleSet();
		}

		JSONArray ja = new JSONArray();
		List<Role> list = roleDao.find("from Role t order by t.sort");
		for (int i = 0, size = list.size(); i < size; i++) {
			Role role = list.get(i);
			Long id = role.getId();

			JSONObject jo = new JSONObject();
			jo.put("id", id);
			jo.put("text", role.getName());
			jo.put("leaf", true);

			boolean checked = false;
			for (Role r : roleSet) {
				if (Objects.equals(id, r.getId())) {
					checked = true;
					break;
				}
			}
			jo.put("checked", checked);

			ja.add(jo);
		}
		return ja;
	}

	@Override
	public Dataset<Role> get(QueryCondition qc, Paginated pg) {
		StringBuilder hql = new StringBuilder("from Role t where 1 = 1");
		if (qc != null) {
			String name = qc.getName();
			if (StringUtils.isNotBlank(name)) {
				hql.append(" and lower(t.name) like lower('%").append(name).append("%')");
			}
			String description = qc.getDescription();
			if (StringUtils.isNotBlank(description)) {
				hql.append(" and lower(t.description) like lower('%").append(description).append("%')");
			}
		}
		HqlUtils.appendOrderBy(hql, "t", pg);
		return roleDao.find(hql, pg);
	}

	@Override
	public void save(JSONArray jsonArray) {
		for (int i = 0, size = jsonArray.size(); i < size; i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);

			Long id = JsonUtils.getLong(jsonObject, "id");
			Role role;
			if (id == null) {
				role = new Role();
			} else {
				role = this.get(id);
			}
			role.setName(JsonUtils.getString(jsonObject, "name"));
			role.setDescription(JsonUtils.getString(jsonObject, "description"));
			role.setSort(JsonUtils.getInteger(jsonObject, "sort"));

			roleDao.save(role);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void save(Long roleId, Long[] moduleIds) {
		Query query = moduleDao.createQuery("from Module t where t.id in (:moduleIds)");
		query.setParameterList("moduleIds", moduleIds);
		List<Module> moduleList = query.list();

		Role role = roleDao.get(roleId);
		Set<Module> moduleSet = role.getModuleSet();
		moduleSet.clear();
		moduleSet.addAll(moduleList);
		roleDao.save(role);
	}

	@Override
	public void delete(Long[] ids) {
		roleDao.delete(ids);
	}
}
