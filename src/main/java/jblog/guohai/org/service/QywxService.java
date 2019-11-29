package jblog.guohai.org.service;

import jblog.guohai.org.model.QywxDepartmentDto;
import jblog.guohai.org.model.QywxUserPosDto;
import jblog.guohai.org.model.Result;

public interface QywxService {

	/**
	 * 保存用户扫码
	 * 组织架构树是全部的，要优化一下
	 * @param userPos
	 * @param qrcode
	 * @return
	 */

	Result<String> saveScan(QywxUserPosDto userPos, String qrcode, QywxDepartmentDto[] department);
}
