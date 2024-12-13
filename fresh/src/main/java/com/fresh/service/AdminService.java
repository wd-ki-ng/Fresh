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
	
	public int boardUpdate(BoardDTO board) {
		return adminRepository.boardUpdate(board);
		
	}

	public BoardDTO findById(Long no) {
	    return adminRepository.findById(no);
	}
	
	public void setOneBoard_del(Long board_no) {
		adminRepository.setOneBoard_del(board_no);
	}
	
	public List<CustomCommentDTO> getAllComments() {
		return adminRepository.getAllComments();
	}
	
	public void setOneCom_del(Long com_no) {
		adminRepository.setOneCom_del(com_no);
	}

	public List<BoardDTO> getAllNotices() {
		return adminRepository.getAllNotices();
	}
	
	// 게시글 작성
	public void setNotice(BoardDTO notice, Long no, String name) {
		notice.setUser_no(no);
		notice.setBoard_write(name);
		adminRepository.setNotice(notice);
	}

	public List<BoardDTO> getDelPosts() {
		return adminRepository.getDelPosts();
	}
	
	public void restoreComment(Long com_no) {
		adminRepository.restoreComment(com_no);
	}
	
	public void restorePost(Long board_no) {
		adminRepository.restorePost(board_no);
	}

	public List<CustomCommentDTO> getDelComs() {
		return adminRepository.getDelComs();
	}
	
	public void eliminatePost(Long board_no) {
		adminRepository.eliminatePost(board_no);
	}
	
	public void eliminateComment(Long com_no) {
		adminRepository.eliminateComment(com_no);
	}

	public List<BoardDTO> getDelNotis() {
		return adminRepository.getDelNotis();
	}

}
