package jblog.guohai.org.model;

import lombok.Data;

/**
 * 企业微信授权Token
 * @author zhongdaiqi
 *
 */
@Data
public class QywxAccessTokenBean {
	private String errcode;
	private String errmsg;
	private String access_token;
	private String expires_in;
}
