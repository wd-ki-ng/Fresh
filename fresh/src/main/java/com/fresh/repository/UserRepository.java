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

}
