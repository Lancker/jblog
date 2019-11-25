package jblog.guohai.org.bll.agent;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayClient;
import com.alipay.api.AlipayConstants;
import com.alipay.api.CertAlipayRequest;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayFundAccountQueryModel;
import com.alipay.api.domain.AlipayFundTransOrderQueryModel;
import com.alipay.api.domain.AlipayFundTransToaccountTransferModel;
import com.alipay.api.domain.AlipayTradePagePayModel;
import com.alipay.api.domain.AlipayTradePrecreateModel;
import com.alipay.api.domain.AlipayTradeQueryModel;
import com.alipay.api.domain.ExtendParams;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayFundAccountQueryRequest;
import com.alipay.api.request.AlipayFundTransOrderQueryRequest;
import com.alipay.api.request.AlipayFundTransToaccountTransferRequest;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayFundAccountQueryResponse;
import com.alipay.api.response.AlipayFundTransOrderQueryResponse;
import com.alipay.api.response.AlipayTradePrecreateResponse;
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

	public String tradePagePayQr(AlipayOrderBean orderBean) throws Exception {
		AlipayClient alipayClient = buildAlipayClient();
		AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
		AlipayTradePagePayModel model = new AlipayTradePagePayModel();
		//request.setReturnUrl(returnUrl);
		request.setNotifyUrl(notifyUrl);
		model.setOutTradeNo(orderBean.getOutTradeNo());
		model.setSubject(orderBean.getSubject());
		model.setTotalAmount(orderBean.getTotalAmount());
		model.setBody(orderBean.getBody());
		model.setProductCode(orderBean.getProductCode());
		model.setQrPayMode("4");
		model.setQrcodeWidth(200L);
		//model.setTimeExpire("2m");
		model.setTimeoutExpress("2m");
		request.setBizModel(model);
		request.setNeedEncrypt(true);
		return alipayClient.pageExecute(request).getBody();
	}
	
	public String tradePagePayQrHb(AlipayOrderBean orderBean) throws Exception {
		AlipayClient alipayClient = buildAlipayClient();
		AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
		AlipayTradePagePayModel model = new AlipayTradePagePayModel();
		//request.setReturnUrl(returnUrl);
		request.setNotifyUrl(notifyUrl);
		model.setOutTradeNo(orderBean.getOutTradeNo());
		model.setSubject(orderBean.getSubject());
		model.setTotalAmount(orderBean.getTotalAmount());
		model.setBody(orderBean.getBody());
		model.setProductCode(orderBean.getProductCode());
		model.setQrPayMode("4");
		model.setQrcodeWidth(200L);
		//model.setTimeExpire("2m");
		model.setTimeoutExpress("2m");
		ExtendParams params = new ExtendParams();
		params.setHbFqNum("3");
		params.setHbFqSellerPercent("0");
		model.setExtendParams(params);
		request.setBizModel(model);
		request.setNeedEncrypt(true);
		return alipayClient.pageExecute(request).getBody();
	}

	public String tradeQuery(String tradeNo) throws Exception {
		AlipayClient alipayClient = buildAlipayClient();
		AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
		AlipayTradeQueryModel model = new AlipayTradeQueryModel();
		model.setOutTradeNo(tradeNo);
		request.setBizModel(model);
		request.setNeedEncrypt(true);
		AlipayTradeQueryResponse response = alipayClient.certificateExecute(request);
		// 在这里检查订单的状态值 如果是 ACQ.SYSTEM_ERROR 则本次检查失败，不能更新支付单状态
		return response.getBody();
	}

	public String transfer() throws Exception {
		AlipayClient alipayClient = buildAlipayClient();
		AlipayFundTransToaccountTransferRequest request = new AlipayFundTransToaccountTransferRequest();
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
		return alipayClient.certificateExecute(request).getBody();
	}

	public String transferQuery(String bizNo) throws Exception {
		AlipayClient alipayClient = buildAlipayClient();
		AlipayFundTransOrderQueryRequest request = new AlipayFundTransOrderQueryRequest();
		AlipayFundTransOrderQueryModel model = new AlipayFundTransOrderQueryModel();
		model.setOutBizNo(bizNo);
		request.setBizModel(model);
		request.setNeedEncrypt(true);
		AlipayFundTransOrderQueryResponse response = alipayClient.certificateExecute(request);

		// 在这里检查订单的状态值
		return response.getBody();
	}

	/**
	 * 预生成订单（当面付）
	 * 
	 * @param out_trade_no
	 * @param total_amount
	 * @return
	 */
	public String precreate(String outTradeNo, String subject, String totalAmount) throws Exception {
		AlipayClient alipayClient = buildAlipayClient();
		AlipayTradePrecreateRequest request = new AlipayTradePrecreateRequest();
		//request.setNotifyUrl(totalAmount);
		AlipayTradePrecreateModel model = new AlipayTradePrecreateModel();
		model.setOutTradeNo(outTradeNo);
		model.setTotalAmount(totalAmount);
		model.setSubject(subject);
		model.setBody("这是一个 body这是一个 body这是一个 body这是一个 body这是一个 body这是一个 body这是一个 body这是一个 body这是一个 body");
		model.setTimeoutExpress("1m");
		request.setBizModel(model);
		request.setNeedEncrypt(true);
		request.setReturnUrl(returnUrl);
		request.setNotifyUrl(notifyUrl);
		AlipayTradePrecreateResponse response = alipayClient.certificateExecute(request);

		// 在这里检查订单的状态值
		return response.getQrCode();
	}
	
	/**
	 *  余额查询
	 * @return
	 */
	public String accountQuery() throws Exception{
		AlipayClient alipayClient = buildAlipayClient();
		AlipayFundAccountQueryRequest request = new AlipayFundAccountQueryRequest();
		AlipayFundAccountQueryModel model = new AlipayFundAccountQueryModel();
		model.setAlipayUserId("2088021741763089");
		model.setAccountType("ACCTRANS_ACCOUNT");
		model.setAccountSceneCode("SCENE_000_000_000");
		request.setBizModel(model);
		request.setNeedEncrypt(true);
		AlipayFundAccountQueryResponse response = alipayClient.certificateExecute(request);
		return response.getBody();
	}

	/**
	 * 修复支付宝SDK的RSA2验签Bug
	 * 
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public boolean rsaCertCheckV2(Map<String, String> params) throws Exception {
		// 获取
		String sign = params.get("sign");
		String content = AlipaySignature.getSignCheckContentV1(params);
		return AlipaySignature.rsaCertCheck(content, sign, alipayCertPath, AlipayConstants.CHARSET_UTF8,
				AlipayConstants.SIGN_TYPE_RSA2);
	}

	public String decrypt(Map<String, String> params) throws Exception {
		String charset = params.get("charset");
		String bizContent = params.get("biz_content");

		return AlipaySignature.rsaDecrypt(bizContent, privateKey, charset);
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
