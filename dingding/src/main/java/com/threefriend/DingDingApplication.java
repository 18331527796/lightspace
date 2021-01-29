package com.threefriend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication()
@ComponentScan("com") // 1. 多模块项目需要扫描的包
@EnableJpaRepositories("com.threefriend.dingding") // 2. Dao 层所在的包 
@EntityScan("com.threefriend.dingding") // 3. Entity 所在的包
public class DingDingApplication extends SpringBootServletInitializer{

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		// 注意这里要指向原先用main方法执行的Application启动类
		System.out.println("这里是DingDing启动类 这是真的启动了");
		return builder.sources(DingDingApplication.class);
	}
	
	public static void main(String[] args) {
		SpringApplication.run(DingDingApplication.class, args);
	}

}
