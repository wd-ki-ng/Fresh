package com.fresh.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fresh.dto.BoardDTO;
import com.fresh.dto.CommentDTO;
import com.fresh.dto.CustomCommentDTO;
import com.fresh.dto.LikesDTO;
import com.fresh.dto.UserDTO;
import com.fresh.service.BoardService;
import com.fresh.util.UserUtil;

@Controller
public class BoardController {

	@Autowired
	private BoardService boardService;

	@Autowired
	private UserUtil userUtil;

	@GetMapping("/boardview")
	public String boardview(Model model, @RequestParam(value = "no") Long no) {
		UserDTO user = userUtil.getUserData();
		// ログインしていない人には接近不可能
		// 'or' 演算をする時、前のバリューがtrueなら後ろの演算はしないので順番に気を付けて指定すること
		// == null を後ろにするとuserのバリューがnullの場合、nullpointエラーが発生するのでnullのチェックを優先すること
		if (user == null || user.getROLE().equals("ROLE_ANONYMOUS")) {
			return "redirect:/login";
		}

		// 該当ポストのビューを増やす
		boardService.setView(no);
		// いいねボタンを押したのか押してないのかを確認
		LikesDTO likesDTO = new LikesDTO(user.getUser_no(), no);
		model.addAttribute("isLikedPost", boardService.isLikedPost(likesDTO));
		
		// 該当ポストの情報をもらう
		BoardDTO detail = boardService.getDetail(no);

		// ----- 以前のポストと次のポストの情報をもらう------------
		long prevBoard = boardService.prevBoard(no); // 以前のポスト
		long nextBoard = boardService.NextBoard(no); // 次のポスト
		// ----------------------------

		// 該当ポストのコメントの数
		int com_count = boardService.getCommentCount(no);

		// 該当ポストのコメントが存在する場合、コメントのリストをもらう
		List<CommentDTO> comments = boardService.getComments(no);
		model.addAttribute("comments", comments);
		model.addAttribute("com_count", com_count);
		// ポストの情報をもらった場合
		model.addAttribute("detail", detail);
		model.addAttribute("user", user);
		// -----------------------
		model.addAttribute("prevBoard", prevBoard); // 以前のポストの情報を追加
		model.addAttribute("nextBoard", nextBoard); // 次のポストの情報を追加
		// ----------------------

		if (prevBoard == -1L) {
			model.addAttribute("prevBoard", null); // 以前のポストの情報がない場合、null処理
		} else {
			model.addAttribute("prevBoard", prevBoard); // 以前のポストのID追加
		}

		if (nextBoard == -1L) {
			model.addAttribute("nextBoard", null); // 次のポストの情報がない場合、null処理
		} else {
			model.addAttribute("nextBoard", nextBoard); // 次のポストのID追加
		}
		return "boardview";
	}

	// ポストの作成ページへ
	@GetMapping("/boardWrite")
	public String boardWrite(Model model) {
		// ログインの情報をもらってログインをしていない場合はログインページに移動
		UserDTO user = userUtil.getUserData();
		model.addAttribute("user", userUtil.getUserNameAndRole());
		if (user == null || user.getROLE().equals("ROLE_ANONYMOUS")) {
			return "redirect:/login";
		}

		// ログインしている場合作成ページへ
		return "boardWrite";
	}

	@GetMapping("/submitPost")
	public String submitPost() {
		return "boardWrite";
	}

	//作成して提出
	@PostMapping("/submitPost")
	public String submitPost(Model model, BoardDTO board) {
		// ログインの情報をもらってログインをしていない場合はログインページに移動
		UserDTO user = userUtil.getUserData();
		model.addAttribute("user", userUtil.getUserNameAndRole());
		if (user == null || user.getROLE().equals("ROLE_ANONYMOUS")) {
			return "redirect:/login";
		}

		// データベースに内容をセーブ
		boardService.setBoard(board, user.getUser_no(), user.getUser_username());

		// リストページへ
		return "redirect:/board";
	}

	// ------------ 修正ボタンを押す場合、修正ページへ--------------
	@GetMapping("/boardUpdate")
	public String boardUpdate(Model model, @RequestParam(value = "no") Long no) {
		// ログインの情報をもらってログインをしていない場合はログインページに移動
		UserDTO user = userUtil.getUserData();
		model.addAttribute("user", userUtil.getUserNameAndRole());
		if (user == null || user.getROLE().equals("ROLE_ANONYMOUS")) {
			return "redirect:/login";
		}
		BoardDTO boardUpdate = boardService.findById(no);
		model.addAttribute("boardUpdate", boardUpdate);
		// 修正ページへ
		return "boardUpdate";
	}

	// 修正ボタンを押す場合、データベースに内容をセーブ
	@PostMapping("/boardUpdate")
	public String boardUpdate(BoardDTO board, Model model) {
		// ログインの情報をもらってログインをしていない場合はログインページに移動
		UserDTO user = userUtil.getUserData();
		model.addAttribute("user", userUtil.getUserNameAndRole());
		if (user == null || user.getROLE().equals("ROLE_ANONYMOUS")) {
			return "redirect:/login";
		}
		// Updateを要請
		boardService.boardUpdate(board);
			// FindByIDで修正された内容を照会
			return "redirect:/boardview?no="+board.getBoard_no();
		}
		//-------------コメント修正---------------------------	
		//修正ボタンを押す場合、データベースに内容をセーブ
		@PostMapping("/comUpdate")
		public String comUpdate(CommentDTO com , Model model) {
			// ログインの情報をもらってログインをしていない場合はログインページに移動
			UserDTO user = userUtil.getUserData();
			model.addAttribute("user", userUtil.getUserNameAndRole());
			if (user == null || user.getROLE().equals("ROLE_ANONYMOUS")) {
				return "redirect:/login";
			}
			//Updateを要請
		boardService.comUpdate(com);
			// FindByIDで修正された内容を照会
			return "redirect:/boardview?no="+com.getBoard_no();
		}		
		
	@PostMapping("/deleteBoard")
	@ResponseBody
	public String deleteBoard(@RequestParam("no") Long no, Model model) {
		UserDTO user = userUtil.getUserData();
		model.addAttribute("user", userUtil.getUserNameAndRole());
		if (user != null || !user.getROLE().equals("ROLE_ANONYMOUS")) {
			boardService.boardDel(no);
			return "true";
		} else {
			return "false";
		}
	}

	// コメント作成ボタン
	@PostMapping("/comWrite")
	public String comWrite(Model model, CommentDTO comment) {
		// ログインの情報をもらってログインをしていない場合はログインページに移動
		UserDTO user = userUtil.getUserData();
		model.addAttribute("user", userUtil.getUserNameAndRole());
		if (user == null || user.getROLE().equals("ROLE_ANONYMOUS")) {
			return "redirect:/login";
		}

		// データベースに内容をセーブ
		boardService.setComment(comment, user.getUser_no());

		// ポストの詳細ページへ
		return "redirect:/boardview?no=" + comment.getBoard_no();
	}

	// ポストのリストページ
	@GetMapping("/board")
	public String boardList(Model model) {
		// 現在、ユーザーの情報をもらう.ログインしていない場合、役割が"ROLE_ANONYMOUS"や userのバリューがnullになる
		UserDTO user = userUtil.getUserData();
		model.addAttribute("user", userUtil.getUserNameAndRole());

		// ポストをもらってmodelに追加
		List<BoardDTO> BoardList = boardService.getBoardList();
		model.addAttribute("BoardList", BoardList);

		return "board";
	}
	
	// ポストのいいねボタン
	@GetMapping("/postLike")
	public String postLike(Model model, @RequestParam(value = "no") Long no, @RequestParam(value = "like") int like) {
		// ログインの情報をもらってログインをしていない場合はログインページに移動
		UserDTO user = userUtil.getUserData();
		model.addAttribute("user", userUtil.getUserNameAndRole());
		if (user == null || user.getROLE().equals("ROLE_ANONYMOUS")) {
			return "redirect:/login";
		}
		
		if (like > 0) {
			// Like取り消し
			boardService.canceledLike(user.getUser_no(), no, null, null);
		} else {
			// Likeテーブルにデータ入力
			boardService.clickedLike(user.getUser_no(), no, null, null);
		}
		
		return "redirect:/boardview?no=" + no;
	}
}
