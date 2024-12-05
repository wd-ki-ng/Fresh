package com.fresh.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CustomCommentDTO {
	
	private Long com_no; // 댓글 번호
	private Long user_no; // 작성자 번호
	private Long board_no; // 게시글 번호
	private Date com_cdate; // 작성일
	private Date com_mdate; // 수정일
	private Long com_upper; // 상위 댓글 번호
	private String com_comment; // 댓글 내용
	private int com_del; // 삭제 여부 ( 1:미삭제, 0:삭제 )
	
	private String user_username;		//유저 닉네임
}
