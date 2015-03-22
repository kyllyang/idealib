package org.kyll.myserver.business.meaord.service.impl;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.hibernate.transform.Transformers;
import org.kyll.myserver.base.paginated.Dataset;
import org.kyll.myserver.base.paginated.Paginated;
import org.kyll.myserver.business.meaord.QueryCondition;
import org.kyll.myserver.business.meaord.dao.MeaordDishesDao;
import org.kyll.myserver.business.meaord.dao.MeaordRestaurantDao;
import org.kyll.myserver.business.meaord.entity.MeaordDishes;
import org.kyll.myserver.business.meaord.service.MeaordDishesService;
import org.kyll.myserver.business.sysmanager.entity.Attachment;
import org.kyll.myserver.business.sysmanager.service.AttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.util.List;
import java.util.Map;

/**
 * User: Kyll
 * Date: 2014-11-17 9:30
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class MeaordDishesServiceImpl implements MeaordDishesService {
	@Autowired
	private MeaordDishesDao meaordDishesDao;
	@Autowired
	private MeaordRestaurantDao meaordRestaurantDao;
	@Autowired
	private AttachmentService attachmentService;

	@Override
	public MeaordDishes get(Long id) {
		return meaordDishesDao.get(id);
	}

	@Override
	public JSONArray getTreeJson(Long restaurantId) {
		String sql = "select t.TYPE_ from meaord_dishes t where t.restaurant_id_ = '" + restaurantId + "' group by t.type_ order by t.type_ asc";
		List<Map<String, Object>> list = meaordDishesDao.findBySQL(sql);

		JSONArray ja = new JSONArray();
		for (Map<String, Object> map : list) {
			String type = (String) map.get("TYPE_");
			JSONObject jo = new JSONObject();
			jo.put("id", type);
			jo.put("text", type);
			jo.put("leaf", true);
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public List<MeaordDishes> get(QueryCondition qc) {
		StringBuilder hql = new StringBuilder("from MeaordDishes t where 1 = 1");
		if (qc != null) {
			Long restaurantId = qc.getRestaurantId();
			if (restaurantId != null) {
				hql.append(" and t.meaordRestaurant.id = '").append(restaurantId).append("'");
			}
			String type = qc.getType();
			if (StringUtils.isNotBlank(type)) {
				hql.append(" and t.type = '").append(type).append("'");
			}
		}
		hql.append(" order by t.sort asc");
		return meaordDishesDao.find(hql);
	}

	@Override
	public void save(MeaordDishes entity, Long restaurantId, List<CommonsMultipartFile> commonsMultipartFileList) {
		entity.setMeaordRestaurant(meaordRestaurantDao.get(restaurantId));
		meaordDishesDao.save(entity);

		if (!commonsMultipartFileList.isEmpty()) {
			String entityName = MeaordDishes.class.getName();
			String entityId = String.valueOf(entity.getId());
			attachmentService.delete(entityName, entityId);
			attachmentService.save(entityName, entityId, commonsMultipartFileList);
		}
	}

	@Override
	public void delete(Long[] ids) {
		meaordDishesDao.delete(ids);
	}
}
