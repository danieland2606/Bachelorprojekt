package com.meowmed.rdanotification;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NotificationApplication {
	@Rest
	public static void main(String[] args) {
		SpringApplication.run(NotificationApplication.class, args);
	}

}
