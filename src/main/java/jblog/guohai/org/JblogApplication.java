package jblog.guohai.org;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan("jblog.guohai.org.dao")
@ComponentScan(basePackages = { "jblog.guohai.org", "com.alipay" })
public class JblogApplication {

	public static void main(String[] args) {
		SpringApplication.run(JblogApplication.class, args);
	}

}
