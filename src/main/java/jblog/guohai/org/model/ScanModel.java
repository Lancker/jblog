package jblog.guohai.org.model;

import java.util.Date;

import lombok.Data;

/**
 * 扫码流水
 * @author zhongdaiqi
 *
 */
@Data
public class ScanModel {
	private int scanCode;
	private String scanUserId;
	private String scanName;
	private String scanMobile;
	private String scanDepartment;
	private String scanPosition;
	private String scanEmail;
	private String scanAvatar;
	private String scanQrcode;
	private String scanQrcodeTitle;
	private Date scanDate;
}
