package jblog.guohai.org.bll.agent.pingan;

public class Packets {
	byte[] head;
	byte[] body;

	int len;

	public byte[] getHead() {
		return head;
	}

	public String getHeadString(String charset) throws Exception {
		return null != head && head.length > 0 ? new String(head, charset) : "";
	}

	public void setHead(byte[] head) {
		this.head = head;
	}

	public byte[] getBody() {
		return body;
	}

	public String getBodyString(String charset) throws Exception {

		return null != body && body.length > 0 ? new String(body, charset) : "";
	}

	public void setBody(byte[] body) {
		this.body = body;
	}

	public int getLen() {
		return len;
	}

	public void setLen(int len) {
		this.len = len;
	}
}
