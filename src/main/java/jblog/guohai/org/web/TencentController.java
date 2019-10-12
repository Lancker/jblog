
package jblog.guohai.org.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 腾讯云
 * @author zhongdaiqi
 *
 */
@Controller
@RequestMapping("/tencent")
public class TencentController {
	@RequestMapping(value = "captcha")
	public String transfer(Model model) throws Exception {

		return "tencent/captcha";
	}
}
