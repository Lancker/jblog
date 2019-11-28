package jblog.guohai.org.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alipay.config.AlipayConfig;
import com.alipay.util.AlipaySubmit;

@Controller
@RequestMapping("/pay/alipay/mapi")
public class AlipayMApiController {
	
	@Autowired
	private AlipayConfig alipayConfig;
	
	@Autowired
	private AlipaySubmit alipaySubmit;
	
	@RequestMapping(value = "index")
	public String accountQuery(Model model) throws Exception {

		return "pay/alipay/mapi/index";

	}

	@RequestMapping(value = "api")
	public String api(Model model, HttpServletRequest request) throws Exception {
		// 支付类型
		String payment_type = "1";
		// 必填，不能修改
		// 服务器异步通知页面路径
		String notify_url = "https://i.zuime.com/create_direct_pay_by_user-JAVA-UTF-8/notify_url.jsp";
		// 需http://格式的完整路径，不能加?id=123这类自定义参数

		// 页面跳转同步通知页面路径
		String return_url = "https://i.zuime.com/create_direct_pay_by_user-JAVA-UTF-8/return_url.jsp";
		// 需http://格式的完整路径，不能加?id=123这类自定义参数，不能写成http://localhost/

		// 商户订单号
		String out_trade_no = new String(request.getParameter("WIDout_trade_no").getBytes("ISO-8859-1"), "UTF-8");
		// 商户网站订单系统中唯一订单号，必填

		// 订单名称
		String subject = new String(request.getParameter("WIDsubject").getBytes("ISO-8859-1"), "UTF-8");
		// 必填

		// 付款金额
		String total_fee = new String(request.getParameter("WIDtotal_fee").getBytes("ISO-8859-1"), "UTF-8");
		// 必填

		// 订单描述

		String body = new String(request.getParameter("WIDbody").getBytes("ISO-8859-1"), "UTF-8");
		// 默认支付方式
		String paymethod = "bankPay";
		// 必填
		// 默认网银
		String defaultbank = new String(request.getParameter("WIDdefaultbank").getBytes("ISO-8859-1"), "UTF-8");
		// 必填，银行简码请参考接口技术文档

		// 商品展示地址
		String show_url = new String(request.getParameter("WIDshow_url").getBytes("ISO-8859-1"), "UTF-8");
		// 需以http://开头的完整路径，例如：http://www.商户网址.com/myorder.html

		// 防钓鱼时间戳
		String anti_phishing_key = "";
		// 若要使用请调用类文件submit中的query_timestamp函数

		// 客户端的IP地址
		String exter_invoke_ip = "";
		// 非局域网的外网IP地址，如：221.0.0.1

		//////////////////////////////////////////////////////////////////////////////////

		// 把请求参数打包成数组
		Map<String, String> sParaTemp = new HashMap<String, String>();
		sParaTemp.put("service", "create_direct_pay_by_user");
		sParaTemp.put("partner", alipayConfig.getPartner());
		sParaTemp.put("seller_email", alipayConfig.getSellerEmail());
		sParaTemp.put("_input_charset", AlipayConfig.input_charset);
		sParaTemp.put("payment_type", payment_type);
		sParaTemp.put("notify_url", notify_url);
		sParaTemp.put("return_url", return_url);
		sParaTemp.put("out_trade_no", out_trade_no);
		sParaTemp.put("subject", subject);
		sParaTemp.put("total_fee", total_fee);
		sParaTemp.put("body", body);
		sParaTemp.put("paymethod", paymethod);
		sParaTemp.put("defaultbank", defaultbank);
		sParaTemp.put("show_url", show_url);
		sParaTemp.put("anti_phishing_key", anti_phishing_key);
		sParaTemp.put("exter_invoke_ip", exter_invoke_ip);

		// 建立请求
		String sHtmlText = alipaySubmit.buildRequest(sParaTemp, "get", "确认");
		model.addAttribute("page", sHtmlText);
		return "pay/alipay/mapi/api";

	}
}
