package com.fresh.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fresh.service.EmailService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class EmailController {
	private final EmailService emailService;
	
	@PostMapping("/send-email")
	public ResponseEntity<Void> sendEmail(@RequestParam("email") String email, HttpSession session){
		//이메일 발송 및 인증번호 저장
		String verificationCode = emailService.sendVerificationCode(email);
		session.setAttribute("verificationCode", verificationCode);							//세션에 전송된 인증번호 저장
		return ResponseEntity.ok().build();
	}
	
	//입력한 인증번호와 세션에 저장된 인증번호 비교
	@PostMapping("/verify-email")
	public ResponseEntity<Boolean> verifyCode(@RequestParam("verificationCode") String verificationCode, HttpSession session){
		String storedCode = (String) session.getAttribute("verificationCode");
		
		if(storedCode != null && storedCode.equals(verificationCode)) {
			return ResponseEntity.ok(true);
		} else {
			return ResponseEntity.ok(false);
		}
	}
	
}
