package com.fresh.repository;

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

}
