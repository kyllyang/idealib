package org.kyll.myserver.business.meaord.service;

import net.sf.json.JSONArray;
import org.kyll.myserver.base.BaseService;
import org.kyll.myserver.base.paginated.Dataset;
import org.kyll.myserver.base.paginated.Paginated;
import org.kyll.myserver.business.meaord.QueryCondition;
import org.kyll.myserver.business.meaord.entity.MeaordDishes;
import org.kyll.myserver.business.sysmanager.entity.Attachment;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.util.List;

/**
 * User: Kyll
 * Date: 2014-11-17 9:29
 */
public interface MeaordDishesService extends BaseService<MeaordDishes, Long> {
	JSONArray getTreeJson(Long restaurantId);

	List<MeaordDishes> get(QueryCondition qc);

	void save(MeaordDishes entity, Long restaurantId, List<CommonsMultipartFile> commonsMultipartFileList);

	void delete(Long[] ids);
}
