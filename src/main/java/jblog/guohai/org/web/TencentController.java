
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
	    
	
	}
}
