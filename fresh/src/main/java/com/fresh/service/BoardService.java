package com.fresh.service;
 
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fresh.dto.BoardDTO;
import com.fresh.dto.CommentDTO;
import com.fresh.dto.CustomCommentDTO;
import com.fresh.dto.UserDTO;
import com.fresh.repository.BoardRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardService {
	
	@Autowired
	private BoardRepository boardRepository;
	
	// 상세 게시글 보기
	public BoardDTO getDetail(Long no) {
		return boardRepository.getDetail(no);
	}
	
	// 조회수 늘리기
	public int setView(Long no) {
		return boardRepository.setView(no);
	}
	
	// 게시글 작성
	public int setBoard(BoardDTO board, Long no, String name) {
		board.setUser_no(no);
		board.setBoard_write(name);
		return boardRepository.setBoard(board);
	}
	
	//게시글 수정 ------------------------------------------------------
	public int boardUpdate(BoardDTO board) {
		return boardRepository.boardUpdate(board);
		
	}

	public BoardDTO findById(Long no) {
	    return boardRepository.findById(no);
	}
	
	
	
	//----------------------------------
	
	// 댓글 개수 가져오기
	public int getCommentCount(Long no) {
		return boardRepository.getCommentCount(no);
	}
	
	// 댓글 가져오기
	public List<CustomCommentDTO> getComments(Long no) {
		return boardRepository.getComments(no);
	}
	
	// 댓글 작성
	public int setComment(CommentDTO comment, Long user_no) {
		comment.setUser_no(user_no);
		return boardRepository.setComment(comment);
	}
	
	// 통합 인기글 : 추천수 + 조회수를 더한 '반응수' 열을 만들고, 반응수가 높은 순으로 정렬
	public List<BoardDTO> getMainHotPost() {
		return boardRepository.getMainHotPost();
	}
	
	// 통합 최신글 : 게재일 순으로 정렬해서 가장 최신 글 5개
	public List<BoardDTO> getMainNewPost() {
		return boardRepository.getMainNewPost();
	}
	
	// 메인페이지 공지글 : 게재일 순으로 정렬해서 가장 최신 글 5개
	public List<BoardDTO> getMainNotice() {
		return boardRepository.getMainNotice();
	}
	
	// 게시판 리스트 
	public List<BoardDTO> getBoardList() {
		return boardRepository.getBoardList();
	}


}


