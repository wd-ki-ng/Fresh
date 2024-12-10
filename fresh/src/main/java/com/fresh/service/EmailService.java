package com.fresh.service;

import java.security.SecureRandom;
import java.util.Random;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class EmailService {
	private final JavaMailSender javaMailSender;
	
	public String sendVerificationCode(String code) {
		String verificationCode = generateEmailCode();
		String subject = "fresh 인증번호";
		String message = "인증번호는 " + verificationCode + " 입니다.";
		
		sendEmail(code, subject, message);
		return verificationCode; 									//세션에 저장하기위해서 반환
	}
	
	public void sendEmail(String recipient, String subject, String text) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(recipient);							//수신자 설정
		message.setSubject(subject);						//제목 설정
		message.setText(text);								//내용 설정
		javaMailSender.send(message);
	}
	
	//이메일 인증번호 생성 , 6자리 랜덤 알파벳 인증번호
	public String generateEmailCode() {
		int codeLength = 6;
		String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
		StringBuilder sb = new StringBuilder(codeLength);
		Random random = new SecureRandom();
		
		for (int i=0; i<codeLength; i++) {
			int index = random.nextInt(alphabet.length());
			char randomChar = alphabet.charAt(index);
			sb.append(randomChar);
		}
		return sb.toString();
	}
	
	//임시비밀번호 전송
	public void sendTempPw(String userEmail, String tempPw) {
		SimpleMailMessage message = new SimpleMailMessage();
		
		message.setTo(userEmail);
		message.setSubject("임시비밀번호입니다");
		message.setText("안녕하세요,\n\n Fresh로그인 임시비밀번호는 " + tempPw + " 입니다.\n로그인 후 비밀번호를 변경해 주세요.");
		javaMailSender.send(message);
	}
}
