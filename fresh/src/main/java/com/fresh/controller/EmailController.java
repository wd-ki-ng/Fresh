package com.fresh.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fresh.dto.UserDTO;
import com.fresh.service.EmailService;
import com.fresh.service.UserService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class EmailController {
	private final EmailService emailService;
	
	@Autowired
	private UserService userService;
	

	
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
	
	//임시 비밀번호 발급용
	@PostMapping("/findPw")
	@ResponseBody
	public Map<String, Object> sendTempPw(UserDTO user/*@RequestParam("userId") String userId, @RequestParam("email") String email*/ ){
		Map<String, Object> response = new HashMap<>();
		
		
		UserDTO userDTO = userService.findByIdAndEmail(user);		//사용자 확인
		
		
		if(userDTO.getUser_no() != null) {
			String tempPw = generateTempPw();
			
			//response.put("tempPw", userDTO.setTemp_pw(tempPw));
			
			userDTO.setTemp_pw(tempPw);
			userService.updatePw(userDTO);							//암호화된 비밀번호 저장
			emailService.sendTempPw(user.getUser_email(), tempPw); 			//임시 비밀번호 전송(이메일)
			
			response.put("success", true);
		} else {
			response.put("success", false);
		}
		
		return response;
	}
	
	//임시 비밀번호 생성 로직
	private String generateTempPw() {
		return UUID.randomUUID().toString().substring(0,12); 		//12자리 임시비밀번호 생성
	}
}
