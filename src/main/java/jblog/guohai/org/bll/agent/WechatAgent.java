package jblog.guohai.org.bll.agent;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import jblog.guohai.org.model.Result;
import jblog.guohai.org.util.HttpUtil;

@Service
public class WechatAgent {

	private static String ACCESS_TOKEN_URL_TPL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=%s&secret=%s&js_code=%s&grant_type=authorization_code";
	@Value("${wechat.appId}")
	private static String appId;
	@Value("${wechat.secret}")
	private String secret;

	/**
	 * 获取AccessToken
	 * 
	 * @param code
	 * @return
	 */
	public Result<String> getWechatAccessToken(String code) {
		String accessTokenUrl = String.format(ACCESS_TOKEN_URL_TPL, appId, secret, code);
		return HttpUtil.get(accessTokenUrl, null);
	}
}
