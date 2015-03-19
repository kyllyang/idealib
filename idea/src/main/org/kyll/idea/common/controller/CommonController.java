package org.kyll.idea.common.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CommonController {
	@RequestMapping("/index.ctrl")
	public String index() throws Exception {
		return "common/index";
	}

	@RequestMapping("/common/menu.ctrl")
	public String menu() throws Exception {
		return "common/menu";
	}
}
