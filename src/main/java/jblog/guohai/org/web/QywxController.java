package jblog.guohai.org.web;

import jblog.guohai.org.bll.agent.QywxAgent;
import jblog.guohai.org.model.*;
import jblog.guohai.org.service.BlogService;
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

	@Autowired
	BlogService blogService;

	@RequestMapping(value = "qrcode")
	public String pay(Model model,String qrcode) throws Exception {
		model.addAttribute("qrcode", qrcode);
		return "qywx/qrcode";
	}

	@RequestMapping(value = "/check")
	public String check(Model model, String code,String state,String uuid) {
		logger.info(String.format("企业微信登陆参数 code: %s state:%s uuid:%s", code,state,uuid));
		if(StringUtils.isEmpty(uuid)){
			logger.info("物品uuid为空");
			model.addAttribute("success", false);
			model.addAttribute("message", "物品信息为空");
			return "qywx/check";
		}
		
		Result<String> ret = qywxAgent.getAccessToken();
		if(!ret.isStatus()){
			logger.info("企业微信授权信息出错");
			model.addAttribute("success", false);
			model.addAttribute("message", "企业微信授权信息出错，请联系活动主办方处理");
			return "qywx/check";
		}
		//我们要维护一下 accessToken 不然每次登陆都会去取 accessToken 腾讯会block的
		QywxAccessTokenDto token = JsonTool.toBeanFormStr(ret.getData(), QywxAccessTokenDto.class);
		logger.info("企业微信授权AccessToken:%s"+JsonTool.toStrFormBean(token));
		if(null==token){
			logger.info("企业微信授权Token为null");
			model.addAttribute("success", false);
			model.addAttribute("message", "企业微信授权出错，请联系活动主办方处理");
			return "qywx/check";
		}
		Result<String> userInfoRet = qywxAgent.getUserInfo(token.getAccess_token(), code);
		if(!userInfoRet.isStatus()){
			logger.info("用户信息获取失败");
			model.addAttribute("success", false);
			model.addAttribute("message", "用户信息获取失败，请稍后再试");
			return "qywx/check";
		}
		
		QywxUserInfoDto userInfo = JsonTool.toBeanFormStr(userInfoRet.getData(), QywxUserInfoDto.class);
		logger.info("用户信息:%s"+JsonTool.toStrFormBean(userInfo));
		if(null==userInfo){
			logger.info("用户信息为null");
			model.addAttribute("success", false);
			model.addAttribute("message", "用户信息为空，请稍后再试");
			return "qywx/check";
		}
		if(StringUtils.isEmpty(userInfo.getUserId()) ){
			logger.info("用户id为null");
			model.addAttribute("success", false);
			model.addAttribute("message", "请使用企业微信扫码");
			return "qywx/check";
		}
		Result<String> userPosRet = qywxAgent.getUserPos(token.getAccess_token(), userInfo.getUserId());
		if(!userPosRet.isStatus()){
			logger.info("职位信息获取失败");
			model.addAttribute("success", false);
			model.addAttribute("message", "岗位信息获取不成功，请稍后再试");
			return "qywx/check";
		}
		logger.info("用户职位信息："+JsonTool.toStrFormBean(userPosRet));
		QywxUserPosDto userPos = JsonTool.toBeanFormStr(userPosRet.getData(), QywxUserPosDto.class);
		if(null==userPos){
			logger.info("用户职位信息为null");
			model.addAttribute("success", false);
			model.addAttribute("message", "岗位信息获取不成功，请稍后再试");
			return "qywx/check";
		}
		Result<QywxDepartmentResponse> fullDeptRet = qywxAgent.getFullDept(token.getAccess_token());
		if(!fullDeptRet.isStatus() || null ==fullDeptRet.getData()){
			logger.info("组织架构获取失败");
			model.addAttribute("success", false);
			model.addAttribute("message", "组织架构获取失败");
			return "qywx/check";
		}

		//获取物品信息
		BlogContent  blogContent = blogService.getByQrcode(uuid);
		if(null==blogContent){
			logger.info("物品信息获取不到");
			model.addAttribute("success", false);
			model.addAttribute("message", "物品信息获取不到");
			return "qywx/check";
		}

		Result<String> scanRet = qywxService.saveScan(userPos, uuid,fullDeptRet.getData().getDepartment());
		if(!scanRet.isStatus()){
			logger.info("保存扫码流水失败");
			model.addAttribute("success", false);
			model.addAttribute("message", "扫码未成功，请稍后再试");
			return "qywx/check";
		}
		model.addAttribute("success", true);
		model.addAttribute("message","恭喜扫码成功！");
		model.addAttribute("userPos",userPos);
		model.addAttribute("blog",blogContent);
		model.addAttribute("dept",scanRet.getData());
		return "qywx/check";
	}
}
