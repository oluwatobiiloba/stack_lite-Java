package com.stacklite.dev.stacklite_clone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class StackliteCloneApplication {

	public static void main(String[] args) {
		SpringApplication.run(StackliteCloneApplication.class, args);
	}

}
