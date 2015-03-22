package org.kyll.myserver.business.meaord.ctrl;

import org.kyll.myserver.base.paginated.Dataset;
import org.kyll.myserver.business.meaord.QueryCondition;
import org.kyll.myserver.business.meaord.entity.MeaordRestaurant;
import org.kyll.myserver.business.meaord.service.MeaordRestaurantService;
import org.kyll.myserver.business.meaord.vo.MeaordRestaurantVo;
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

/**
 * User: Kyll
 * Date: 2014-11-14 13:35
 */
@Controller
@Scope("request")
public class MeaordRestaurantCtrl {
	@Autowired
	private MeaordRestaurantService meaordRestaurantService;

	@RequestMapping("/meaord/restaurant/list.ctrl")
	public void list(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Dataset<MeaordRestaurant> dataset = meaordRestaurantService.get(RequestUtils.get(request, "qc", QueryCondition.class), RequestUtils.getPaginated(request));
		Dataset<MeaordRestaurantVo> voDataset = VoUtils.convert(dataset, MeaordRestaurantVo.class);

		response.setContentType("text/plain");
		response.getWriter().println(JsonUtils.convert(voDataset));
	}

	@RequestMapping("/meaord/restaurant/input.ctrl")
	public void input(Long id, HttpServletResponse response) throws Exception {
		MeaordRestaurant entity = meaordRestaurantService.get(id);
		MeaordRestaurantVo entityVo = VoUtils.convert(entity, MeaordRestaurantVo.class);

		response.setContentType("text/plain");
		response.getWriter().println(JsonUtils.convert(entityVo));
	}

	@RequestMapping("/meaord/restaurant/save.ctrl")
	public void save(MeaordRestaurantVo entityVo, HttpServletResponse response) throws Exception {
		meaordRestaurantService.save(EntityUtils.convert(entityVo, MeaordRestaurant.class, meaordRestaurantService));

		response.setContentType("text/plain");
		response.getWriter().println(JsonUtils.ajaxResult(true));
	}

	@RequestMapping("/meaord/restaurant/delete.ctrl")
	public void delete(Long[] ids, HttpServletResponse response) throws Exception {
		meaordRestaurantService.delete(ids);

		response.setContentType("text/plain");
		response.getWriter().println(JsonUtils.ajaxResult(true));
	}
}
