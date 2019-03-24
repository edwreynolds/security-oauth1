package com.epr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Prototype app using Spring Security, Spring Boot and OAuth2 
 * client to force authorized login to page 192.168.1.12:8080/home 
 * but not require authorization to reach default page 192.168.1.12:8080
 * 
 * Note the use of an actual IP address.  That's because in GitHub the callback
 * URL has been set to use the IP address of home development PC.  Could
 * use localhost but then app must be run on target machine.  By using
 * specific IP are able to execute this app from some other machine that
 * has newtork access to 192.168.1.12
 * 
 * Use: Spring Boot(2.1.3), WebMVC, Security(5.1), OAuth Client, Lombok, Java 8
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
