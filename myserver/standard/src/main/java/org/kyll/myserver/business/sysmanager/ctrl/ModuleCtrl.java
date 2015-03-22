package org.kyll.myserver.business.sysmanager.ctrl;

import net.sf.json.JSONArray;
import org.kyll.myserver.base.paginated.Dataset;
import org.kyll.myserver.business.sysmanager.QueryCondition;
import org.kyll.myserver.business.sysmanager.entity.Module;
import org.kyll.myserver.business.sysmanager.service.DictItemService;
import org.kyll.myserver.business.sysmanager.service.ModuleService;
import org.kyll.myserver.business.sysmanager.vo.ModuleVo;
import org.kyll.myserver.business.sysmanager.vo.SessionVo;
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
import java.util.Map;

/**
 * User: Kyll
 * Date: 2014-11-07 13:10
 */
@Controller
@Scope("request")
public class ModuleCtrl {
	@Autowired
	private ModuleService moduleService;
	@Autowired
	private DictItemService dictItemService;

	private static Map<String, String> typeDictMap;
	private static Map<String, String> funcTypeDictMap;

	@RequestMapping("/sysmanager/module/topMenu.ctrl")
	public void topMenu(HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<Module> entityList = moduleService.getTopMenu(((SessionVo) request.getSession().getAttribute("sessionVo")).getUserId());
		List<ModuleVo> voList = VoUtils.convert(entityList, ModuleVo.class);

		response.setContentType("text/plain");
		response.getWriter().println(JsonUtils.convert(voList));
	}

	@RequestMapping("/sysmanager/module/leftMenu.ctrl")
	public void leftMenu(Long moduleId, HttpServletRequest request, HttpServletResponse response) throws Exception {
		JSONArray ja = moduleService.getLeftMenu(((SessionVo) request.getSession().getAttribute("sessionVo")).getUserId(), moduleId);

		response.setContentType("text/plain");
		response.getWriter().println(ja.toString());
	}

	@RequestMapping("/sysmanager/module/tree.ctrl")
	public void tree(Boolean checked, Long roleId, HttpServletResponse response) throws Exception {
		JSONArray ja = moduleService.getTreeJson(checked, roleId);

		response.setContentType("text/plain");
		response.getWriter().println(ja.toString());
	}

	@RequestMapping("/sysmanager/module/list.ctrl")
	public void list(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Dataset<Module> dataset = moduleService.get(RequestUtils.get(request, "qc", QueryCondition.class), RequestUtils.getPaginated(request));
		Dataset<ModuleVo> voDataset = VoUtils.convert(dataset, ModuleVo.class, voHandler);

		response.setContentType("text/plain");
		response.getWriter().println(JsonUtils.convert(voDataset));
	}

	@RequestMapping("/sysmanager/module/input.ctrl")
	public void input(Long id, HttpServletResponse response) throws Exception {
		Module entity = moduleService.get(id);
		ModuleVo entityVo = VoUtils.convert(entity, ModuleVo.class, voHandler);

		response.setContentType("text/plain");
		response.getWriter().println(JsonUtils.convert(entityVo));
	}

	@RequestMapping("/sysmanager/module/save.ctrl")
	public void save(ModuleVo entityVo, HttpServletResponse response) throws Exception {
		moduleService.save(EntityUtils.convert(entityVo, Module.class, moduleService), entityVo.getParentId());

		response.setContentType("text/plain");
		response.getWriter().println(JsonUtils.ajaxResult(true));
	}

	@RequestMapping("/sysmanager/module/delete.ctrl")
	public void delete(Long[] ids, HttpServletResponse response) throws Exception {
		moduleService.delete(ids);

		response.setContentType("text/plain");
		response.getWriter().println(JsonUtils.ajaxResult(true));
	}

	private VoUtils.VoHandler<Module, ModuleVo> voHandler = new VoUtils.VoHandler<Module, ModuleVo>() {
		@Override
		public void handler(Module module, ModuleVo moduleVo) {
			if (typeDictMap == null) {
				typeDictMap = dictItemService.getMap("sysmanager_module_type");
			}
			if (funcTypeDictMap == null) {
				funcTypeDictMap = dictItemService.getMap("sysmanager_module_funcType");
			}

			Module parent = module.getParent();
			if (parent != null) {
				moduleVo.setParentId(parent.getId());
				moduleVo.setParentName(parent.getName());
			}
			moduleVo.setTypeText(typeDictMap.get(String.valueOf(module.getType())));
			moduleVo.setFuncTypeText(funcTypeDictMap.get(String.valueOf(module.getFuncType())));
		}
	};
}
