package org.kyll.common.ctrl;

import org.kyll.common.model.Binary;
import org.kyll.common.facade.BinaryFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;

@Controller
public class BinaryCtrl {
	@Autowired
	private BinaryFacade binaryFacade;

	@RequestMapping("/common/binary/image.ctrl")
	public void showImage(Binary binary, HttpServletResponse response) throws Exception {
		response.setContentType("image");

		binary = binaryFacade.getBinaryById(binary);
		if (binary != null) {
			response.getOutputStream().write(binary.getContent());
		}
	}
}
