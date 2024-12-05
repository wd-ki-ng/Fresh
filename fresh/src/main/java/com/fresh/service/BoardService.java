package com.fresh.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fresh.dto.BoardDTO;
import com.fresh.repository.BoardRepository;

@Service
public class BoardService {
	
	@Autowired
	private BoardRepository boardRepository;

	public BoardDTO getDetail(int no) {
		return boardRepository.getDetail(no);
	}
	
	// 게시글 작성
	public int setBoard(BoardDTO board) {
		return boardRepository.setBoard(board);
	}

	public Object getAnswerContent(int board_del) {
		// TODO Auto-generated method stub
		return null;
	}
	
	// 통합 인기글 : 추천수 + 조회수를 더한 '반응수' 열을 만들고, 반응수가 높은 순으로 정렬
	public List<BoardDTO> getMainHotPost() {
		return boardRepository.getMainHotPost();
	}
	
	// 통합 최신글 : 게재일 순으로 정렬해서 가장 최신 글 5개
	public List<BoardDTO> getMainNewPost() {
		return boardRepository.getMainNewPost();
	}
	
	// 게시판 리스트 
	public List<BoardDTO> getBoardList() {
		return boardRepository.getBoardList();
	}

}
