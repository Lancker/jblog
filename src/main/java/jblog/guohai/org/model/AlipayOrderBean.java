package jblog.guohai.org.model;

import com.alibaba.fastjson.annotation.JSONField;

import lombok.Data;

@Data
public class AlipayOrderBean {
	/**
	 * 商户订单号，必填
	 * 
	 */
	@JSONField(name = "out_trade_no")
	private String outTradeNo;
	/**
	 * 订单名称，必填
	 */
	private String subject;
	/**
	 * 付款金额，必填 根据支付宝接口协议，必须使用下划线
	 */
	@JSONField(name = "total_amount")
	private String totalAmount;
	
	
	/**
	 * 商品描述，可空
	 */
	private String body;

	/**
	 * 产品编号
	 */
	@JSONField(name = "product_code")
	private String productCode;
}
