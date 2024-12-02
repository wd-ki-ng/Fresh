package com.fresh.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class BoardController {
	
	@GetMapping({"/","/index"})
	public String index() {
		return "/index";
	}
}
