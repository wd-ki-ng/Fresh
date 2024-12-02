package com.fresh.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
	private Integer user_no;
	private String name;
	private String username;
	private String password;
	private String fir_yn;
	private Integer pw_err_cnt;
	private LocalDateTime rct_acc_dt;
	private String role; // 권한
}

