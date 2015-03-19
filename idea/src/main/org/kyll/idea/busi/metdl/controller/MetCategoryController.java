package org.kyll.idea.busi.metdl.controller;

import org.kyll.common.paginated.Paginated;
import org.kyll.common.paginated.PaginatedHelper;
import org.kyll.idea.busi.metdl.facade.MetCategoryFacade;
import org.kyll.idea.busi.metdl.model.MetCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MetCategoryController {
	@Autowired
	private MetCategoryFacade metCategoryFacade;

	@RequestMapping("/busi/metdl/category/list.ctrl")
	public String list(Paginated paginated, ModelMap modelMap) throws Exception {
		modelMap.addAttribute("dataset", metCategoryFacade.getMetCategoryList(paginated));
		return "busi/met/category/list";
	}

	@RequestMapping("/busi/metdl/category/merge/list.ctrl")
	public String mergeList(ModelMap modelMap) throws Exception {
		modelMap.addAttribute("categoryList", metCategoryFacade.getMetCategoryList());
		return "busi/met/category/merge";
	}

	@RequestMapping("/busi/metdl/category/merge/save.ctrl")
	public String modify(@RequestParam(value = "name") String name, @RequestParam(value = "categorys") String[] categorys) throws Exception {
		metCategoryFacade.mergeMetCategory(name.trim(), categorys);
		return "redirect:/busi/metdl/category/merge/list.ctrl";
	}

	@RequestMapping("/busi/metdl/category/sortToUp.ctrl")
	public String sortToUp(Paginated paginated, MetCategory metCategory) throws Exception {
		metCategoryFacade.modifyMetCategoryForSortUp(metCategory);
		return "redirect:/busi/metdl/category/list.ctrl?" + PaginatedHelper.makeParam(paginated);
	}

	@RequestMapping("/busi/metdl/category/sortByDown.ctrl")
	public String sortDepartmentByDown(Paginated paginated, MetCategory metCategory) throws Exception {
		metCategoryFacade.modifyMetCategoryForSortDown(metCategory);
		return "redirect:/busi/metdl/category/list.ctrl?" + PaginatedHelper.makeParam(paginated);
	}
}
