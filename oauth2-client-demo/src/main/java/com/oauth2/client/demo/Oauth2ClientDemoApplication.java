package com.oauth2.client.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class Oauth2ClientDemoApplication {

	public static void main(final String[] args) {
		SpringApplication.run(Oauth2ClientDemoApplication.class, args);
	}

	@Secured("ROLE_CUSTOMER")
	@GetMapping("/hello")
	public String echo() {
		final Object user = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return "current user: " + user;
	}
}
