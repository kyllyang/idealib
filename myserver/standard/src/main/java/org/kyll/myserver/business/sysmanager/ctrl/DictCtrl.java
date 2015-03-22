package org.kyll.myserver.business.sysmanager.ctrl;

import net.sf.json.JSONArray;
import org.kyll.myserver.base.paginated.Dataset;
import org.kyll.myserver.business.sysmanager.QueryCondition;
import org.kyll.myserver.business.sysmanager.entity.Dict;
import org.kyll.myserver.business.sysmanager.service.DictService;
import org.kyll.myserver.business.sysmanager.vo.DictVo;
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
 * Date: 2014-11-09 06:49
 */
@Controller
@Scope("request")
public class DictCtrl {
	@Autowired
	private DictService dictService;

	@RequestMapping("/sysmanager/dict/tree.ctrl")
	public void tree(Long parentId, HttpServletResponse response) throws Exception {
		JSONArray ja = dictService.getTreeJson(parentId);

		response.setContentType("text/plain");
		response.getWriter().println(ja.toString());
	}

	@RequestMapping("/sysmanager/dict/input.ctrl")
	public void input(Long id, HttpServletResponse response) throws Exception {
		Dict entity = dictService.get(id);
		DictVo entityVo = VoUtils.convert(entity, DictVo.class, voHandler);

		response.setContentType("text/plain");
		response.getWriter().println(JsonUtils.convert(entityVo));
	}

	@RequestMapping("/sysmanager/dict/save.ctrl")
	public void save(DictVo entityVo, HttpServletResponse response) throws Exception {
		dictService.save(EntityUtils.convert(entityVo, Dict.class, dictService), entityVo.getParentId());

		response.setContentType("text/plain");
		response.getWriter().println(JsonUtils.ajaxResult(true));
	}

	@RequestMapping("/sysmanager/dict/delete.ctrl")
	public void delete(Long[] ids, HttpServletResponse response) throws Exception {
		dictService.delete(ids);

		response.setContentType("text/plain");
		response.getWriter().println(JsonUtils.ajaxResult(true));
	}

	private VoUtils.VoHandler<Dict, DictVo> voHandler = (dict, dictVo) -> {
		Dict parent = dict.getParent();
		if (parent != null) {
			dictVo.setParentId(parent.getId());
			dictVo.setParentName(parent.getName());
		}
	};
}
