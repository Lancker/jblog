package jblog.guohai.org.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import jblog.guohai.org.dao.BlogDao;
import jblog.guohai.org.dao.ScanDao;
import jblog.guohai.org.model.BlogContent;
import jblog.guohai.org.model.QywxUserPosDto;
import jblog.guohai.org.model.Result;
import jblog.guohai.org.model.ScanModel;
import jblog.guohai.org.util.JsonTool;
import java.util.Date;
import org.springframework.stereotype.Service;

@Service
public class QywxServiceImpl  implements QywxService {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
    @Autowired
    private BlogDao blogDao;
    
    @Autowired
    private ScanDao scanDao;
	
    /**
     * 保存用户扫码
     */
	public Result<String> saveScan(QywxUserPosDto userPos,String qrcode){
		logger.info("保存用户扫码");
		logger.info(String.format("用户职位信息 %s 二维码标识:%s", JsonTool.toStrFormBean(userPos),qrcode));
		BlogContent blog = blogDao.getByQrcode(qrcode);
		if(null==blog){
			logger.info("扫码物品不存在");
			Result.Fail();
		}
		logger.info(String.format("物品信息:%s", JsonTool.toStrFormBean(blog)));
		ScanModel scanModel = new ScanModel();
		scanModel.setScanUserId(userPos.getUserid());
		scanModel.setScanName(userPos.getName());
		scanModel.setScanMobile(userPos.getMobile());
		scanModel.setScanDepartment("待完善");
		scanModel.setScanPosition(userPos.getPosition());
		scanModel.setScanEmail(userPos.getEmail());
		scanModel.setScanAvatar(userPos.getAvatar());
		//填充一下物品信息（即博客信息）
		scanModel.setScanQrcode(blog.getPostQrcode());
		scanModel.setScanQrcodeTitle(blog.getPostTitle());
		//记录扫码时间
		scanModel.setScanDate(new Date());
		if(!scanDao.addScan(scanModel)){
			logger.info("保存扫码流水失败");
			Result.Fail();
		}
		return new Result<>(true,null);
	}


}
