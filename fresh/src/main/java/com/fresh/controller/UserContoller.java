package com.fresh.controller;

import java.util.HashMap;
import java.util.Map;

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
import org.springframework.web.bind.annotation.ResponseBody;

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
				return "redirect:/";
			}
		}
	
	@PostMapping("/login")
	public String login(UserDTO user){
			return  "redirect:/";
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
	
	@GetMapping("/findid")
	public String findId(Model model) {
		return "findid";
	}
	
	@GetMapping("/findpassword")
	public String findPw(Model model) {
		return "findpassword";
	}
	

	
	//会員登録 - IDの重複確認
	   @PostMapping("/checkid")
	   public ResponseEntity<Boolean> checkId(@RequestParam("userId") String userId) {
	      //System.out.println(userId);
	      String user = userService.checkUserId(userId);
	      
	      
	      /*
	      if(userId.equals(user.getUser_id())) {
	         return ResponseEntity.ok(true);
	      }else {
	         return ResponseEntity.ok(false);
	      }
	      */
	      if(user.equals("false")) {
	    	  return ResponseEntity.ok(false);
	      } else{
	    	  return ResponseEntity.ok(true);
	      }
	   }
	   


	//会員登録 - ユーザー名の重複確認
	   @PostMapping("/checkUserName")
	   public ResponseEntity<Boolean> userName(@RequestParam("userName") String userName){
		   //System.out.println(userName);
		   String user = userService.checkUserUserName(userName);
		   /*
		   if(userName.equals(user.getUser_username())) {
			   return ResponseEntity.ok(true);
		   } else {
			   return ResponseEntity.ok(false);
		   }
		   */
		   if(user.equals("false")) {
		    	  return ResponseEntity.ok(false);
		      } else{
		    	  return ResponseEntity.ok(true);
		      }
	   }
	   // IDを探すの要請を処理
	   @PostMapping("/findid")
	   @ResponseBody
	   public Map<String, Object> findId(@RequestParam("name") String name, @RequestParam("email") String email){
		   Map<String, Object> response = new HashMap<>();
		   
		   UserDTO user = new UserDTO();
		   user.setUser_name(name);
		   user.setUser_email(email);
		   
		   //サービスでIDを探す
		   String userId = userService.findIdByNameAndEmail(user);
		   
		   //IDが存在する場合は成功、応答がない場合は失敗
		   if(userId != null) {
			   response.put("success", true);
			   response.put("id", userId);
		   } else {
			   response.put("success", false);
		   }
		   
		   return response;
	   }
	   
	   
}
