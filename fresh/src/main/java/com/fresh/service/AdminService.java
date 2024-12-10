package com.fresh.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.fresh.dto.BoardDTO;
import com.fresh.dto.CustomCommentDTO;
import com.fresh.dto.UserDTO;
import com.fresh.repository.AdminRepository;
import com.fresh.repository.UserRepository;

@Service
public class AdminService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private AdminRepository adminRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	public List<UserDTO> getMemList() {
		return adminRepository.getMemList();
	}
	
	public UserDTO getOneMem(Long user_no) {
		return adminRepository.getOneMem(user_no);
	}
	
	public List<BoardDTO> getAllPosts() {
		return adminRepository.getAllPosts();
	}
	
	public List<CustomCommentDTO> getAllComments() {
		return adminRepository.getAllComments();
	}

	public List<BoardDTO> getAllNotices() {
		return adminRepository.getAllNotices();
	}

	public List<BoardDTO> getDelPosts() {
		return adminRepository.getDelPosts();
	}

	public List<CustomCommentDTO> getDelComs() {
		return adminRepository.getDelComs();
	}

	public List<BoardDTO> getDelNotis() {
		return adminRepository.getDelNotis();
	}

}
