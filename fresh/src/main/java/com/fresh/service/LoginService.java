package com.fresh.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.fresh.dto.UserDTO;
import com.fresh.repository.LoginRepository;

@Service
public class LoginService {
	
	@Autowired
	private LoginRepository loginRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	public UserDTO list() {
		return loginRepository.list();
	}
	
	public void joinProcess(UserDTO user) {
		user.setUser_pw(bCryptPasswordEncoder.encode(user.getUser_pw()));
		loginRepository.joinProcess(user);
	}
}
