
package jblog.guohai.org.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import jblog.guohai.org.bll.agent.TencentCaptchaAgent;

/**
 * 腾讯云
 * 
 * @author zhongdaiqi
 *
 */
@Controller
@RequestMapping("/tencent")
public class TencentController {

	@Autowired
	private TencentCaptchaAgent tencentCaptchaAgent;

	@RequestMapping(value = "captcha")
	public String captcha(Model model) throws Exception {

		return "tencent/captcha";
	}

	@RequestMapping(value = "verify")
	@ResponseBody
	public String verify(Model model, String ticket, String randstr,HttpServletRequest request) throws Exception {
		return tencentCaptchaAgent.verify(ticket, randstr,request.getRemoteAddr());
	}
}
