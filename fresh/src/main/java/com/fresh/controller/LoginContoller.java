package com.fresh.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.fresh.dto.UserDTO;
import com.fresh.service.LoginService;
import com.fresh.util.UserUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class LoginContoller {
	
	@Autowired
	private LoginService loginService;
	
	@Autowired
	private UserUtil userUtil;

	@GetMapping("/login")
	public String login(Model model) {
		UserDTO user = userUtil.getUserNameAndRole();
		
		model.addAttribute("user", user);
		if (user.getUser_id().equals("anonymousUser")) {
			return "/login";
		System.out.println(user.getUser_id()+ "=" +user.getROLE());
		model.addAttribute("user", user);
		if (user.getUser_id().equals("anonymousUser")) {
			return "login";
		} else {
			return "redirect:/";
		}
		
	}
	
	@PostMapping("/login")
	public String login(UserDTO user) {
		return "redirect:/";
	}
	
	
	@GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
        return "redirect:/";
    }
	
	
	@GetMapping("/join")
	public String join(Model model) {
		model.addAttribute("user", userUtil.getUserNameAndRole());
		return "/join";
	}
	
}
