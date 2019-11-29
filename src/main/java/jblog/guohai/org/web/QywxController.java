package jblog.guohai.org.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/qywx")
public class QywxController {

	@RequestMapping(value = "qrcode")
	public String pay(Model model) throws Exception {
		return "qywx/qrcode";
	}
}
