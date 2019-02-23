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
 * @author epr12
 *
 */
@SpringBootApplication
public class SecurityOauth1Application {

	public static void main(String[] args) {
		SpringApplication.run(SecurityOauth1Application.class, args);
	}

}
