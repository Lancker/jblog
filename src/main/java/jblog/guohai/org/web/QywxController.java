package jblog.guohai.org.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import jblog.guohai.org.bll.agent.QywxAgent;
import jblog.guohai.org.model.QywxAccessTokenDto;
import jblog.guohai.org.model.QywxUserInfoDto;
import jblog.guohai.org.model.Result;
import jblog.guohai.org.util.JsonTool;

@Controller
@RequestMapping("/qywx")
public class QywxController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	QywxAgent qywxAgent;

	@RequestMapping(value = "qrcode")
	public String pay(Model model) throws Exception {
		return "qywx/qrcode";
	}

	@ResponseBody
	@RequestMapping(value = "/check")
	public String check(Model model, String code,String state) {
		logger.info(String.format("企业微信登陆参数 code: %s state:%s", code,state));
		
		Result<String> ret = qywxAgent.getAccessToken();
		if(!ret.isStatus()){
			logger.info("企业微信授权信息出错");
			return "qywx/check:"+JsonTool.toStrFormBean(ret);
		}
		//我们要维护一下 accessToken 不然每次登陆都会去取 accessToken 腾讯会block的
		
		QywxAccessTokenDto token = JsonTool.toBeanFormStr(ret.getData(), QywxAccessTokenDto.class);
		logger.info("企业微信授权AccessToken:%s"+JsonTool.toStrFormBean(token));
		if(null==token){
			logger.info("企业微信授权Token为null");
			return "qywx/check:token为空";
		}
		Result<String> userInfoRet = qywxAgent.getUserInfo(token.getAccess_token(), code);
		QywxUserInfoDto userInfo = JsonTool.toBeanFormStr(userInfoRet.getData(), QywxUserInfoDto.class);
		logger.info("用户信息:%s"+JsonTool.toStrFormBean(userInfo));
		if(null==userInfo){
			logger.info("用户信息为null");
			return "qywx/check:用户信息为空";
		}
		return "qywx/check:" + token.getAccess_token();
	}
}
