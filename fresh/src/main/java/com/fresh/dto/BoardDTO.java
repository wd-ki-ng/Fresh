package com.fresh.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class BoardDTO {
	
	private Long board_no; // 글 번호
	private Long user_no;
	private String board_title; // 제목
	private String board_content; // 내용
	private String board_write; // 내용
	private Date board_date; // 작성일
	private Long board_like; // 좋아요
	private int board_count; // 조횟수
	private int board_del; // 삭제 여부 ( 1:미삭제, 0:삭제 )
	
}
