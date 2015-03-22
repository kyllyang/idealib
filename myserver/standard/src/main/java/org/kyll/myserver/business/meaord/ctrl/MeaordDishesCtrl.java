package org.kyll.myserver.business.meaord.ctrl;

import net.sf.json.JSONArray;
import org.kyll.myserver.base.paginated.Dataset;
import org.kyll.myserver.business.meaord.QueryCondition;
import org.kyll.myserver.business.meaord.entity.MeaordDishes;
import org.kyll.myserver.business.meaord.entity.MeaordRestaurant;
import org.kyll.myserver.business.meaord.service.MeaordDishesService;
import org.kyll.myserver.business.meaord.vo.MeaordDishesVo;
import org.kyll.myserver.util.EntityUtils;
import org.kyll.myserver.util.JsonUtils;
import org.kyll.myserver.util.RequestUtils;
import org.kyll.myserver.util.VoUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * User: Kyll
 * Date: 2014-11-17 9:33
 */
@Controller
@Scope("request")
public class MeaordDishesCtrl {
	@Autowired
	private MeaordDishesService meaordDishesService;

	@RequestMapping("/meaord/dishes/tree.ctrl")
	public void tree(Long restaurantId, HttpServletResponse response) throws Exception {
		JSONArray ja = meaordDishesService.getTreeJson(restaurantId);

		response.setContentType("text/plain");
		response.getWriter().println(ja.toString());
	}

	@RequestMapping("/meaord/dishes/list.ctrl")
	public void list(HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<MeaordDishes> entityList = meaordDishesService.get(RequestUtils.get(request, "qc", QueryCondition.class));
		List<MeaordDishesVo> voList = VoUtils.convert(entityList, MeaordDishesVo.class);

		response.setContentType("text/plain");
		response.getWriter().println(JsonUtils.convert(voList));
	}

	@RequestMapping("/meaord/dishes/input.ctrl")
	public void input(Long id, HttpServletResponse response) throws Exception {
		MeaordDishes entity = meaordDishesService.get(id);
		MeaordDishesVo entityVo = VoUtils.convert(entity, MeaordDishesVo.class, (meaordDishes, meaordDishesVo) -> {
			MeaordRestaurant meaordRestaurant = meaordDishes.getMeaordRestaurant();
			meaordDishesVo.setMeaordRestaurantId(meaordRestaurant.getId());
			meaordDishesVo.setMeaordRestaurantName(meaordRestaurant.getName());
		});

		response.setContentType("text/plain");
		response.getWriter().println(JsonUtils.convert(entityVo));
	}

	@RequestMapping("/meaord/dishes/save.ctrl")
	public void save(MeaordDishesVo entityVo, HttpServletResponse response) throws Exception {
		meaordDishesService.save(EntityUtils.convert(entityVo, MeaordDishes.class, meaordDishesService), entityVo.getMeaordRestaurantId(), VoUtils.getCommonsMultipartFileList(entityVo));

		response.setContentType("text/plain");
		response.getWriter().println(JsonUtils.ajaxResult(true));
	}

	@RequestMapping("/meaord/dishes/delete.ctrl")
	public void delete(Long[] ids, HttpServletResponse response) throws Exception {
		meaordDishesService.delete(ids);

		response.setContentType("text/plain");
		response.getWriter().println(JsonUtils.ajaxResult(true));
	}
}
