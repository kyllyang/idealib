package org.kyll.myserver.business.sysmanager.ctrl;

import net.sf.json.JSONArray;
import org.kyll.myserver.base.paginated.Dataset;
import org.kyll.myserver.business.sysmanager.QueryCondition;
import org.kyll.myserver.business.sysmanager.entity.DictItem;
import org.kyll.myserver.business.sysmanager.service.DictItemService;
import org.kyll.myserver.business.sysmanager.vo.DictItemVo;
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
 * Date: 2015-02-05 15:53
 */
@Controller
@Scope("request")
public class DictItemCtrl {
	@Autowired
	private DictItemService dictItemService;

	@RequestMapping("/sysmanager/dictitem/list.ctrl")
	public void list(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Dataset<DictItem> dataset = dictItemService.get(RequestUtils.get(request, "qc", QueryCondition.class), RequestUtils.getPaginated(request));
		Dataset<DictItemVo> voDataset = VoUtils.convert(dataset, DictItemVo.class);

		response.setContentType("text/plain");
		response.getWriter().println(JsonUtils.convert(voDataset));
	}

	@RequestMapping("/sysmanager/dictitem/save.ctrl")
	public void save(String dictItemJson, HttpServletResponse response) throws Exception {
		dictItemService.save(JSONArray.fromObject(dictItemJson));

		response.setContentType("text/plain");
		response.getWriter().println(JsonUtils.ajaxResult(true));
	}

	@RequestMapping("/sysmanager/dictitem/delete.ctrl")
	public void delete(Long[] ids, HttpServletResponse response) throws Exception {
		dictItemService.delete(ids);

		response.setContentType("text/plain");
		response.getWriter().println(JsonUtils.ajaxResult(true));
	}
}
