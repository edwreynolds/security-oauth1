package com.epr.config;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private Environment env;
	
	private static final Logger logger = LoggerFactory.getLogger(SecurityConfiguration.class);

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		// Environment variable GITHUB_OAUTH contains the client secret key
		String oauthClientKeyValue = env.getProperty("spring.security.oauth2.client.registration.github.client-secret");
		if (StringUtils.isBlank(oauthClientKeyValue) || "UNKNOWN".compareTo(oauthClientKeyValue)==0) {
			logger.error("OAuth2 client key has not been set in environment variables");
		}

		http.csrf().disable();

		http
			.antMatcher("/**").authorizeRequests()
			.antMatchers("/", "/login**", "/webjars/**", "/error**", "/images/**", "/css/**", "/js/**").permitAll()
			.anyRequest().authenticated().and().oauth2Login();

		// HttpSecurity.oauth2Client() provides a number of configuration options for customizing
		// OAuth 2.0 Client. The following code shows the complete configuration options 
		// available for the oauth2Client()
		/*
		http
		.oauth2Client()
		.clientRegistrationRepository(this.clientRegistrationRepository())
		.authorizedClientRepository(this.authorizedClientRepository())
		.authorizedClientService(this.authorizedClientService())
		.authorizationCodeGrant()
		.authorizationRequestRepository(this.authorizationRequestRepository())
		.authorizationRequestResolver(this.authorizationRequestResolver())
		.accessTokenResponseClient(this.accessTokenResponseClient());		
		*/
		
		
		// various options to further customize logout requirements:
		/*
		http
			.logout()
			.logoutUrl("logout")
			.logoutSuccessUrl("index")
			.logoutSuccessHandler(logoutSuccessHandler)
			.invalidateHttpSession(true)
			.addLogoutHandler(logoutHandler)
			.deleteCookies(cookieNamesToClear);
		*/
		
	}
	
}
