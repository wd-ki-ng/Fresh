package com.fresh.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fresh.dto.UserDTO;
import com.fresh.service.UserService;
import com.fresh.util.UserUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class UserContoller {
	
	@Autowired
	private UserService userService;

	@Autowired
	private UserUtil userUtil;

	@GetMapping("/login")
	public String login(Model model) {
		UserDTO user = userUtil.getUserNameAndRole();
		model.addAttribute("user", user);
			if (user.getUser_id().equals("anonymousUser")) {
				return "login";
			} else {
				return "/";
			}
		}
	

	@PostMapping("/login")
	public String login(UserDTO user) {
		return "redirect:/";
	}

	@GetMapping("/logout")
	public String logout(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null) {
			new SecurityContextLogoutHandler().logout(request, response, authentication);
		}
		return "redirect:/";
	}

	@GetMapping("/join")
	public String join(Model model) {
		return "join";
	}
	
	@PostMapping("/join")
	public String joinProcess(UserDTO userDTO) {
		userService.joinProcess(userDTO);
		return "redirect:/login";
	}
	
	//회원가입 - 아이디 중복체크
	   @PostMapping("/checkid")
	   public ResponseEntity<Boolean> checkId(@RequestParam("userId") String userId) {
	      System.out.println(userId);
	      UserDTO user = userService.findByUserId(userId);
	      
	      if(userId.equals(user.getUser_id())) {
	         return ResponseEntity.ok(true);
	      } else {
	         return ResponseEntity.ok(false);
	      }
	   }
	   
	//회원가입 - 닉네임 중복체크
	   @PostMapping("/checkUserName")
	   public ResponseEntity<Boolean> userName(@RequestParam("userName") String userName){
		   System.out.println(userName);
		   UserDTO user = userService.findByUserUserName(userName);
		   
		   if(userName.equals(user.getUser_username())) {
			   return ResponseEntity.ok(true);
		   } else {
			   return ResponseEntity.ok(false);
		   }
	   }
}
