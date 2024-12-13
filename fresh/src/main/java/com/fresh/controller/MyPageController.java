package com.fresh.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fresh.dto.CustomUserDetails;
import com.fresh.dto.UserDTO;
import com.fresh.service.CustomUserDetailService;
import com.fresh.service.MypageService;
import com.fresh.util.UserUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/mypage")
public class MyPageController {
	
	@Autowired
	private UserUtil userUtil;
	
	@Autowired
	private MypageService mypageService;

	@Autowired
	private CustomUserDetailService customUserDetailService;
	

	@GetMapping("/mypage")
	public  String mypage(Model model, Principal principal) {
        if (principal == null) {
            // ログインしていない場合、ログインページへ
            return "redirect:/login";
        }

        // Principal オブジェクトでユーザーの名前をもらう
        String name = principal.getName();

        // ユーザーの名前でCustomUserDetailsのオブジェクトをもらう
        CustomUserDetails customUserDetails = (CustomUserDetails) customUserDetailService.loadUserByUsername(name);
        
        // CustomUserDetailsのオブジェクトからユーザーの名前とユーザーの名をもらう
        String username = customUserDetails.getName();
        String userId = customUserDetails.getUsername();
        
        
        // 他に必要なデータをモデルに追加
        model.addAttribute("user", userUtil.getUserData());

        return "mypage/mypage";
	}
	
	@GetMapping("/board")
	public String mypageBoard(Model model) {
		UserDTO user = userUtil.getUserData();
		model.addAttribute("user", user);
		return "mypage/mypageBoard";
	}
	
	//パスワードを変更する
   @PostMapping("/change")
   public String changePw(UserDTO userDTO ,HttpServletRequest request, HttpServletResponse response) {
	   userDTO.setUser_no(userUtil.getUserData().getUser_no());
	   mypageService.changePw(userDTO);
		   
	   Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	   if(authentication != null) {
		   new SecurityContextLogoutHandler().logout(request, response, authentication);
	   }
	   
	   return "redirect:/";
	   
   }
	
}
