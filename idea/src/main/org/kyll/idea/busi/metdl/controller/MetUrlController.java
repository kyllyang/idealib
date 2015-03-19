package org.kyll.idea.busi.metdl.controller;

import org.kyll.common.paginated.Paginated;
import org.kyll.idea.busi.metdl.facade.MetUrlFacade;
import org.kyll.idea.busi.metdl.model.Met;
import org.kyll.idea.busi.metdl.model.MetUrl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MetUrlController {
	@Autowired
	private MetUrlFacade metUrlFacade;

	@RequestMapping("/busi/metdl/url/view.ctrl")
	public String list(Paginated paginated, MetUrl metUrl, ModelMap modelMap) throws Exception {
		metUrlFacade.download(metUrl);
		modelMap.addAttribute("metUrl", metUrl);
		return "busi/met/url/view";
	}
}
