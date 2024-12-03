package com.fresh.service;

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

	public Object getAnswerContent(int board_del) {
		// TODO Auto-generated method stub
		return null;
	}

}
