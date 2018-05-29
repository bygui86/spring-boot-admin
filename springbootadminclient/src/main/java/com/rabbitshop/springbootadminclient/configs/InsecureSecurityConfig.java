package com.rabbitshop.springbootadminclient.configs;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


@Slf4j
@Configuration
@EnableAutoConfiguration
@Profile("insecure")
public class InsecureSecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(final HttpSecurity httpSecurity) throws Exception {

		log.debug("Loading INSECURE config...");

		httpSecurity
				.csrf().disable()
				.httpBasic()
				.and()

				.authorizeRequests()
				.anyRequest().permitAll()
		;
	}

}
