package com.fresh.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LikesDTO {
	
	private Long user_no;				//　ユーザー番号PK
	private Long board_no; 				// ポスト番号
	private Long com_no; 				// コメント番号
	private Long reply_no;				// リコメント番号
	
	public LikesDTO(Long user_no, Long board_no) {
		this.user_no = user_no;
		this.board_no = board_no;
	}
	
	public LikesDTO(Long user_no, Long board_no, Long com_no, Long reply_no) {
		this.user_no = user_no;
		this.board_no = board_no;
		this.com_no = com_no;
		this.reply_no = reply_no;
	}
}

