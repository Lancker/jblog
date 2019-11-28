package jblog.guohai.org.util;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

public class ResponseUtil {
	/**
	 * 
	 * <p>
	 * 输出纯消息提示
	 * </p>
	 * 
	 * @action zhongdaiqi 2019年6月19日 下午3:22:57
	 * @param @param
	 *            httpResponse
	 * @param @param
	 *            message
	 * @return void
	 * @throws IOException
	 */
	public static void write(HttpServletResponse httpResponse, String message) throws IOException {
		httpResponse.setContentType("text/html;charset=UTF-8");
		PrintWriter out = httpResponse.getWriter();
		out.write(message);
	}
}
