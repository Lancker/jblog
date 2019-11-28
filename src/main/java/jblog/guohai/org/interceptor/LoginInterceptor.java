package jblog.guohai.org.interceptor;

import jblog.guohai.org.model.UserModel;
import jblog.guohai.org.service.UserServiceImpl;
import jblog.guohai.org.util.JsonTool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
public class LoginInterceptor implements HandlerInterceptor {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws IOException {
        String uuid = null;
        if (null == request.getCookies()) {
        	logger.info("获取cookie为空");
            response.sendRedirect("/admin/");
            return false;
        }
        for (Cookie cookie : request.getCookies()) {
        	
            if (cookie.getName().equals("user")) {
                uuid = cookie.getValue();
                break;
            }
        }
        logger.info("获取uuid："+uuid);
        if (null == uuid) {
            response.sendRedirect("/admin/");
            return false;
        }
        
        UserModel user = UserServiceImpl.getUserByUUID(uuid);
        logger.info("获取user："+JsonTool.toStrFormBean(user));
        if (null == user) {
            response.sendRedirect("/admin/");
            return false;
        }
        return true;
    }
}
