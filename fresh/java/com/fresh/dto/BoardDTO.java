package com.fresh.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class BoardDTO {
	
	private int	board_no; // 글 번호
	private String board_title; // 제목
	private String board_content; // 내용
	private String board_write; // 내용
	private Date board_date; // 작성일
	private String board_count; // 내용
	private int board_del; // 삭제 여부 ( 1:미삭제, 2:삭제 )
	
}
