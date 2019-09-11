package jblog.guohai.org.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class AlipayOrderBean {
	/**
	 * 商户订单号，必填
	 * 
	 */
	@JsonProperty("out_trade_no")
	private String outTradeNo;
	/**
	 * 订单名称，必填
	 */
	private String subject;
	/**
	 * 付款金额，必填 根据支付宝接口协议，必须使用下划线
	 */
	@JsonProperty("total_amount")
	private String totalAmount;
	/**
	 * 商品描述，可空
	 */
	private String body;

	/**
	 * 产品编号
	 */
	@JsonProperty("product_code")
	private String productCode;
}
