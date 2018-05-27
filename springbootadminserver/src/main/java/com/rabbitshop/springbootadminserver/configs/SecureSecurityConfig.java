package com.rabbitshop.springbootadminserver.configs;

import de.codecentric.boot.admin.server.config.AdminServerProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

@Slf4j
@Configuration
@EnableAutoConfiguration
@Profile("secure")
public class SecureSecurityConfig extends WebSecurityConfigurerAdapter {

	private final String adminContextPath;

	public SecureSecurityConfig(AdminServerProperties adminServerProperties) {

		this.adminContextPath = adminServerProperties.getContextPath();
	}

	@Override
	protected void configure(final HttpSecurity http) throws Exception {

		log.warn("Loading SECURE config...");

		SavedRequestAwareAuthenticationSuccessHandler successHandler = new SavedRequestAwareAuthenticationSuccessHandler();
		successHandler.setTargetUrlParameter("redirectTo");

		log.debug("Admin Context Path: " + adminContextPath);

		http.authorizeRequests()
				.antMatchers(adminContextPath + "/assets/**").permitAll()
				.antMatchers(adminContextPath + "/login").permitAll()
				.anyRequest().authenticated()
				.and()
				.formLogin().loginPage(adminContextPath + "/login").successHandler(successHandler)
				.and()
				.logout().logoutUrl(adminContextPath + "/logout")
				.and()
				.httpBasic()
				.and()
				.csrf().disable();
	}

}
