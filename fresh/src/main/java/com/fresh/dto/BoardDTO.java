package com.fresh.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class BoardDTO {
	
	private Long board_no; // ポスト番号
	private Long user_no;
	private int category;
	private String board_title; // タイトル
	private String board_content; // 内容
	private String board_write; // ユーザー名
	private Date board_date; // 作成日
	private Long board_like; // ♥
	private int board_count; // ビュー
	private int board_del; // 削除可否 ( 1:未削除, 0:削除 )
	
}
