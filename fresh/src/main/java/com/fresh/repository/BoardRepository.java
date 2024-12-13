package com.fresh.repository;
 
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.fresh.dto.BoardDTO;
import com.fresh.dto.CommentDTO;
import com.fresh.dto.CustomCommentDTO;
import com.fresh.dto.UserDTO;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class BoardRepository {

	@Autowired
	private SqlSession sqlSession;
	
	public BoardDTO getDetail(Long no) {
		return sqlSession.selectOne("board.detail", no);
	}
	//以前のポスト
	public long prevBoard(Long no) {
		Long prevBoard = sqlSession.selectOne("board.prevBoard", no);
		return  prevBoard != null ? prevBoard : -1L; 
		
	}
	// 次のポスト 
	public long NextBoard(Long no) {
		 Long nextBoard = sqlSession.selectOne("board.nextBoard", no);
		 return nextBoard != null ? nextBoard : -1L; // 다음 게시글이 없으면 -1을 반환
	}
	
	public int setView(Long no) {
		return sqlSession.update("board.updateView", no);
	}
	
	public int setBoard(BoardDTO board) {
		return sqlSession.insert("board.write", board);
	}
	//------------------- 修正--------------------------
	public int boardUpdate(BoardDTO board) {
		return sqlSession.update("board.boardUpdate", board);
	}
	 
	public BoardDTO findById(long no) {
		return sqlSession.selectOne("board.findById", no);
	}
	
	//-------------------コメント修正-------------------
	public int comUpdate(CommentDTO com) {
		return sqlSession.update("board.comUpdate", com);
	}
	//----------------------------------------------
	
	public int getCommentCount(Long no) {
		return sqlSession.selectOne("board.com_cnt", no);
	}
	
	public List<CustomCommentDTO> getComments(Long no) {
		return sqlSession.selectList("board.com_list", no);
	}
	
	public int setComment(CommentDTO comment) {
		return sqlSession.insert("board.com_write", comment);
	}
	
	public List<BoardDTO> getMainHotPost() {
		return sqlSession.selectList("board.main_hot");
	}
	
	public List<BoardDTO> getMainNewPost() {
		return sqlSession.selectList("board.main_new");
	}
	
	public List<BoardDTO> getMainNotice() {
		return sqlSession.selectList("board.main_notice");
	}
	
	public List<BoardDTO> getBoardList() {
		return sqlSession.selectList("board.boardList");
	}

	public int boardDel(Long no) {
		return sqlSession.update("board.boardDel",no);
	}
	
	public List<BoardDTO> getSearchBoard(String keyword) {
		return sqlSession.selectList("board.searchBoard", keyword);
	}

}
