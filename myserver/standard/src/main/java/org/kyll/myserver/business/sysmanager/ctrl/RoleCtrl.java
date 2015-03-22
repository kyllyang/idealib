package org.kyll.myserver.business.sysmanager.ctrl;

import net.sf.json.JSONArray;
import org.kyll.myserver.base.paginated.Dataset;
import org.kyll.myserver.business.sysmanager.QueryCondition;
import org.kyll.myserver.business.sysmanager.entity.Role;
import org.kyll.myserver.business.sysmanager.service.RoleService;
import org.kyll.myserver.business.sysmanager.vo.RoleVo;
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
 * Date: 2014-11-10 14:41
 */
@Controller
@Scope("request")
public class RoleCtrl {
	@Autowired
	private RoleService roleService;

	@RequestMapping("/sysmanager/role/tree.ctrl")
	public void tree(Long userId, HttpServletResponse response) throws Exception {
		JSONArray ja = roleService.getTreeJson(userId);

		response.setContentType("text/plain");
		response.getWriter().println(ja.toString());
	}

	@RequestMapping("/sysmanager/role/list.ctrl")
	public void list(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Dataset<Role> dataset = roleService.get(RequestUtils.get(request, "qc", QueryCondition.class), RequestUtils.getPaginated(request));
		Dataset<RoleVo> voDataset = VoUtils.convert(dataset, RoleVo.class);

		response.setContentType("text/plain");
		response.getWriter().println(JsonUtils.convert(voDataset));
	}

	@RequestMapping("/sysmanager/role/save.ctrl")
	public void save(String roleJson, HttpServletResponse response) throws Exception {
		roleService.save(JSONArray.fromObject(roleJson));

		response.setContentType("text/plain");
		response.getWriter().println(JsonUtils.ajaxResult(true));
	}

	@RequestMapping("/sysmanager/role/saveModule.ctrl")
	public void saveModule(Long roleId, Long[] moduleIds, HttpServletResponse response) throws Exception {
		roleService.save(roleId, moduleIds);

		response.setContentType("text/plain");
		response.getWriter().println(JsonUtils.ajaxResult(true));
	}

	@RequestMapping("/sysmanager/role/delete.ctrl")
	public void delete(Long[] ids, HttpServletResponse response) throws Exception {
		roleService.delete(ids);

		response.setContentType("text/plain");
		response.getWriter().println(JsonUtils.ajaxResult(true));
	}
}
