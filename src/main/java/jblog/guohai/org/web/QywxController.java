package jblog.guohai.org.web;

import jblog.guohai.org.bll.agent.QywxAgent;
import jblog.guohai.org.model.*;
import jblog.guohai.org.service.QywxService;
import jblog.guohai.org.util.JsonTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/qywx")
public class QywxController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	QywxAgent qywxAgent;
	
	@Autowired
	QywxService qywxService;

	@RequestMapping(value = "qrcode")
	public String pay(Model model) throws Exception {
		return "qywx/qrcode";
	}

	@ResponseBody
	@RequestMapping(value = "/check")
	public String check(Model model, String code,String state,String uuid) {
		logger.info(String.format("企业微信登陆参数 code: %s state:%s uuid:%s", code,state,uuid));
		if(StringUtils.isEmpty(uuid)){
			logger.info("物品uuid为空");
			return "qywx/check:物品uuid为空";
		}
		
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
		if(!userInfoRet.isStatus()){
			logger.info("用户信息获取失败");
			return "qywx/check:"+JsonTool.toStrFormBean(userInfoRet);
		}
		
		QywxUserInfoDto userInfo = JsonTool.toBeanFormStr(userInfoRet.getData(), QywxUserInfoDto.class);
		logger.info("用户信息:%s"+JsonTool.toStrFormBean(userInfo));
		if(null==userInfo){
			logger.info("用户信息为null");
			return "qywx/check:用户信息为空";
		}
		if(StringUtils.isEmpty(userInfo.getUserId()) ){
			logger.info("用户id为null");
			return "qywx/check:用户id为空";
		}
		Result<String> userPosRet = qywxAgent.getUserPos(token.getAccess_token(), userInfo.getUserId());
		if(!userPosRet.isStatus()){
			logger.info("职位信息获取失败");
			return "qywx/check:职位信息获取失败";
		}
		logger.info("用户职位信息："+JsonTool.toStrFormBean(userPosRet));
		QywxUserPosDto userPos = JsonTool.toBeanFormStr(userPosRet.getData(), QywxUserPosDto.class);
		if(null==userPos){
			logger.info("用户职位信息为null");
			return "qywx/check:用户职位信息为空";
		}
		Result<QywxDepartmentResponse> fullDeptRet = qywxAgent.getFullDept(token.getAccess_token());
		if(!fullDeptRet.isStatus() || null ==fullDeptRet.getData()){
			logger.info("组织架构获取失败");
			return "qywx/check:组织架构获取失败";
		}

		//获取物品信息
		Result<String> scanRet = qywxService.saveScan(userPos, uuid,fullDeptRet.getData().getDepartment());
		if(!scanRet.isStatus()){
			logger.info("保存扫码流水失败");
			return "qywx/check:保存扫码流水失败";
		}
		logger.info("保存扫码流水成功");
		return "qywx/check:" + JsonTool.toStrFormBean(userPos);
	}
}
