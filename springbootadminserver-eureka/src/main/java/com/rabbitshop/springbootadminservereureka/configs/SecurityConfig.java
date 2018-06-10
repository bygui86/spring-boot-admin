package com.rabbitshop.springbootadminservereureka.configs;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Slf4j
@Configuration
@EnableAutoConfiguration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	/*
		PLEASE NOTE: For the sake of brevity we’re disabling the security for now. Have a look at the security section on how to deal with secured endpoints.
	 */
	@Override
	protected void configure(final HttpSecurity http) throws Exception {

		log.debug("Loading SECURITY config...");

		http
				.authorizeRequests()
				.anyRequest().permitAll()
				.and()
				.csrf().disable();
	}

}
