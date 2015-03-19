package org.kyll.idea.busi.metdl.controller;

import org.kyll.common.paginated.Paginated;
import org.kyll.idea.busi.metdl.facade.MetCategoryFacade;
import org.kyll.idea.busi.metdl.facade.MetFacade;
import org.kyll.idea.busi.metdl.model.Met;
import org.kyll.idea.busi.metdl.model.MetImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;

@Controller
public class MetController {
	@Autowired
	private MetFacade metFacade;
	@Autowired
	private MetCategoryFacade metCategoryFacade;

	@RequestMapping("/busi/metdl/list.ctrl")
	public String list(Paginated paginated, Met met, ModelMap modelMap) throws Exception {
		modelMap.addAttribute("metCategoryList", metCategoryFacade.getMetCategoryList());
		modelMap.addAttribute("dataset", metFacade.getMetList(met, paginated));
		modelMap.addAttribute("met", met);
		return "busi/met/list";
	}

	@RequestMapping("/busi/metdl/search.ctrl")
	public String search(Paginated paginated, Met met, ModelMap modelMap) throws Exception {
		return this.list(paginated, met, modelMap);
	}

	@RequestMapping("/busi/metdl/modify.ctrl")
	public String modify(Paginated paginated, Met met, Met searchMet, ModelMap modelMap) throws Exception {
		metFacade.modifyMetStatus(met);
		return this.list(paginated, searchMet, modelMap);
	}

	@RequestMapping("/busi/metdl/sync.ctrl")
	public String sync(@RequestParam(value = "urlStr") String urlStr, Paginated paginated, ModelMap modelMap) throws Exception {
		metFacade.syncMet(urlStr);
		return "redirect:/busi/metdl/list.ctrl";
	}

	@RequestMapping("/busi/metdl/image.ctrl")
	public void showImage(@RequestParam(value = "id") String id, HttpServletResponse response) throws Exception {
		response.setContentType("image");
		MetImage metImage = metFacade.getMetImage(id);
		if (metImage != null) {
			response.getOutputStream().write(metImage.getContent());
		}
	}
}
