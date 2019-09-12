package jblog.guohai.org.web;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import jblog.guohai.org.bll.agent.AlipayAgent;
import jblog.guohai.org.model.AlipayOrderBean;

@Controller
@RequestMapping("/order/alipay")
public class AlipayController {

	@Autowired
	private AlipayAgent alipayAgent;

	@RequestMapping(value = "pay")
	public String pay(Model model) throws Exception {
		AlipayOrderBean orderBean = new AlipayOrderBean();
		
		orderBean.setOutTradeNo(String.format("20000%s", new Date().getTime()));
		orderBean.setTotalAmount("1.00");
		orderBean.setSubject("醉么商城订单" + String.format("Zuime%s", new Date().getTime()));
		orderBean.setBody("醉么商城订单" + String.format("Zuime%s", new Date().getTime()));
		orderBean.setProductCode("FAST_INSTANT_TRADE_PAY");

		model.addAttribute("page", alipayAgent.tradePagePay(orderBean));
		return "pay/alipay/create";
	}

	@RequestMapping(value = "return")
	public String payReturn() {

		return "pay/alipay/return";
	}
	
	@RequestMapping(value = "pay/query")
	public String payQuery(String tradeNo,Model model) throws Exception {
		model.addAttribute("page", alipayAgent.tradeQuery(tradeNo));
		return "pay/alipay/create";
	}

	@RequestMapping(value = "transfer")
	public String transfer(Model model) throws Exception {
		model.addAttribute("page", alipayAgent.transfer());
		return "pay/alipay/create";

	}
	
	@RequestMapping(value = "transfer/query")
	public String transferQuery(String bizNo,Model model) throws Exception {
		model.addAttribute("page", alipayAgent.transferQuery(bizNo));
		return "pay/alipay/create";

	}
}
