
package jblog.guohai.org.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tencentcloudapi.captcha.v20190722.CaptchaClient;
import com.tencentcloudapi.captcha.v20190722.models.DescribeCaptchaResultRequest;
import com.tencentcloudapi.captcha.v20190722.models.DescribeCaptchaResultResponse;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.cvm.v20170312.CvmClient;
import com.tencentcloudapi.cvm.v20170312.models.DescribeZonesRequest;

/**
 * 腾讯云
 * @author zhongdaiqi
 *
 */
@Controller
@RequestMapping("/tencent")
public class TencentController {
	@RequestMapping(value = "captcha")
	public String captcha(Model model) throws Exception {

		return "tencent/captcha";
	}
	
	@RequestMapping(value = "verify")
	@ResponseBody
	public String verify(Model model,String ticket,String randstr) throws Exception {
	     try{
	            // 实例化一个认证对象，入参需要传入腾讯云账户secretId，secretKey
	            Credential cred = new Credential("2046804662L", "05lhzYMzdWuhUpg3Lfz5Kow**");
	            
	            // 实例化要请求产品(以cvm为例)的client对象
	            ClientProfile clientProfile = new ClientProfile();
	            clientProfile.setSignMethod(ClientProfile.SIGN_TC3_256);

	            CaptchaClient client = new CaptchaClient(cred, "ap-beijing", clientProfile);
	            
	            // 实例化一个请求对象
	            DescribeCaptchaResultRequest req = new DescribeCaptchaResultRequest();
	            req.setCaptchaType(9L);
	            req.setTicket(ticket);
	            req.setCaptchaAppId(2046804662L);
	            req.setAppSecretKey("05lhzYMzdWuhUpg3Lfz5Kow**");
	            req.setRandstr(randstr);
	            
	            
	            // 通过client对象调用想要访问的接口，需要传入请求对象
	            DescribeCaptchaResultResponse resp = client.DescribeCaptchaResult(req);
	            
	            // 输出json格式的字符串回包
	            System.out.println(DescribeCaptchaResultRequest.toJsonString(resp));
	            return DescribeCaptchaResultRequest.toJsonString(resp);
	        } catch (TencentCloudSDKException e) {
	                System.out.println(e.toString());
	            	return "e.toString()";
	        }
	
	}
}
