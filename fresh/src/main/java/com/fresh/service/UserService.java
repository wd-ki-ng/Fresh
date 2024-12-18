package com.fresh.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.fresh.dto.UserDTO;
import com.fresh.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	//パスワードをハッシュ化
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	

	
	public UserDTO list() {
		return userRepository.list();
	}
	
	//会員登録をする時、入力された情報をデータベースに追加し
	//パスワードはハッシュ化させてデータベースに追加
	public void joinProcess(UserDTO user) {
		user.setUser_pw(bCryptPasswordEncoder.encode(user.getUser_pw()));
		userRepository.joinProcess(user);
	}
	
	public UserDTO findByUserId(String user_id) {
		return userRepository.findByUserId(user_id);
	}
	
	public UserDTO findByUserUserName(String user_username) {
		return userRepository.findByUserUserName(user_username);
	}
	
	//IDの重複確認
	public String checkUserId(String user_id) {
		return userRepository.checkUserId(user_id);
	}
	
	//ユーザー名の重複確認
	public String checkUserUserName(String user_username) {
		return userRepository.checkUserUserName(user_username);
	}
	
	//IDを探す
    public String findIdByNameAndEmail(UserDTO user) {
    	return userRepository.findIdByNameAndEmail(user);
    }
    
    //IDとメールで会員の存否を確認
    public UserDTO findByIdAndEmail(UserDTO user) {
    	return userRepository.findByIdAndEmail(user);
    }
    
    //仮のパスワードの発給
    public void createTempPw(UserDTO user) {
    	String temppw = bCryptPasswordEncoder.encode(user.getTemp_pw());
    	user.setTemp_pw(temppw);
    	userRepository.createTempPw(user);
    }

	
}
