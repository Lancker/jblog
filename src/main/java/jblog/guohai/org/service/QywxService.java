package jblog.guohai.org.service;

import jblog.guohai.org.model.QywxUserPosDto;
import jblog.guohai.org.model.Result;

public interface QywxService {
	
	/**
	 * 保存用户扫码
	 * @param userPos
	 * @param uuid
	 * @return
	 */
	Result<String> saveScan(QywxUserPosDto userPos,String qrcode);
}
