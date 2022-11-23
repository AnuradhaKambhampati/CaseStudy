package com.propertysearch.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/propertySearch")
public class PropertySearchController {
	
	@GetMapping("/login")
	public String login() {
		return "Logged In..";
	}
	
	@PostMapping("/signup")
	public void signup() {
		
	}

}
