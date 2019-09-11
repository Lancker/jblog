package jblog.guohai.org.bll.agent;

import java.security.GeneralSecurityException;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayClient;
import com.alipay.api.CertAlipayRequest;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.codec.Base64;
import com.alipay.api.request.AlipayTradeQueryRequest;

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
	@Value("${alipay.charset:UTF-8}")
	private String charset;

	public String buildPayUrl(AlipayOrderBean orderBean) throws  Exception{
		// 构造client
		CertAlipayRequest certAlipayRequest = new CertAlipayRequest();
		// 设置网关地址
		certAlipayRequest.setServerUrl(gateway);
		// 设置应用Id
		certAlipayRequest.setAppId(appId);
		// 设置应用私钥
		certAlipayRequest.setPrivateKey(privateKey);
		// 设置请求格式，固定值json
		certAlipayRequest.setFormat("json");
		// 设置字符集
		certAlipayRequest.setCharset(charset);
		// 设置签名类型
		certAlipayRequest.setSignType(signType);
		// 设置应用公钥证书路径
		certAlipayRequest.setCertPath(appCertPath);
		// 设置支付宝公钥证书路径
		certAlipayRequest.setAlipayPublicCertPath(alipayCertPath);
		// 设置支付宝根证书路径
		certAlipayRequest.setRootCertPath(alipayRootCertPath);
		// 构造client
		AlipayClient alipayClient;
		alipayClient = new DefaultAlipayClient(certAlipayRequest);
		
		// 构造API请求
		AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
		request.setReturnUrl(returnUrl);
		request.setNotifyUrl(notifyUrl);
		String json = JSON.toJSONString(orderBean);
		request.setBizContent(json);
		return alipayClient.certificateExecute(request).getBody();
	}

	public String encrypt(String bizContent) throws Exception{
		String fullAlg = "AES/CBC/PKCS5Padding";
		 
        Cipher cipher = Cipher.getInstance(fullAlg);
        IvParameterSpec iv = new IvParameterSpec(initIv(fullAlg));
        cipher.init(Cipher.ENCRYPT_MODE,
                new SecretKeySpec(Base64.decodeBase64(aesKey.getBytes()), "AES"),
                iv);
 
        byte[] encryptBytes = cipher.doFinal(bizContent.getBytes(charset));
        return new String(Base64.encodeBase64(encryptBytes));
		
	}
	
	 /**
     * 初始向量的方法, 全部为0. 这里的写法适合于其它算法,针对AES算法的话,IV值一定是128位的(16字节).
     *
     * @param fullAlg
     * @return
     * @throws GeneralSecurityException
     */
    private static byte[] initIv(String fullAlg) throws GeneralSecurityException {
        Cipher cipher = Cipher.getInstance(fullAlg);
        int blockSize = cipher.getBlockSize();
        byte[] iv = new byte[blockSize];
        for (int i = 0; i < blockSize; ++i) {
            iv[i] = 0;
        }
        return iv;
    }
}
