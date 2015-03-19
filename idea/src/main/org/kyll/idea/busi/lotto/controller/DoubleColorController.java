package org.kyll.idea.busi.lotto.controller;

import org.kyll.common.paginated.Paginated;
import org.kyll.idea.busi.lotto.facade.DoubleColorFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DoubleColorController {
	@Autowired
	private DoubleColorFacade doubleColorFacade;

	@RequestMapping("/busi/lotto/dc/list.ctrl")
	public String list(Paginated paginated, ModelMap modelMap) throws Exception {
		modelMap.addAttribute("dataset", doubleColorFacade.getDoubleColorList(paginated));
		return "busi/lotto/dc/list";
	}
}
