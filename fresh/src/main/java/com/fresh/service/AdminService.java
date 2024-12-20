package com.fresh.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fresh.dto.BoardDTO;
import com.fresh.dto.CommentDTO;
import com.fresh.dto.UserDTO;
import com.fresh.repository.AdminRepository;

@Service
public class AdminService {
	
	@Autowired
	private AdminRepository adminRepository;
	
	// --------------------------------------会員管理--------------------------------------
	public List<UserDTO> getMemList() {					// 全ての会員リスト
		return adminRepository.getMemList();
	}
	
	public UserDTO getOneMem(Long user_no) {			// 特定の会員情報
		return adminRepository.getOneMem(user_no);
	}
	
	public List<BoardDTO> getMemPosts(Long user_no) {	// 特定の会員が書いたポストリスト
		return adminRepository.getMemPosts(user_no);
	}
	
	public void setOneMem(UserDTO member) {				// 特定の会員情報の修正
		adminRepository.setOneMem(member);
	}
	
	public void delOneMem(Long user_no) {				// 特定の会員脱退
		adminRepository.delOneMem(user_no);
	}
	// --------------------------------------会員管理--------------------------------------
	
	// ----------------------------------------ポスト管理----------------------------------------
	public List<BoardDTO> getAllPosts() {				// 削除されていないポストリスト
		return adminRepository.getAllPosts();
	}
	
	public int boardUpdate(BoardDTO board) {			// 管理者のポスト修正
		return adminRepository.boardUpdate(board);
	}

	public BoardDTO findById(Long no) {					// 修正しようとしているポストの情報を探す
	    return adminRepository.findById(no);
	}
	
	public void setOneBoard_del(Long board_no) {		// 特定のポストの削除
		adminRepository.setOneBoard_del(board_no);
	}
	// ----------------------------------------ポスト管理----------------------------------------
	
	// ----------------------------------------コメント管理----------------------------------------
	public List<CommentDTO> getAllComments() {			// 全てのコメントリスト
		return adminRepository.getAllComments();
	}
	
	public void setOneCom_del(Long com_no) {			// コメント削除
		adminRepository.setOneCom_del(com_no);
	}
	// ----------------------------------------コメント管理----------------------------------------
	
	// ----------------------------------------お知らせ管理----------------------------------------
	public List<BoardDTO> getAllNotices() {				// お知らせリスト
		return adminRepository.getAllNotices();
	}
	
	public void setNotice(BoardDTO notice, Long no, String name) {		// お知らせ作成
		notice.setUser_no(no);
		notice.setBoard_write(name);
		adminRepository.setNotice(notice);
	}
	// ----------------------------------------お知らせ管理----------------------------------------
	
	// -----------------------------------------ゴミ箱管理-----------------------------------------
	public List<BoardDTO> getDelPosts() {				// 削除されたポストのリスト
		return adminRepository.getDelPosts();
	}
	
	public List<CommentDTO> getDelComs() {				// 削除されたコメントのリスト
		return adminRepository.getDelComs();
	}
	
	public List<BoardDTO> getDelNotis() {				// 削除されたお知らせのリスト
		return adminRepository.getDelNotis();
	}
	
	public void restorePost(Long board_no) {			// 削除されたポストの復旧
		adminRepository.restorePost(board_no);
	}
	
	public void restoreComment(Long com_no) {			// 削除されたコメントの復旧
		adminRepository.restoreComment(com_no);
	}
	
	public void eliminatePost(Long board_no) {			// 削除されたポストの永久削除
		adminRepository.eliminatePost(board_no);
	}
	
	public void eliminateComment(Long com_no) {			// 削除されたコメントの永久削除
		adminRepository.eliminateComment(com_no);
	}
	// -----------------------------------------ゴミ箱管理-----------------------------------------
}
