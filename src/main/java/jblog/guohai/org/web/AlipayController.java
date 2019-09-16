package jblog.guohai.org.web;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;

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
	public String payReturn(HttpServletRequest request,Model model) throws Exception {
		//获取支付宝GET过来反馈信息
		Map<String,String> params = new HashMap<String,String>();
		Map<String,String[]> requestParams = request.getParameterMap();
		for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i]
						: valueStr + values[i] + ",";
			}
			//乱码解决，这段代码在出现乱码时使用
			//valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
			params.put(name, valueStr);
		}
		if(alipayAgent.rsaCertCheckV2(params)){
			String tradeNo = params.get("out_trade_no");
			model.addAttribute("page", String.format("签名通过,tradeNo %s", tradeNo));
			//model.addAttribute("page", alipayAgent.decrypt(params));
		}else{
			model.addAttribute("page", "签名未通过");
		}
		return "pay/alipay/create";
		
	}
	
	@RequestMapping(value = "notify")
	public String notify(HttpServletRequest request,Model model) throws Exception {
		//获取支付宝POST过来反馈信息
		Map<String,String> params = new HashMap<String,String>();
		Map<String,String[]> requestParams = request.getParameterMap();
		for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i]
						: valueStr + values[i] + ",";
			}
			//乱码解决，这段代码在出现乱码时使用
			//valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
			params.put(name, valueStr);
		}
		System.out.println("接收到参数 可用来造测试数据：");
		System.out.println(JSON.toJSONString(params));
		if(alipayAgent.rsaCertCheckV2(params)){
			//商户订单号
			String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");
		
			//支付宝交易号
			String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");
		
			//交易状态
			String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"),"UTF-8");
			
			model.addAttribute("page", String.format("签名通过,tradeNo %s", out_trade_no));
			
			//model.addAttribute("page", alipayAgent.decrypt(params));
			System.out.println(String.format("out_trade_no: %s", out_trade_no));
			System.out.println(String.format("trade_no: %s", trade_no));
			System.out.println(String.format("trade_status: %s", trade_status));
			System.out.println("success");
		}else{
			System.out.println("fail");
			model.addAttribute("page", "签名未通过");
		}
		return "pay/alipay/create";
		
		
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
