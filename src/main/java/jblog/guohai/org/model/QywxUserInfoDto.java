package jblog.guohai.org.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class QywxUserInfoDto {

	private String errcode;
	private String errmsg;
	@JsonProperty("UserId")
	private String userId;
	@JsonProperty("DeviceId")
	private String deviceId;
	
}
