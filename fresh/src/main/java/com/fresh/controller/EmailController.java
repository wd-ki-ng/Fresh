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
		//メール送信＆認証番号をセーブ
		String verificationCode = emailService.sendVerificationCode(email);
		session.setAttribute("verificationCode", verificationCode);							//セッションに伝達された認証番号をセーブ
		return ResponseEntity.ok().build();
	}
	
	//入力した認証番号とセッションにセーブされている認証番号を比べる
	@PostMapping("/verify-email")
	public ResponseEntity<Boolean> verifyCode(@RequestParam("verificationCode") String verificationCode, HttpSession session){
		String storedCode = (String) session.getAttribute("verificationCode");
		
		if(storedCode != null && storedCode.equals(verificationCode)) {
			return ResponseEntity.ok(true);
		} else {
			return ResponseEntity.ok(false);
		}
	}
	
	//仮パスワードを発給する
	@PostMapping("/findPw")
	@ResponseBody
	public Map<String, Object> sendTempPw(UserDTO user){
		Map<String, Object> response = new HashMap<>();
		
		
		UserDTO userDTO = userService.findByIdAndEmail(user);		//ユーザーの確認
		
		
		if(userDTO.getUser_no() != null) {
			String tempPw = generateTempPw();
			
			//response.put("tempPw", userDTO.setTemp_pw(tempPw));
			
			userDTO.setTemp_pw(tempPw);
			userService.createTempPw(userDTO);								//エンコーディングされたパスワードをセーブする
			emailService.sendTempPw(user.getUser_email(), tempPw); 			//仮のパスワードを送信(メール)
			
			response.put("success", true);
		} else {
			response.put("success", false);
		}
		
		return response;
	}
	
	//仮のパスワードの生成ロジック
	private String generateTempPw() {
		return UUID.randomUUID().toString().substring(0,12); 		//12文字の仮のパスワードを生成
	}
}
