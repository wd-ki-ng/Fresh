package com.fresh.dto;

import java.util.Date;

import lombok.Data;

@Data
public class UserDTO {
	
	private Long user_no;				//ユーザー番号PK
	private String user_id;			    //ユーザーID
	private String user_pw;				//ユーザーパスワード
	private String temp_pw;				//仮のパスワード
	private String pw_chk;				
	private String user_name;			//ユーザーの名前
	private String user_email;			//ユーザーのメール
	private String user_username;		//ユーザー名
	private Date user_date;				//ユーザーの登録日
	private String ROLE;				//権限
	
	
}

