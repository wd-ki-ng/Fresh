package com.fresh.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.fresh.dto.UserDTO;
import com.fresh.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	

	
	public UserDTO list() {
		return userRepository.list();
	}
	
	public void joinProcess(UserDTO user) {
		
		user.setUser_pw(bCryptPasswordEncoder.encode(user.getUser_pw()));
		userRepository.joinProcess(user);
	}

	public UserDTO findByUserId(String user_id) {
		return userRepository.findByUserId(user_id);
	}
	
	public UserDTO findByUserUserName(String user_username) {
		return userRepository.findByUserUserName(user_username);
	}
	
	//아이디 중복체크
	public String checkUserId(String user_id) {
		return userRepository.checkUserId(user_id);
	}
	
	//닉네임 중복체크
	public String checkUserUserName(String user_username) {
		return userRepository.checkUserUserName(user_username);
	}
	
	// 아이디 찾기 서비스
    public String findIdByNameAndEmail(UserDTO user) {
    	return userRepository.findIdByNameAndEmail(user);
    }
	
}
