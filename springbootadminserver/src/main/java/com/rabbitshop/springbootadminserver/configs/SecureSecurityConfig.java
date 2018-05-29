package com.rabbitshop.springbootadminserver.configs;

import de.codecentric.boot.admin.server.config.AdminServerProperties;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Getter(value = AccessLevel.PROTECTED)
@Configuration
@EnableAutoConfiguration
@Profile("secure")
public class SecureSecurityConfig extends WebSecurityConfigurerAdapter {

	String adminContextPath;

	public SecureSecurityConfig(final AdminServerProperties adminServerProperties) {

		this.adminContextPath = adminServerProperties.getContextPath();
	}

	@Override
	protected void configure(final HttpSecurity httpSecurity) throws Exception {

		log.debug("Loading SECURE config...");

		SavedRequestAwareAuthenticationSuccessHandler successHandler = getSavedRequestAwareAuthenticationSuccessHandler();

		log.debug("Admin Context Path: " + getAdminContextPath());

		httpSecurity
				.csrf().disable()

				.httpBasic()
				.and()

				.authorizeRequests()
				.antMatchers(getAdminContextPath() + "/assets/**").permitAll()
				.antMatchers(getAdminContextPath() + "/login").permitAll()
				.anyRequest().authenticated()
				.and()

				.formLogin().loginPage(getAdminContextPath() + "/login").successHandler(successHandler)
				.and()

				.logout().logoutUrl(getAdminContextPath() + "/logout")
		;
	}

	private SavedRequestAwareAuthenticationSuccessHandler getSavedRequestAwareAuthenticationSuccessHandler() {

		SavedRequestAwareAuthenticationSuccessHandler successHandler = new SavedRequestAwareAuthenticationSuccessHandler();
		successHandler.setTargetUrlParameter("redirectTo");
		return successHandler;
	}

}
