package jblog.guohai.org.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import jblog.guohai.org.bll.agent.PingAnAgent;

@Controller
@RequestMapping("/pingan")
public class PingAnController {

	@Autowired
	private PingAnAgent pingAnAgent;
	
	@RequestMapping(value = "query")
	public String transfer(Model model) throws Exception {
		model.addAttribute("page", pingAnAgent.queryAmount());
		return "pay/alipay/create";
	}
}
