package com.fresh.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
	private Integer user_no;
	private String user_id; //아이디
	private String user_pw;
	private String user_email;
	private String user_name;//유저 이름
	private String user_username;//닉네임
	private LocalDateTime user_date;
	private String ROLE; // 권한
}

