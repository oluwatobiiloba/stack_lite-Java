package com.stacklite.dev.stacklite_clone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@SpringBootApplication
public class StackliteCloneApplication {

	public static void main(String[] args) {
		SpringApplication.run(StackliteCloneApplication.class, args);
	}

	@Configuration
	public static class SecurityConfiguration {

		@Bean
		public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
			http.httpBasic().disable();
			http.csrf().disable();
			return http.build();
		}
	}
}
