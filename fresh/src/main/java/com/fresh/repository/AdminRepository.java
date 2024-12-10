package com.fresh.repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.fresh.dto.BoardDTO;
import com.fresh.dto.CustomCommentDTO;
import com.fresh.dto.UserDTO;

@Repository
public class AdminRepository {
	
	@Autowired
	private SqlSession sqlSession;

	public List<UserDTO> getMemList() {
		return sqlSession.selectList("admin.mem_list");
	}
	
	public UserDTO getOneMem(Long user_no) {
		return sqlSession.selectOne("admin.getMem", user_no);
	}
	
	public List<BoardDTO> getMemPosts(Long user_no) {
		return sqlSession.selectList("admin.memPosts", user_no);
	}
	
	public void setOneMem(UserDTO member) {
		sqlSession.update("admin.memUpdate", member);
	}
	
	public void delOneMem(Long user_no) {
		sqlSession.delete("admin.memDelete", user_no);
	}
	
	public List<BoardDTO> getAllPosts() {
		return sqlSession.selectList("admin.post_list");
	}
	
	public void setOneBoard_del(Long board_no) {
		sqlSession.update("admin.postDel", board_no);
	}
	
	public List<CustomCommentDTO> getAllComments() {
		return sqlSession.selectList("admin.com_list");
	}

	public List<BoardDTO> getAllNotices() {
		return sqlSession.selectList("admin.noti_list");
	}

	public List<BoardDTO> getDelPosts() {
		return sqlSession.selectList("admin.del_post");
	}

	public List<CustomCommentDTO> getDelComs() {
		return sqlSession.selectList("admin.del_com");
	}

	public List<BoardDTO> getDelNotis() {
		return sqlSession.selectList("admin.del_noti");
	}
}