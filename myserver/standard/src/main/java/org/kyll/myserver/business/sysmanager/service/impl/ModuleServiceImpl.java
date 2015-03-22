package org.kyll.myserver.business.sysmanager.service.impl;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.kyll.myserver.base.paginated.Dataset;
import org.kyll.myserver.base.paginated.Paginated;
import org.kyll.myserver.business.sysmanager.QueryCondition;
import org.kyll.myserver.business.sysmanager.dao.ModuleDao;
import org.kyll.myserver.business.sysmanager.dao.RoleDao;
import org.kyll.myserver.business.sysmanager.dao.UserDao;
import org.kyll.myserver.business.sysmanager.entity.Module;
import org.kyll.myserver.business.sysmanager.entity.Role;
import org.kyll.myserver.business.sysmanager.entity.User;
import org.kyll.myserver.business.sysmanager.service.ModuleService;
import org.kyll.myserver.util.HqlUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * User: Kyll
 * Date: 2014-11-07 13:07
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ModuleServiceImpl implements ModuleService {
	@Autowired
	private ModuleDao moduleDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private RoleDao roleDao;

	@Override
	public Module get(Long id) {
		return moduleDao.get(id);
	}

	@Override
	public List<Module> getTopMenu(Long userId) {
		Set<Module> moduleSet = new HashSet<>();

		User user = userDao.get(userId);
		Set<Role> roleSet = user.getRoleSet();
		for (Role role : roleSet) {
			moduleSet.addAll(role.getModuleSet().stream().map(this::getTopModule).collect(Collectors.toList()));
		}

		List<Module> moduleList = new ArrayList<>(moduleSet);
		Collections.sort(moduleList, new Comparator<Module>() {
			@Override
			public int compare(Module o1, Module o2) {
				return o1.getSort() - o2.getSort();
			}
		});

		return moduleList;
	}

	private Module getTopModule(Module module) {
		Module parent = module.getParent();
		return parent == null ? module : this.getTopModule(parent);
	}

	@Override
	public JSONArray getLeftMenu(Long userId, Long moduleId) {
		Set<Module> resultModuleSet = new HashSet<>();

		Module topModule = null;
		User user = userDao.get(userId);
		Set<Role> roleSet = user.getRoleSet();
		for (Role role : roleSet) {
			Set<Module> moduleSet = role.getModuleSet();
			for (Module module : moduleSet) {
				topModule = this.getTopModule(module);
				if (Objects.equals(moduleId, topModule.getId())) {
					resultModuleSet.add(module);
				}
			}
		}

		List<Module> moduleList = new ArrayList<>(resultModuleSet);
		Collections.sort(moduleList, new Comparator<Module>() {
			@Override
			public int compare(Module o1, Module o2) {
				return o1.getSort() - o2.getSort();
			}
		});

		return this.recursiveMenu(moduleId, moduleList);
	}

	private JSONArray recursiveMenu(Long parentId, List<Module> list) {
		JSONArray ja = new JSONArray();

		for (Module module : list) {
			Module parent = module.getParent();
			if (parentId == null ? parent == null : parent != null && Objects.equals(parentId, parent.getId())) {
				Long id = module.getId();

				JSONObject jo = new JSONObject();
				jo.put("id", id);
				jo.put("text", module.getName());
				jo.put("funcType", module.getFuncType());
				jo.put("funcCode", module.getFuncCode());

				JSONArray children = this.recursiveMenu(id, list);
				if (children.isEmpty()) {
					jo.put("leaf", true);
				} else {
					jo.put("leaf", false);
				}
				jo.put("children", children);

				ja.add(jo);
			}
		}
		return ja;
	}

	@Override
	public Dataset<Module> get(QueryCondition qc, Paginated pg) {
		StringBuilder hql = new StringBuilder("from Module t where 1 = 1");
		if (qc != null) {
			Long parentId = qc.getParentId();
			if (parentId == null) {
				hql.append(" and t.parent is null");
			} else {
				hql.append(" and t.parent.id = '").append(parentId).append("'");
			}
			String name = qc.getName();
			if (StringUtils.isNotBlank(name)) {
				hql.append(" and lower(t.name) like lower('%").append(name).append("%')");
			}
			Integer type = qc.getType();
			if (type != null) {
				hql.append(" and t.type = '").append(type).append("'");
			}
			Integer funcType = qc.getFuncType();
			if (funcType != null) {
				hql.append(" and t.funcType = '").append(funcType).append("'");
			}
		}
		HqlUtils.appendOrderBy(hql, "t", pg);
		return moduleDao.find(hql, pg);
	}

	@Override
	public JSONArray getTreeJson(Boolean checked, Long roleId) {
		List<Module> list = moduleDao.find("from Module t order by t.sort");
		return this.recursiveTree(null, list, checked, roleId == null ? new HashSet<>() : roleDao.get(roleId).getModuleSet());
	}

	private JSONArray recursiveTree(Long parentId, List<Module> list, Boolean checked, Set<Module> modelSet) {
		JSONArray ja = new JSONArray();

		for (Module module : list) {
			Module parent = module.getParent();
			if (parentId == null ? parent == null : parent != null && Objects.equals(parentId, parent.getId())) {
				Long id = module.getId();

				JSONObject jo = new JSONObject();
				jo.put("id", id);
				jo.put("text", module.getName());
				jo.put("type", module.getType());
				if (checked != null) {
					boolean isChecked = checked;
					for (Module m : modelSet) {
						if (Objects.equals(id, m.getId())) {
							isChecked = true;
							break;
						}
					}
					jo.put("checked", isChecked);
				}

				JSONArray children = this.recursiveTree(id, list, checked, modelSet);
				if (children.isEmpty()) {
					jo.put("leaf", true);
				} else {
					jo.put("leaf", false);
				}
				jo.put("children", children);

				ja.add(jo);
			}
		}
		return ja;
	}

	@Override
	public void save(Module module, Long parentId) {
		if (parentId != null) {
			module.setParent(this.get(parentId));
		}
		moduleDao.save(module);
	}

	@Override
	public void delete(Long[] ids) {
		moduleDao.delete(ids);
	}
}
