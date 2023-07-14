package com.stacklite.dev.stacklite_clone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;

@SpringBootApplication
@EnableAsync
public class StackliteCloneApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(StackliteCloneApplication.class, args);
		context.registerShutdownHook();
	}

	@Configuration
	@EnableWebSecurity
	public static class SecurityConfiguration {

		@Bean
		DefaultSecurityFilterChain filterChain(HttpSecurity http) throws Exception {
			http.httpBasic().disable();
			http.csrf().disable();
			return http.build();
		}

		@Bean
		BCryptPasswordEncoder bCryptPasswordEncoder() {
			return new BCryptPasswordEncoder();
		}
	}
}
