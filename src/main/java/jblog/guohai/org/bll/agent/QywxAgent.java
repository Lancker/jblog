package jblog.guohai.org.bll.agent;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import jblog.guohai.org.model.Result;
import jblog.guohai.org.util.HttpUtil;

@Service
public class QywxAgent {

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
	public Result<String> getFullDept(String token) {
		String apiUrl = String.format(GET_DEPARTMENT_TPL, token);
		return HttpUtil.get(apiUrl, null);
	}
}
