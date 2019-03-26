package com.epr.controller;

import java.io.IOException;
import java.io.Writer;
import java.util.Collection;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class HomeController {

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	@Autowired
	OAuth2AuthorizedClientService clientService;

	@GetMapping("/")
	public String index(Model model) {
		logger.info("HomeController no security");
		return "index";
	}

	@GetMapping(value = "/home")
	public String home(Model model) {
		logger.info("HomeController login required");
		return "home";
	}

	private OAuth2AuthorizedClient getAuthorizedClient(OAuth2AuthenticationToken authentication) {
		return clientService.loadAuthorizedClient(authentication.getAuthorizedClientRegistrationId(), authentication.getName());
	}	

	private String getRepoInfo() {

		// After a successful authentication with an external OAuth 2 service, the
		// Authentication object kept in the security context is actually an 
		// OAuth2AuthenticationToken which, along with help from OAuth2AuthorizedClientService 
		// can avail us with an access token for making requests against the service’s API.
		//
		// The Authentication can be obtained in many ways, including via SecurityContextHolder.
		// Once you have the Authentication, you can cast it to OAuth2AuthenticationToken.
		//
		// There will be an OAuth2AuthorizedClientService automatically configured as a
		// bean in the Spring application context.
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
		OAuth2AuthorizedClient client = getAuthorizedClient(oauthToken);
		if (client == null) {
			logger.error("Unable to load authorized client");
			throw new IllegalAccessError("Unable to load authorized client");
		}
		// Request the access token from the OAuth service.
		final String accessToken = client.getAccessToken().getTokenValue();
		logger.info("Access token: {}", accessToken);
		
		Object principal = authentication.getPrincipal();
		String username;
		if (principal instanceof UserDetails) {
			username = ((UserDetails) principal).getUsername();
		} else {
			username = principal.toString();
		}
		logger.info("username(principal)={}",username);
		
//		StringBuilder sb = new StringBuilder();
//		sb.append("oauthToken=");
//		sb.append(oauthToken);
//		sb.append(System.lineSeparator());
//		sb.append("clientId=");
//		sb.append(oauthToken.getAuthorizedClientRegistrationId());
//		sb.append(System.lineSeparator());
//		sb.append("principleName=");
//		sb.append(oauthToken.getName());
//		sb.append(System.lineSeparator());
//		sb.append("accessToken=");
//		sb.append(accessToken);
//		System.out.println(sb.toString());

		// https://www.baeldung.com/spring-security-5-oauth2-login
		// https://www.javainuse.com/spring/spring-boot-oauth-access-token
		// https://www.baeldung.com/spring-security-openid-connect

		// https://developer.github.com/v3/
		// https://developer.github.com/apps/building-oauth-apps/understanding-scopes-for-oauth-apps/
		// This URL shows list of all GitHUb API access points: https://api.github.com/
		//
		// https://api.github.com/user/repos?access_token=#################################
		// https://api.github.com/user?access_token=#################################
		//
		// Access resource server to get info about repositories. Note repo is one
		// of the scopes requested when contacting GitHub for authorization
		// final String uri = "https://api.github.com/user/repos?access_token=" +
		// accessToken;
		final String uri = "https://api.github.com/repos/edwreynolds/security-oauth1?access_token=" + accessToken;
		RestTemplate restTemplate = new RestTemplate();
		return restTemplate.getForObject(uri, String.class);
	}

	private String getRepoInfoEx() {

		// After a successful authentication with an external OAuth 2 service, the
		// Authentication object kept in the security context is actually an 
		// OAuth2AuthenticationToken which, along with help from OAuth2AuthorizedClientService 
		// can avail us with an access token for making requests against the service’s API.
		//
		// The Authentication can be obtained in many ways, including via SecurityContextHolder.
		// Once you have the Authentication, you can cast it to OAuth2AuthenticationToken.
		//
		// There will be an OAuth2AuthorizedClientService automatically configured as a
		// bean in the Spring application context.
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
		OAuth2AuthorizedClient client = getAuthorizedClient(oauthToken);
		if (client == null) {
			logger.error("Unable to load authorized client");
			throw new IllegalAccessError("Unable to load authorized client");
		}
		
		// UserDetails is a core interface in Spring Security
		Object principal = authentication.getPrincipal();
		String username;
		if (principal instanceof UserDetails) {
			username = ((UserDetails) principal).getUsername();
		} else if (principal instanceof DefaultOAuth2User) {
			logger.info("principal(DefaultOAuth2User)={}",principal.toString());
			username = (String) ((DefaultOAuth2User) principal).getAttributes().get("login");
		} else {
			username = principal.toString();
		}
		logger.info("username(principal)={}",username);
		
		// GrantedAuthority, to reflect the application-wide permissions granted to a principal
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		for(GrantedAuthority auth : authorities) {
			logger.info("GrantedAuthority{}",auth);
		}
		
		// Request the access token from the OAuth service.
		final String accessToken = client.getAccessToken().getTokenValue();
		logger.info("Access token: {}", accessToken);
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);
		HttpEntity<String> entity = new HttpEntity<>("", headers);
		final String uri = "https://api.github.com/repos/edwreynolds/security-oauth1";
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);
		logger.info("HTTP Response Code={}",response.getStatusCodeValue());
		return response.getBody();
	}

	@PostMapping(value = "/getRepos")
	public @ResponseBody void getGitHubRepos(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		logger.info("Received request 'getRepos'");

		response.setContentType("application/json; charset=utf8");
		// Send results in JSON format using Jackson JSON library.
		JsonGenerator jGen = null;
		try {

			// Use Writer or OutputStream depending on whether you want to send textual or
			// binary data.
			Writer writer = response.getWriter();
			ObjectMapper mapper = new ObjectMapper();
			jGen = mapper.getFactory().createGenerator(writer);

			//String repoTxt = getRepoInfo();
			String repoTxt = getRepoInfoEx();
			jGen.writeRaw(repoTxt);

			// Pretty print the JSON string
			Object json = mapper.readValue(repoTxt, Object.class);
			if (logger.isDebugEnabled()) {
				logger.debug("{}", mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json));
			}
			//

		} catch (Exception e) {
			logger.error("Get repo info: ", e);
			if (jGen != null) {
				try {
					jGen.writeStartObject();
					jGen.writeStringField("status", "FAIL");
					jGen.writeStringField("errorTxt", "Run-time exception. Cause=" + e.toString());
					jGen.writeEndObject();
				} catch (Exception ex) {
					logger.error("Exception {}", ex.getMessage());
				}
			} else {
				// Generally this results in a HTTP 500 error
				logger.warn("Failed create JSON response");
			}
		} finally {
			if (jGen != null) {
				jGen.close();
			}
		}
	}

	
	// https://docs.spring.io/spring-security/site/docs/5.1.x/reference/pdf/spring-security-reference.pdf

	// https://developer.okta.com/blog/2017/12/18/spring-security-5-oidc	
//	@PostMapping(value = "/getOktaUserInfo")
//	public @ResponseBody void getOktaUserInfo(HttpServletRequest request, HttpServletResponse response, OAuth2AuthenticationToken authentication)
//			throws IOException {
//
//		OAuth2AuthorizedClient authorizedClient = getAuthorizedClient(authentication);
//		String userInfoEndpointUri = authorizedClient.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUri();
//		if (!StringUtils.isEmpty(userInfoEndpointUri)) { 
//			// userInfoEndpointUri is optional for OIDC Clients
//			userAttributes = WebClient.builder().filter(oauth2Credentials(authorizedClient)).build().get()
//					.uri(userInfoEndpointUri).retrieve().bodyToMono(Map.class).block();
//		}
//		
//		logger.info("Received request 'getReposEx'");
//
//		response.setContentType("application/json; charset=utf8");
//		// Send results in JSON format using Jackson JSON library.
//		JsonGenerator jGen = null;
//		try {
//
//			// Use Writer or OutputStream depending on whether you want to send textual or
//			// binary data.
//			Writer writer = response.getWriter();
//			ObjectMapper mapper = new ObjectMapper();
//			jGen = mapper.getFactory().createGenerator(writer);
//
//			//String repoTxt = getRepoInfo();
//			String repoTxt = getRepoInfoEx();
//			jGen.writeRaw(repoTxt);
//
//			// Pretty print the JSON string
//			Object json = mapper.readValue(repoTxt, Object.class);
//			if (logger.isDebugEnabled()) {
//				logger.debug("{}", mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json));
//			}
//			//
//
//		} catch (Exception e) {
//			logger.error("Get repo info: ", e);
//			if (jGen != null) {
//				try {
//					jGen.writeStartObject();
//					jGen.writeStringField("status", "FAIL");
//					jGen.writeStringField("errorTxt", "Run-time exception. Cause=" + e.toString());
//					jGen.writeEndObject();
//				} catch (Exception ex) {
//					logger.error("Exception {}", ex.getMessage());
//				}
//			} else {
//				// Generally this results in a HTTP 500 error
//				logger.warn("Failed create JSON response");
//			}
//		} finally {
//			if (jGen != null) {
//				jGen.close();
//			}
//		}
//	}
	

}
