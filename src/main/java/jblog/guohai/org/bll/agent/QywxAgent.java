package jblog.guohai.org.bll.agent;

import jblog.guohai.org.model.QywxDepartmentResponse;
import jblog.guohai.org.model.Result;
import jblog.guohai.org.util.HttpUtil;
import jblog.guohai.org.util.JsonTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class QywxAgent {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	private static String ACCESS_TOKEN_URL_TPL="https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=%s&corpsecret=%s";
	private static String GET_USER_INFO_TPL="https://qyapi.weixin.qq.com/cgi-bin/user/getuserinfo?access_token=%s&code=%s";
	private static String GET_USER_POS_TPL="https://qyapi.weixin.qq.com/cgi-bin/user/get?access_token=%s&userid=%s";

	private static String GET_DEPARTMENT_TPL="https://qyapi.weixin.qq.com/cgi-bin/department/list?access_token=%s";
	
	@Value("${qywx.corpid}")
	private String corpid;
	@Value("${qywx.corpsecret}")
	private String corpsecret;

	/**
	 * 获取AccessToken
	 * @return
	 */
	public Result<String> getAccessToken() {
		// 需要控制授权时长，频繁取会出问题
		String accessTokenUrl = String.format(ACCESS_TOKEN_URL_TPL, corpid, corpsecret);
		return HttpUtil.get(accessTokenUrl, null);
	}
	
	public Result<String> getUserInfo(String token,String code) {
		// 需要控制授权时长，频繁取会出问题
		String apiUrl = String.format(GET_USER_INFO_TPL, token, code);
		return HttpUtil.get(apiUrl, null);
	}
	

	/**
	 * 获取用户职位信息
	 * @param token
	 * @param userId
	 * @return
	 */
	public Result<String> getUserPos(String token,String userId) {
		// 需要控制授权时长，频繁取会出问题
		String apiUrl = String.format(GET_USER_POS_TPL, token, userId);
		return HttpUtil.get(apiUrl, null);
	}


	/**
	 * 获取全量组织架构信息
	 * @param token
	 * @param userId
	 * @return
	 */
	public Result<QywxDepartmentResponse> getFullDept(String token) {
		logger.info("获取组织架构信息");
		String apiUrl = String.format(GET_DEPARTMENT_TPL, token);
		Result<String> fullDeptRet =  HttpUtil.get(apiUrl, null);
		//获取全量组织架构信息
		if(!fullDeptRet.isStatus()){
			logger.info("获取组织架构信息失败");
			return new Result<>(false,null);
		}
		logger.info("原始组织架构信息：%s"+ JsonTool.toStrFormBean(fullDeptRet.getData()));
		QywxDepartmentResponse qywxDepartmentResponse = JsonTool.toBeanFormStr(fullDeptRet.getData(), QywxDepartmentResponse.class);
		if(null==qywxDepartmentResponse){
			logger.info("获取组织架构信息为空");
			return new Result<>(false,null);
		}
		logger.info("组织架构信息:"+JsonTool.toStrFormBean(qywxDepartmentResponse.getDepartment()));
		return new Result<>(true,qywxDepartmentResponse);
	}
}
