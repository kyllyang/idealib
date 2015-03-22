package org.kyll.myserver.business.sysmanager.ctrl;

import org.kyll.myserver.business.sysmanager.QueryCondition;
import org.kyll.myserver.business.sysmanager.entity.Attachment;
import org.kyll.myserver.business.sysmanager.service.AttachmentService;
import org.kyll.myserver.util.ConstUtils;
import org.kyll.myserver.util.RequestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * User: Kyll
 * Date: 2014-12-03 13:58
 */
@Controller
@Scope("request")
public class AttachmentCtrl {
	@Autowired
	private AttachmentService attachmentService;

	@RequestMapping("/sysmanager/attachment/view.ctrl")
	public void view(HttpServletRequest request, HttpServletResponse response) throws Exception {
		QueryCondition qc = RequestUtils.get(request, "qc", QueryCondition.class);
		List<Attachment> attachmentList = attachmentService.get(qc);

		Attachment attachment = null;
		if (attachmentList.isEmpty()) {
			System.err.println("附件 [" + qc.getId() + " | " + qc.getEntityName() + " | " + qc.getEntityId() + "] 不存在！");
		} else if (attachmentList.size() > 1) {
			System.err.println("附件 [" + qc.getId() + " | " + qc.getEntityName() + " | " + qc.getEntityId() + "] 返回多条记录！");
		} else {
			attachment = attachmentList.get(0);
		}

		if (attachment != null) {
			response.setContentType(attachment.getContentType());
			response.setHeader("Content-Disposition", "attachment; filename=" + attachment.getOriginalFilename());

			File attachmentDir = new File(ConstUtils.getAttachmentPath());
			if ((attachmentDir.exists() && attachmentDir.isDirectory())) {
				File entityDir = new File(attachmentDir.getPath() + File.separator + attachment.getEntityName().replace(".", File.separator));
				if ((entityDir.exists() && entityDir.isDirectory())) {
					File file = new File(entityDir.getPath() + File.separator + attachment.getRandomFilename() + "." + attachment.getExtensionName());
					if (file.exists() && file.isFile()) {
						InputStream in = new BufferedInputStream(new FileInputStream(file), 2048);
						OutputStream out = response.getOutputStream();
						byte[] buffer = new byte[2048];
						int result;
						while ((result = in.read(buffer)) != -1) {
							out.write(buffer, 0, result);
						}
						in.close();
						out.flush();
					} else {
						System.err.println(file.getPath() + " 不存在， 或者不是文件！");
					}
				} else {
					System.err.println(entityDir.getPath() + " 不存在， 或者不是目录！");
				}
			} else {
				System.err.println("SYS_CONFIG.ATTACHMENT_PATH 不存在， 或者不是目录！");
			}
		}
	}
}
