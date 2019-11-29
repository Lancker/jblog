package jblog.guohai.org.bll.agent;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import jblog.guohai.org.model.Result;
import jblog.guohai.org.util.HttpUtil;

@Service
public class QywxAgent {

	private static String ACCESS_TOKEN_URL_TPL="https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=%s&corpsecret=%s";
	
	
	@Value("${qywx.corpid}")
	private String corpid;
	@Value("${qywx.corpsecret}")
	private String corpsecret;

	/**
	 * 获取AccessToken
	 * 
	 * @param code
	 * @return
	 */
	public Result<String> getAccessToken() {
		// 需要控制授权时长，频繁取会出问题
		String accessTokenUrl = String.format(ACCESS_TOKEN_URL_TPL, corpid, corpsecret);
		return HttpUtil.get(accessTokenUrl, null);
	}
	
}
