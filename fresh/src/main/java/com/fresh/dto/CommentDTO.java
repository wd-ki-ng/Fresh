package com.fresh.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CommentDTO {
	
	private Long com_no; // コメント番号
	private Long user_no; // ユーザー番号
	private Long board_no; // ポスト番号
	private Date com_cdate; // 作成日
	private Date com_mdate; // 修正日
	private String com_comment; // コメントの内容
	private int com_del; // 削除可否 ( 1:未削除, 0:削除 )
	private Long com_like;
}
