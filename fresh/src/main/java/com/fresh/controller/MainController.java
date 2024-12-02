package com.fresh.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.fresh.util.UserUtil;

@Controller
public class MainController {
	
	@Autowired
	UserUtil userUtil;
	
	@GetMapping({"/","/index"})
	public String index(Model model) {
		model.addAttribute("user", userUtil.getUserNameAndRole());
		return "index";
	}
	
	@GetMapping("/main")
	public String main(Model model) {
		model.addAttribute("user", userUtil.getUserNameAndRole());
		return "main";
	}
}
