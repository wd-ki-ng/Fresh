package com.fresh.repository;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.fresh.dto.UserDTO;

@Repository
public class MypageRepository {
	
	@Autowired
	private SqlSession sqlSession;
	
	//パスワードの変更
	public void changePw(UserDTO user) {
		sqlSession.update("myPage.changePw", user);
	}

}
