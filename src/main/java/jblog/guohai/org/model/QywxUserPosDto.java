package jblog.guohai.org.model;

import lombok.Data;

/**
 * 企业微信用户职位信息
 * @author zhongdaiqi
 *
 */
@Data
public class QywxUserPosDto {
	private String errcode;
	private String errmsg;
	private String userid;
	private String name;
	private String mobile;
	private String[] department;
	private String position;
	private String email;
	private String avatar;
	private String qr_code;
}
