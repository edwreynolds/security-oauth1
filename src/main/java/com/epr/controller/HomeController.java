package com.epr.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	@RequestMapping("/")
	String index(Model model) {
		logger.info("HomeController no security");
		return "index";
	}

    @PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping("**/secured/home")
	String home(Model model) {
		logger.info("HomeController login required");
		return "home";
	}

}
