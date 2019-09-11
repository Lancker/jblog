package jblog.guohai.org.web;

import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import jblog.guohai.org.bll.agent.AlipayAgent;
import jblog.guohai.org.model.AlipayOrderBean;


@Controller
@RequestMapping("/order/alipay")
public class AlipayController {
    private static final String CONTENT_TYPE = "application/json;charset=UTF-8";
	@Autowired
	private AlipayAgent alipayAgent;

	@RequestMapping(value = "pay")
	public String pay(String outTradeNo, String subject, String totalAmount, String body,Model model) throws Exception {
		AlipayOrderBean orderBean = new AlipayOrderBean();

		orderBean.setOutTradeNo(String.format("20000%s", new Date().getTime()));
		orderBean.setTotalAmount("1.00");
		orderBean.setSubject("醉么商城订单" +String.format("Test%s", new Date().getTime()));
		orderBean.setBody("醉么商城订单" +String.format("Test%s", new Date().getTime()));
		orderBean.setProductCode("FAST_INSTANT_TRADE_PAY");

        model.addAttribute("page",alipayAgent.buildPayUrl(orderBean));
        return "pay/alipay/create";
	}
	
	@RequestMapping(value = "return")
	public String payReturn(){
		
		return "pay/alipay/return";
	}
}
