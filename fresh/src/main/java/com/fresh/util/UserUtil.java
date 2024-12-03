package com.fresh.util;

import java.util.Collection;
import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.fresh.dto.UserDTO;
import com.fresh.repository.LoginRepository;

@Component
public class UserUtil {
	
	@Autowired
	private LoginRepository loginRepository;

	public String getUsername() {
		// 현재 사용자 아이디
		String id = SecurityContextHolder.getContext().getAuthentication().getName();

		return id;

	}

	public String getUserRole() {
		// 세션 사용자 권한
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		Iterator<? extends GrantedAuthority> iter = authorities.iterator();
		GrantedAuthority auth = iter.next();
		String role = auth.getAuthority();
		
		return role;
	}
	
	public UserDTO getUserNameAndRole() {
		String id = SecurityContextHolder.getContext().getAuthentication().getName();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		Iterator<? extends GrantedAuthority> iter = authorities.iterator();
		GrantedAuthority auth = iter.next();
		String role = auth.getAuthority();
		
		UserDTO user = new UserDTO();
		user.setUser_id(id);
		user.setROLE(role);
		return user;
	}
	
	public UserDTO getUserData() {
		String id = SecurityContextHolder.getContext().getAuthentication().getName();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		Iterator<? extends GrantedAuthority> iter = authorities.iterator();
		GrantedAuthority auth = iter.next();
		String role = auth.getAuthority();
		
		UserDTO user = new UserDTO();
		user.setUser_id(id);
		user.setROLE(role);
		if (user.getROLE().equals("ROLE_ANONYMOUS")) {
			return null;
		} else {
			return loginRepository.getUserData(user);
		}
	}
}
