package com.rabbitshop.springbootadminclient.configs;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;


@Slf4j
@Configuration
@EnableAutoConfiguration
@Profile("secure")
public class SecureSecurityConfig extends WebSecurityConfigurerAdapter {

	private static final String CLIENT_ROLE = "CLIENT";

	@Override
	protected void configure(final AuthenticationManagerBuilder authManagerBuilder) throws Exception {

		log.debug("Configuring global authentication management...");

		// Client
		final String username = "client";
		final String password = "clientsecret";

		log.debug("Setting user role {}, username {}", CLIENT_ROLE, username);

		authManagerBuilder
				.inMemoryAuthentication()
				.passwordEncoder(NoOpPasswordEncoder.getInstance()) // TODO: replace with a more secure PasswordEncoder
				.withUser(username)
				.password(password)
				.authorities("ROLE_CLIENT")
				.roles(CLIENT_ROLE)
		;
	}

	@Override
	protected void configure(final HttpSecurity httpSecurity) throws Exception {

		log.warn("Loading SECURE config...");

		// httpSecurity.authorizeRequests()
				// .antMatchers("*/actuator/**").permitAll()
				// .antMatchers("*/assets/**").permitAll()
				// .antMatchers("*/login").permitAll()
				// .anyRequest().authenticated()
				// .and()
				// .csrf().disable();

		// httpSecurity.authorizeRequests()
				// .antMatchers("*/**").permitAll()
				// .anyRequest().permitAll()
				// .and()
				// .csrf().disable();

		httpSecurity
				.csrf().disable()

				.httpBasic()
				// .realmName(realmName)
				// .authenticationEntryPoint(getBasicAuthenticationEntryPoint())
				.and()

				.authorizeRequests()
				.antMatchers("*/**").hasAnyRole(CLIENT_ROLE)
				.anyRequest().authenticated()
		;
	}

}
