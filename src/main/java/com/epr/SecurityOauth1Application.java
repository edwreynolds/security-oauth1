package com.epr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Prototype app using Spring Security and OAuth2 client to force login to 
 * page localhost:8099/secured/home but not require authentication to 
 * reach default page localhost:8099
 * 
 * Use: Spring Boot(2.13), WebMVC, Security, OAuth Client, Lombok, Java 8
 * 
 * Github is being used for OAuth2 authentication
 * 
 * Banner courtesy of: http://patorjk.com/software/taag/#p=display&f=Big%20Money-sw&t=Security-OAuth-1
 * 
 * REF:
 * https://spring.io/blog/2018/03/06/using-spring-security-5-to-integrate-with-oauth-2-secured-services-such-as-facebook-and-github
 * https://spring.io/guides/tutorials/spring-boot-oauth2/
 * https://www.baeldung.com/spring-security-5-oauth2-login
 * https://developer.github.com/apps/building-oauth-apps/authorizing-oauth-apps/
 * 
 * @author epr12
 *
 */
@SpringBootApplication
public class SecurityOauth1Application {

	public static void main(String[] args) {
		SpringApplication.run(SecurityOauth1Application.class, args);
	}

}
