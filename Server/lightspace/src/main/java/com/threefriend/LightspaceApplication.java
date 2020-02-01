package com.threefriend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan("com") // 1. 多模块项目需要扫描的包
@EnableJpaRepositories("com.threefriend.lightspace.repository") // 2. Dao 层所在的包 
@EntityScan("com.threefriend.lightspace.mapper") // 3. Entity 所在的包
@EnableCaching
public class LightspaceApplication {

	public static void main(String[] args) {
		SpringApplication.run(LightspaceApplication.class, args);
	}

}
