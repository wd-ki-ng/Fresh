package com.fresh.repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.fresh.dto.BoardDTO;
import com.fresh.dto.CommentDTO;
import com.fresh.dto.UserDTO;

@Repository
public class AdminRepository {
	
	@Autowired
	private SqlSession sqlSession;

	// --------------------------------------会員管理--------------------------------------
	public List<UserDTO> getMemList() {					// 全ての会員リスト
		return sqlSession.selectList("admin.mem_list");
	}
	
	public UserDTO getOneMem(Long user_no) {			// 特定の会員情報
		return sqlSession.selectOne("admin.getMem", user_no);
	}
	
	public List<BoardDTO> getMemPosts(Long user_no) {	// 特定の会員が書いたポストリスト
		return sqlSession.selectList("admin.memPosts", user_no);
	}
	
	public void setOneMem(UserDTO member) {				// 特定の会員情報の修正
		sqlSession.update("admin.memUpdate", member);
	}
	
	public void delOneMem(Long user_no) {				// 特定の会員脱退
		sqlSession.delete("admin.memDelete", user_no);
	}
	// --------------------------------------会員管理--------------------------------------
	
	// ----------------------------------------ポスト管理----------------------------------------
	public List<BoardDTO> getAllPosts() {				// 削除されていないポストリスト
		return sqlSession.selectList("admin.post_list");
	}
	
	public int boardUpdate(BoardDTO board) {			// 管理者のポスト修正
		return sqlSession.update("admin.boardUpdate", board);
	}
	 
	public BoardDTO findById(long no) {					// 修正しようとしているポストの情報を探す
		return sqlSession.selectOne("admin.findById", no);
	}
	
	public void setOneBoard_del(Long board_no) {		// 特定のポストの削除
		sqlSession.update("admin.postDel", board_no);
	}
	// ----------------------------------------ポスト管理----------------------------------------
	
	// ----------------------------------------コメント管理----------------------------------------
	public List<CommentDTO> getAllComments() {			// 全てのコメントリスト
		return sqlSession.selectList("admin.com_list");
	}
	
	public void setOneCom_del(Long com_no) {			// コメント削除
		sqlSession.update("admin.comDel", com_no);
	}
	// ----------------------------------------コメント管理----------------------------------------

	// ----------------------------------------お知らせ管理----------------------------------------
	public List<BoardDTO> getAllNotices() {				// お知らせリスト
		return sqlSession.selectList("admin.noti_list");
	}
	
	public void setNotice(BoardDTO notice) {			// お知らせ作成
		sqlSession.insert("admin.noti_write", notice);
	}
	// ----------------------------------------お知らせ管理----------------------------------------

	// -----------------------------------------ゴミ箱管理-----------------------------------------
	public List<BoardDTO> getDelPosts() {				// 削除されたポストのリスト
		return sqlSession.selectList("admin.del_post");
	}
	
	public List<CommentDTO> getDelComs() {				// 削除されたコメントのリスト
		return sqlSession.selectList("admin.del_com");
	}
	
	public List<BoardDTO> getDelNotis() {				// 削除されたお知らせのリスト
		return sqlSession.selectList("admin.del_noti");
	}
	
	public void restorePost(Long board_no) {			// 削除されたポストの復旧
		sqlSession.update("admin.retorePost", board_no);
	}
	
	public void restoreComment(Long com_no) {			// 削除されたコメントの復旧
		sqlSession.update("admin.retoreCom", com_no);
	}
	
	public void eliminatePost(Long board_no) {			// 削除されたポストの永久削除
		sqlSession.delete("admin.eliminatePost", board_no);
	}
	
	public void eliminateComment(Long com_no) {			// 削除されたコメントの永久削除
		sqlSession.delete("admin.eliminateCom", com_no);
	}
	// -----------------------------------------ゴミ箱管理-----------------------------------------
}
