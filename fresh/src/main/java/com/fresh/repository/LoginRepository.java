package com.fresh.repository;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.fresh.dto.UserDTO;

@Repository
public class LoginRepository {
	
	@Autowired
	private SqlSession sqlSession;

	public UserDTO list() {
		return sqlSession.selectOne("test.test");
	}
	
	public UserDTO findByUsername(String username) {
		return sqlSession.selectOne("test.findByUsername", username);
	}

	public void joinProcess(UserDTO user) {
		sqlSession.insert("test.join", user);
	}

	public UserDTO getUserData(UserDTO user) {
		return sqlSession.selectOne("test.getUserData", user);
	}
}
