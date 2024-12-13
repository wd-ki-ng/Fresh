package com.fresh.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.fresh.dto.UserDTO;
import com.fresh.repository.MypageRepository;

@Service
public class MypageService {
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private MypageRepository mypageRepository;
	
	//パスワードを変更
	public void changePw(UserDTO user) {
    	user.setUser_pw(bCryptPasswordEncoder.encode(user.getUser_pw()));
    	mypageRepository.changePw(user);
    }
}
