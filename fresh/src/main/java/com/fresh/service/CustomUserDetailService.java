package com.fresh.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.fresh.dto.CustomUserDetails;
import com.fresh.dto.UserDTO;
import com.fresh.repository.UserRepository;

@Service
public class CustomUserDetailService implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		//ユーザーのアカウント情報をデータベースから抽出し、取り込む
		UserDTO user = userRepository.findByUserId(username);
		
		if (user != null) {
			System.out.println("CustomUserDetailService : " + user.toString());
			return new CustomUserDetails(user);
		}
		System.out.println("存在しない会員");
		return null;
	}
	
}
