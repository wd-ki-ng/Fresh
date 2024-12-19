package com.fresh.service;
 
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fresh.dto.BoardDTO;
import com.fresh.dto.CommentDTO;
import com.fresh.dto.CustomCommentDTO;
import com.fresh.dto.LikesDTO;
import com.fresh.dto.UserDTO;
import com.fresh.repository.BoardRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardService {
	
	@Autowired
	private BoardRepository boardRepository;
	
	// ポストの詳細を見る
	public BoardDTO getDetail(Long no) {
		return boardRepository.getDetail(no);
	}
	
	//---以前のポスト/ 次のポスト----------
	public long prevBoard(Long no) {
		return boardRepository.prevBoard(no);
	}
	
	public long NextBoard(Long no) {
		return boardRepository.NextBoard(no);
	}
	//----------------------------------
	
	// ビューを増やす
	public int setView(Long no) {
		return boardRepository.setView(no);
	}
	
	public int isLikedPost(LikesDTO likesDTO) {
		return boardRepository.isLikedPost(likesDTO);
	}
	
	public void clickedLike(Long user_no, Long board_no, Long com_no, Long reply_no) {
		LikesDTO likesDTO = new LikesDTO(user_no, board_no, com_no, reply_no);
		boardRepository.clickedLike(likesDTO);
		boardRepository.plusBoardLike(likesDTO);
	}
	
	public void canceledLike(Long user_no, Long board_no, Long com_no, Long reply_no) {
		LikesDTO likesDTO = new LikesDTO(user_no, board_no, com_no, reply_no);
		boardRepository.canceledBoardLike(likesDTO);
		boardRepository.minusBoardLike(likesDTO);
	}
	
	// ポスト作成
	public int setBoard(BoardDTO board, Long no, String name) {
		board.setUser_no(no);
		board.setBoard_write(name);
		return boardRepository.setBoard(board);
	}
	
	//ポスト修正------------------------------------------------------
	public int boardUpdate(BoardDTO board) {
		return boardRepository.boardUpdate(board);
		
	}

	public BoardDTO findById(Long no) {
	    return boardRepository.findById(no);
	}
	//---------------コメント修正------------------
	public int comUpdate(CommentDTO com) {
	    return boardRepository.comUpdate(com);
	}
	
	public void comDelete(Long com_no) {
		boardRepository.comDelete(com_no);
	}
	
	public long getBoard_no(Long com_no) {
		return boardRepository.getBoard_no(com_no);
	}

	public int boardDel(Long no) {
		return boardRepository.boardDel(no);
	}
	
	
	// コメントのリストをもらう
	public int getCommentCount(Long no) {
		return boardRepository.getCommentCount(no);
	}
	
	// コメントの情報をもらう
	public List<CommentDTO> getComments(Long no) {
		return boardRepository.getComments(no);
	}
	
	// コメント作成
	public int setComment(CommentDTO comment, Long user_no) {
		comment.setUser_no(user_no);
		return boardRepository.setComment(comment);
	}
	
	// 統合の人気ポスト : ♥ + ビューをプラスした'反応数'の列を作り,反応数が高い順に並べる
	public List<BoardDTO> getMainHotPost() {
		return boardRepository.getMainHotPost();
	}
	
	// 統合の最新ポスト: 作成日順に整列して一番最新ポストを5個
	public List<BoardDTO> getMainNewPost() {
		return boardRepository.getMainNewPost();
	}
	
	// メインページのお知らせ : 作成日順に整列して一番最新のポストを5個
	public List<BoardDTO> getMainNotice() {
		return boardRepository.getMainNotice();
	}
	
	// 掲示板リスト 
	public List<BoardDTO> getBoardList() {
		return boardRepository.getBoardList();
	}
	
	// 入力したキーワードに会うポストのリストをもらう
	public List<BoardDTO> getSearchBoard(String keyword) {
		keyword = "%" + keyword + "%";
		return boardRepository.getSearchBoard(keyword);
	}

}


