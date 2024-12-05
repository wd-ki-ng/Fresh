package com.fresh.dto;

import java.util.Date;

import lombok.Data;

@Data
public class UserDTO {
	
	private Long user_no;				//유저 번호PK
	private String user_id;				//유저 아이디
	private String user_pw;				//유저 비밀번호
	private String pw_chk;
	private String user_name;			//유저 이름
	private String user_email;			//유저 이메일
	private String user_username;		//유저 닉네임
	private Date user_date;				//유저 생성일자
	private String ROLE;				//권한
	
	
}

