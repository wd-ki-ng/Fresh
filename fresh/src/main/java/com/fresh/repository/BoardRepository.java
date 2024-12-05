package com.fresh.repository;
 
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.fresh.dto.BoardDTO;
import com.fresh.dto.CommentDTO;

@Repository
public class BoardRepository {

	@Autowired
	private SqlSession sqlSession;
	
	public BoardDTO getDetail(int no) {
		return sqlSession.selectOne("board.detail", no);
	}
	
	public int setBoard(BoardDTO board) {
		return sqlSession.insert("board.write", board);
	}
	
	public int getCommentCount(int no) {
		return sqlSession.selectOne("board.com_cnt", no);
	}
	
	public List<CommentDTO> getComments(int no) {
		return sqlSession.selectList("board.com_list");
	}
	
	public List<BoardDTO> getMainHotPost() {
		return sqlSession.selectList("board.main_hot");
	}
	
	public List<BoardDTO> getMainNewPost() {
		return sqlSession.selectList("board.main_new");
	}
	
	public List<BoardDTO> getBoardList() {
		return sqlSession.selectList("board.boardList");
	}
}
