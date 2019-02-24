package com.epr.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();

		http
			.antMatcher("/**").authorizeRequests()
			.antMatchers("/", "/login**", "/webjars/**", "/error**", "/images/**", "/css/**", "/js/**").permitAll()
			.anyRequest().authenticated().and().oauth2Login();

	}
}
