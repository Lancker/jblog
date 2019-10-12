package jblog.guohai.org.bll.agent;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import jblog.guohai.org.bll.agent.pingan.Packets;
import jblog.guohai.org.bll.agent.pingan.YQUtil;

@Service
public class PingAnAgent {
	@Value("${pingan.serverip}")
    private  String ServerIp;
	@Value("${pingan.charset}")
	private  String charset;
	@Value("${pingan.yqdm}")
	private  String yqdm;  //银企客户号
	@Value("${pingan.signingAccount}")
	private  String signingAccount;//签约帐号
	@Value("${pingan.dwdm}")
	private  String dwdm;//单位代码（协议号）
	
	/**
	 * 查询余额报文
	 */
	private static final String DATAGRAM_TPL_4001_QUERY_AMOUNT ="<?xml version=\"1.0\" encoding=\"GBK\"?>\n<Result>\n    <Account>%s</Account>    <CcyCode>RMB</CcyCode>\n</Result>";
	
	/**
	 * 查询余额
	 * @return
	 */
	public String queryAmount() throws Exception {
		String queryAmountDatagram = YQUtil.asemblyPackets("01001034300004541000","4001  ",String.format(DATAGRAM_TPL_4001_QUERY_AMOUNT, signingAccount));
		Packets packets = YQUtil.send2server("127.0.0.1", 7072, queryAmountDatagram, YQUtil.PROTOCAL_HTTP);
		
		return String.format("Head:%s,Body:%s", packets.getHeadString(charset),packets.getBodyString(charset));
	}
	
	
}
