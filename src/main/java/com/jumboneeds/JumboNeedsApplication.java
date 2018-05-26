package com.jumboneeds;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableJpaRepositories(basePackages = {"com.jumboneeds"})
@ComponentScan(basePackages = {"com.jumboneeds"})
@EnableScheduling
@SpringBootApplication
public class JumboNeedsApplication {

	public static void main(String[] args) {
		SpringApplication.run(JumboNeedsApplication.class, args);
	}

}
