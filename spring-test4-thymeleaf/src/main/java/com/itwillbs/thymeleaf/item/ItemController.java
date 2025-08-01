package com.itwillbs.thymeleaf.item;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.log4j.Log4j2;

@Controller
@RequestMapping("/items")
@Log4j2
public class ItemController {
	@GetMapping("")
	public String main() {
		return "items/item_main";
	}
	
}






















