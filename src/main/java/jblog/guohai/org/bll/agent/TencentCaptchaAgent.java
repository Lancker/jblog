package jblog.guohai.org.bll.agent;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.tencentcloudapi.captcha.v20190722.CaptchaClient;
import com.tencentcloudapi.captcha.v20190722.models.DescribeCaptchaResultRequest;
import com.tencentcloudapi.captcha.v20190722.models.DescribeCaptchaResultResponse;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.common.profile.ClientProfile;

@Service
public class TencentCaptchaAgent {


	@Value("${tencent.secretId}")
	private String secretId;

	@Value("${tencent.secretKey}")
	private String secretKey;
	
	@Value("${tencent.captcha.captchaAppId}")
	private Long captchaAppId;

	@Value("${tencent.captcha.appSecretKey}")
	private String appSecretKey;
	
	
	public String verify(String ticket,String randstr,String userIp){
		 try{
	            // 实例化一个认证对象，入参需要传入腾讯云账户secretId，secretKey
	            Credential cred = new Credential(secretId, secretKey);
	            
	            // 实例化要请求产品(以cvm为例)的client对象
	            ClientProfile clientProfile = new ClientProfile();
	            clientProfile.setSignMethod(ClientProfile.SIGN_TC3_256);

	            CaptchaClient client = new CaptchaClient(cred, "ap-beijing", clientProfile);
	            
	            // 实例化一个请求对象
	            DescribeCaptchaResultRequest req = new DescribeCaptchaResultRequest();
	            req.setCaptchaType(9L);
	            req.setTicket(ticket);
	            req.setCaptchaAppId(captchaAppId);
	            req.setAppSecretKey(appSecretKey);
	            req.setRandstr(randstr);
	            req.setUserIp(userIp);
	            
	            
	            // 通过client对象调用想要访问的接口，需要传入请求对象
	            DescribeCaptchaResultResponse resp = client.DescribeCaptchaResult(req);
	            
	            // 输出json格式的字符串回包
	            System.out.println(DescribeCaptchaResultRequest.toJsonString(resp));
	            
	            return DescribeCaptchaResultRequest.toJsonString(resp);
	        } catch (TencentCloudSDKException e) {
	                System.out.println(e.toString());
	            	return e.toString();
	        }
	}
}
