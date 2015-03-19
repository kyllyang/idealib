package org.kyll.idea.busi.lotto.controller;

import org.kyll.common.paginated.Paginated;
import org.kyll.idea.busi.lotto.facade.DigitalThreeFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DigitalThreeController {
	@Autowired
	private DigitalThreeFacade digitalThreeFacade;

	@RequestMapping("/busi/lotto/3d/list.ctrl")
	public String list(Paginated paginated, ModelMap modelMap) throws Exception {
		modelMap.addAttribute("dataset", digitalThreeFacade.getDigitalThreeList(paginated));
		return "busi/lotto/3d/list";
	}

	@RequestMapping("/busi/lotto/3d/sync.ctrl")
	public String sync(@RequestParam(value = "urlStr") String urlStr) throws Exception {
		digitalThreeFacade.syncDigitalThree(urlStr);
		return "redirect:/busi/lotto/3d/list.ctrl";
	}
}
