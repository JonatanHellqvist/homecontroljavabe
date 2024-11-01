package com.homecontroljavabe.homecontroljavabe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class HomecontroljavabeApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomecontroljavabeApplication.class, args);
	}
}
