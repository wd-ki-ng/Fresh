package com.fresh.repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.fresh.dto.BoardDTO;

@Repository
public class BoardRepository {

	@Autowired
	private SqlSession sqlSession;
	
	public BoardDTO getDetail(int no) {
		return sqlSession.selectOne("board.detail", no);
	}
	
	public List<BoardDTO> getMainHotPost() {
		return sqlSession.selectList("board.main_hot");
	}
	
	public List<BoardDTO> getMainNewPost() {
		return sqlSession.selectList("board.main_new");
	}
}
