package ru.diasoft.micro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableDiscoveryClient
@EnableAutoConfiguration
@EnableCaching
@SpringBootApplication
@ComponentScan({"ru.diasoft.micro", "ru.diasoft.micro"})
@EnableJpaRepositories({"ru.diasoft.micro", "ru.diasoft.micro"})
@EntityScan({"ru.diasoft.micro", "ru.diasoft.micro"})
public class TemplateApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(TemplateApplication.class, args);
	}
	

}
