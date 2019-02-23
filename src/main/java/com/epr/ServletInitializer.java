package com.epr;

import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

public class ServletInitializer extends SpringBootServletInitializer implements ApplicationRunner, ApplicationListener<ApplicationEvent> {

	private static final Logger appLogger = LoggerFactory.getLogger(ServletInitializer.class);

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(SecurityOauth1Application.class);
	}

	// Means to get the app context, it's simply a Spring managed bean
	@Autowired
	private ApplicationContext appContext;
	
	/**
	 * Override ApplicationRunner run 
	 */
	@Override
	public void run(ApplicationArguments args) throws Exception {
		appLogger.info("*** ServletInitializer is running.  Context Id={} ***", appContext.getId());
		// context ID is derived from app property: spring.application.name
		Set<String> optionArgList = args.getOptionNames();
		for (String argItem : optionArgList) {
			List<String> optionArgValuesList = args.getOptionValues(argItem);
			for (String argValue : optionArgValuesList) {
				appLogger.info("Optional app arg {} -> {}", argItem, argValue);
			}
		}
		List<String> nonOptionArgList = args.getNonOptionArgs();
		for (String argItem : nonOptionArgList) {
			appLogger.info("Non-optional app arg: {}", argItem);
		}
	}

	@Override
	public void onApplicationEvent(ApplicationEvent event) {
		// Record all 'ApplicationEvent'
		appLogger.info("<APP EVENT> {}", event.getClass().getCanonicalName());
	}
	
}
