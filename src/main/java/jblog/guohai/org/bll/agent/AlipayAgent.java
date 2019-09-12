package jblog.guohai.org.bll.agent;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayClient;
import com.alipay.api.AlipayConstants;
import com.alipay.api.CertAlipayRequest;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayFundTransOrderQueryModel;
import com.alipay.api.domain.AlipayFundTransToaccountTransferModel;
import com.alipay.api.domain.AlipayTradeQueryModel;
import com.alipay.api.request.AlipayFundTransOrderQueryRequest;
import com.alipay.api.request.AlipayFundTransToaccountTransferRequest;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayFundTransOrderQueryResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;

import jblog.guohai.org.model.AlipayOrderBean;

@Service
public class AlipayAgent {

	/**
	 * 网关地址
	 */
	@Value("${alipay.gateway}")
	private String gateway;

	@Value("${alipay.appId}")
	private String appId;

	@Value("${alipay.privateKey}")
	private String privateKey;

	@Value("${alipay.alipayPublicKey}")
	private String alipayPublicKey;

	@Value("${alipay.signType:RSA2}")
	private String signType;

	@Value("${alipay.appCertPath}")
	private String appCertPath;

	/**
	 * 设置支付宝公钥证书路径
	 */
	@Value("${alipay.alipayCertPath}")
	private String alipayCertPath;

	/**
	 * 设置支付宝根证书路径
	 */
	@Value("${alipay.alipayRootCertPath}")
	private String alipayRootCertPath;

	/**
	 * 配置同步通知地址
	 */
	@Value("${alipay.returnUrl}")
	private String returnUrl;

	/**
	 * 配置异步通知地址
	 */
	@Value("${alipay.notifyUrl}")
	private String notifyUrl;

	@Value("${alipay.aesKey}")
	private String aesKey;

	/**
	 * 设置字符集
	 */
	@Value("${alipay.charset:utf-8}")
	private String charset;

	public String tradePagePay(AlipayOrderBean orderBean) throws Exception {
		AlipayClient alipayClient = buildAlipayClient();
		AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
		request.setReturnUrl(returnUrl);
		request.setNotifyUrl(notifyUrl);
		String json = JSON.toJSONString(orderBean);
		request.setBizContent(json);
		request.setNeedEncrypt(true);
		return alipayClient.pageExecute(request).getBody();
	}
	
	public String tradeQuery(String tradeNo) throws Exception{
		AlipayClient alipayClient = buildAlipayClient();
		AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
		AlipayTradeQueryModel model = new AlipayTradeQueryModel();
		model.setOutTradeNo(tradeNo);
		request.setBizModel(model);	
		request.setNeedEncrypt(true);
		AlipayTradeQueryResponse response = alipayClient.certificateExecute(request);
		// 在这里检查订单的状态值  如果是 ACQ.SYSTEM_ERROR  则本次检查失败，不能更新支付单状态
		return	response.getBody();
	}

	public String transfer() throws Exception {
		AlipayClient alipayClient = buildAlipayClient();
		AlipayFundTransToaccountTransferRequest request= new AlipayFundTransToaccountTransferRequest();
		AlipayFundTransToaccountTransferModel model = new AlipayFundTransToaccountTransferModel();
		model.setAmount("0.1");
		model.setOutBizNo(String.format("T0001%s", new Date().getTime()));
		model.setPayeeType("ALIPAY_LOGONID");
		model.setPayeeAccount("zhongdaiqi@gmail.com");
		model.setPayerShowName("醉么支付转帐接口测试");
		model.setPayeeRealName("钟代麒");
		model.setRemark("转帐测试中");
		request.setBizModel(model);	
		request.setNeedEncrypt(true);
		// 如果是success则修改状态为成功，如果是SYSTEM_ERROR则，不能修改状态，要调用query接口查询结果来决定状态
	    return	alipayClient.certificateExecute(request).getBody();
	}
	
	public String transferQuery(String bizNo) throws Exception{
		AlipayClient alipayClient = buildAlipayClient();
		AlipayFundTransOrderQueryRequest request = new AlipayFundTransOrderQueryRequest();
		AlipayFundTransOrderQueryModel model = new AlipayFundTransOrderQueryModel();
		model.setOutBizNo(bizNo);
		request.setBizModel(model);	
		request.setNeedEncrypt(true);
		AlipayFundTransOrderQueryResponse response= alipayClient.certificateExecute(request);
		// 在这里检查订单的状态值
		return	response.getBody();
	}

	private AlipayClient buildAlipayClient() throws Exception {
		// 构造client
		CertAlipayRequest certAlipayRequest = new CertAlipayRequest();
		// 设置网关地址
		certAlipayRequest.setServerUrl(gateway);
		// 设置应用Id
		certAlipayRequest.setAppId(appId);
		// 设置应用私钥
		certAlipayRequest.setPrivateKey(privateKey);
		// 设置请求格式，固定值json
		certAlipayRequest.setFormat(AlipayConstants.FORMAT_JSON);
		// 设置字符集
		certAlipayRequest.setCharset(AlipayConstants.CHARSET_UTF8);
		// 设置签名类型
		certAlipayRequest.setSignType(AlipayConstants.SIGN_TYPE_RSA2);
		// 设置应用公钥证书路径
		certAlipayRequest.setCertPath(appCertPath);
		// 设置支付宝公钥证书路径
		certAlipayRequest.setAlipayPublicCertPath(alipayCertPath);
		// 设置支付宝根证书路径
		certAlipayRequest.setRootCertPath(alipayRootCertPath);
		// 构造client
		certAlipayRequest.setEncryptor(aesKey);
		certAlipayRequest.setEncryptType(AlipayConstants.ENCRYPT_TYPE_AES);
		return new DefaultAlipayClient(certAlipayRequest);
	}
}
