package com.fresh.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fresh.dto.CustomUserDetails;
import com.fresh.dto.UserDTO;
import com.fresh.service.CustomUserDetailService;
import com.fresh.service.MypageService;
import com.fresh.util.UserUtil;

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
            // 로그인되지 않은 경우 로그인 페이지로 리다이렉트
            return "redirect:/login";
        }

        // Principal 객체에서 사용자의 이름 가져오기
        String name = principal.getName();

        // 사용자 이름으로 CustomUserDetails 객체 가져오기
        CustomUserDetails customUserDetails = (CustomUserDetails) customUserDetailService.loadUserByUsername(name);
        
        // CustomUserDetails 객체에서 사용자의 이름 가져오기
        String username = customUserDetails.getName();
        String userId = customUserDetails.getUsername();
        
        
        // 다른 필요한 데이터들을 모델에 추가
        model.addAttribute("user", userUtil.getUserData());

        return "mypage/mypage";
	}
	
	@GetMapping("/board")
	public String mypageBoard(Model model) {
		UserDTO user = userUtil.getUserData();
		model.addAttribute("user", user);
		return "mypage/mypageBoard";
	}
	
	
	
}
