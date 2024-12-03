package com.fresh.dto;

import java.time.LocalDateTime;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
	
	private Long user_no;
	private String user_id;
	private String user_pw;
	private String user_name;
	private String user_email;
	private String user_username;
	private Date user_date;
	private String ROLE; // 권한
}

