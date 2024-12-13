package com.fresh.service;

import java.security.SecureRandom;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class EmailService {
	
	private final JavaMailSender javaMailSender;
	
	public String sendVerificationCode(String code) {
		String verificationCode = generateEmailCode();
		String subject = "fresh 認証番号";
		String message = "認証番号は " + verificationCode + " でございます.";
		
		sendEmail(code, subject, message);
		return verificationCode; 									//セッションにセーブする為に返還
	}
	
	public void sendEmail(String recipient, String subject, String text) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(recipient);							//受信者を設定
		message.setSubject(subject);						//タイトルを設定
		message.setText(text);								//内容を設定
		javaMailSender.send(message);
	}
	
	//メールの認証番号を生成する, 6文字のランダムのアルファベットの認証番号
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
	
	//仮のパスワードを送信
	public void sendTempPw(String userEmail, String tempPw) {
		SimpleMailMessage message = new SimpleMailMessage();
		
		message.setTo(userEmail);
		message.setSubject("仮のパスワードでございます。");
		message.setText("Freshです,\n\n Freshの仮のパスワードは " + tempPw + " でございます。\nログインした後にパスワードを変更して下さい。");
		javaMailSender.send(message);
	}
}
