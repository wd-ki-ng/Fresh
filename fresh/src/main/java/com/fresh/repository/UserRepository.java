package com.fresh.repository;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.fresh.dto.UserDTO;

@Repository
public class UserRepository {
	
	@Autowired
	private SqlSession sqlSession;

	public UserDTO list() {
		return sqlSession.selectOne("user.test");
	}
	
	public UserDTO findByUserId(String user_id) {
		return sqlSession.selectOne("user.findByUserId", user_id);
	}

	public void joinProcess(UserDTO user) {
		sqlSession.insert("user.joinProcess", user);
	}

	public UserDTO getUserData(UserDTO user) {
		return sqlSession.selectOne("user.getUserData", user);
	}
	
	public UserDTO findByUserUserName(String user_username) {
		return sqlSession.selectOne("user.findByUserUserName", user_username);
	}
	
	//임시 비밀번호 발급
	public void createTempPw(UserDTO user) {
		sqlSession.update("user.createTempPw", user);
	}
	
	//아이디 중복체크
	public String checkUserId(String user_id) {
		return sqlSession.selectOne("user.checkUserId", user_id);
	}
	
	//닉네임 중복체크
	public String checkUserUserName(String user_username) {
		return sqlSession.selectOne("user.checkUserUserName", user_username);
	}
	
	 //아이디 찾기
	public String findIdByNameAndEmail(UserDTO user) {
		return sqlSession.selectOne("user.findIdByNameAndEmail", user);
	}
	
	//아이디와 이메일로 회원이 존재하는지 확인
	public UserDTO findByIdAndEmail(UserDTO user) {
		return sqlSession.selectOne("user.findByIdAndEmail", user);
	}
}
