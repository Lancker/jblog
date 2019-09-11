package jblog.guohai.org.web;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import jblog.guohai.org.bll.agent.AlipayAgent;
import jblog.guohai.org.model.AlipayOrderBean;

@Controller
@RequestMapping("/order/alipay")
public class AlipayController {

	@Autowired
	private AlipayAgent alipayAgent;

	@RequestMapping(value = "pay")
	@ResponseBody
	public String pay(String outTradeNo, String subject, String totalAmount, String body) throws Exception {
		AlipayOrderBean orderBean = new AlipayOrderBean();

		orderBean.setOutTradeNo(String.format("20000%s", new Date().getTime()));
		orderBean.setTotalAmount("1.00");
		orderBean.setSubject("醉么商城订单" +String.format("Test%s", new Date().getTime()));
		orderBean.setBody("醉么商城订单" +String.format("Test%s", new Date().getTime()));
		orderBean.setProductCode("FAST_INSTANT_TRADE_PAY");

		return alipayAgent.buildPayUrl(orderBean);
	}
}
