package com.itwillbs.db.commons.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class MainController {
	
	@GetMapping("")
	public String main() {
		return "main/main";
	}
	
}
