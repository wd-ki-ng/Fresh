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
	private AdminRepository adminRepository;
	
	public List<UserDTO> getMemList() {
		return adminRepository.getMemList();
	}
	
	public UserDTO getOneMem(Long user_no) {
		return adminRepository.getOneMem(user_no);
	}
	
	public List<BoardDTO> getMemPosts(Long user_no) {
		return adminRepository.getMemPosts(user_no);
	}
	
	public void setOneMem(UserDTO member) {
		adminRepository.setOneMem(member);
	}
	
	public void delOneMem(Long user_no) {
		adminRepository.delOneMem(user_no);
	}
	
	public List<BoardDTO> getAllPosts() {
		return adminRepository.getAllPosts();
	}
	
	public void setOneBoard_del(Long board_no) {
		adminRepository.setOneBoard_del(board_no);
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
