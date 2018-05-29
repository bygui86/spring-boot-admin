package com.rabbitshop.springbootadminclient.configs;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import java.util.Arrays;


@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter(value = AccessLevel.PROTECTED)
@Configuration
@EnableAutoConfiguration
@Profile("secure")
public class SecureSecurityConfig extends WebSecurityConfigurerAdapter {

	@Value("${spring.security.user.roles}")
	String[] clientRoles;

	@Override
	protected void configure(final HttpSecurity httpSecurity) throws Exception {

		log.debug("Loading SECURE config...");

		log.warn("Loading roles: {}", Arrays.toString(clientRoles));

		httpSecurity

				.csrf().disable()

				.httpBasic()
				.and()

				.authorizeRequests()
				// PLEASE NOTE: There is no need to expose all urls ("/**") with one role, for Spring Boot Admin the Spring Actuator endpoints are enough
				.antMatchers("/actuator/**").hasAnyRole(getClientRoles())
				.anyRequest().authenticated()
		;
	}

}
