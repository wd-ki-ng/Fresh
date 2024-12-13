package com.fresh.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fresh.dto.BoardDTO;
import com.fresh.dto.CommentDTO;
import com.fresh.dto.CustomCommentDTO;
import com.fresh.dto.UserDTO;
import com.fresh.service.BoardService;
import com.fresh.util.UserUtil;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class BoardController {

	@Autowired
	private BoardService boardService;

	@Autowired
	private UserUtil userUtil;

	@GetMapping("/boardview")
	public String boardview(Model model, @RequestParam(value = "no") Long no) {
		UserDTO user = userUtil.getUserData();
		// 로그인 안한 사람은 못보게 막음
		// 'or' 연산 시 앞이 true면 뒤의 연산은 하지 않기 때문에 순서도 잘 지정해야 함
		// == null 을 뒤로 밀면 user가 null 일 경우 nullpoint 에러 발생하여 null 체크를 우선으로 진행
		if (user == null || user.getROLE().equals("ROLE_ANONYMOUS")) {
			return "redirect:/login";
		}

		// 해당 글의 조회수를 늘림
		boardService.setView(no);

		// 해당 글 정보를 가져옴
		BoardDTO detail = boardService.getDetail(no);

		// ----- 이전 글과 다음 글 정보를 가져옴------------
		long prevBoard = boardService.prevBoard(no); // 이전 글
		long nextBoard = boardService.NextBoard(no); // 다음 글

		// ----------------------------

		// 해당 게시글의 댓글 수
		int com_count = boardService.getCommentCount(no);

		// 해당 게시글의 댓글이 1개라도 있다면, 댓글 리스트 가져오기
		List<CustomCommentDTO> comments = boardService.getComments(no);
		model.addAttribute("comments", comments);
		model.addAttribute("com_count", com_count);
		// 게시글을 성공적으로 가져온 경우
		model.addAttribute("detail", detail);
		model.addAttribute("user", user);
		// -----------------------
		model.addAttribute("prevBoard", prevBoard); // 이전 글 정보 추가
		model.addAttribute("nextBoard", nextBoard); // 다음 글 정보 추가
		// ----------------------

		if (prevBoard == -1L) {
			model.addAttribute("prevBoard", null); // 이전 글이 없다면 null 처리
		} else {
			model.addAttribute("prevBoard", prevBoard); // 이전 글 ID 추가
		}

		if (nextBoard == -1L) {
			model.addAttribute("nextBoard", null); // 다음 글이 없다면 null 처리
		} else {
			model.addAttribute("nextBoard", nextBoard); // 다음 글 ID 추가
		}
		return "boardview";
	}

	// 글쓰기 페이지로 단순 이동
	@GetMapping("/boardWrite")
	public String boardWrite(Model model) {
		// 로그인 정보를 가져와서 로그인을 하지 않은 상태면 로그인 화면으로 보냄
		UserDTO user = userUtil.getUserData();
		model.addAttribute("user", userUtil.getUserNameAndRole());
		if (user == null || user.getROLE().equals("ROLE_ANONYMOUS")) {
			return "redirect:/login";
		}

		// 로그인한 상태라면 화면 이동
		return "boardWrite";
	}

	@GetMapping("/submitPost")
	public String submitPost() {
		return "boardWrite";
	}

	// 글쓰고 제출
	@PostMapping("/submitPost")
	public String submitPost(Model model, BoardDTO board) {
		// 로그인 정보를 가져와서 로그인을 하지 않은 상태면 로그인 화면으로 보냄
		UserDTO user = userUtil.getUserData();
		model.addAttribute("user", userUtil.getUserNameAndRole());
		if (user == null || user.getROLE().equals("ROLE_ANONYMOUS")) {
			return "redirect:/login";
		}

		// 데이터 베이스에 내용 입력
		boardService.setBoard(board, user.getUser_no(), user.getUser_username());

		// 게시글 목록으로 돌아감
		return "redirect:/board";
	}

	// ------------ 게시글 수정 버튼 클릭시 수정 화면으로 넘어감--------------
	@GetMapping("/boardUpdate")
	public String boardUpdate(Model model, @RequestParam(value = "no") Long no) {
		// 로그인 정보를 가져와서 로그인을 하지 않은 상태면 로그인 화면으로 보냄
		UserDTO user = userUtil.getUserData();
		model.addAttribute("user", userUtil.getUserNameAndRole());
		if (user == null || user.getROLE().equals("ROLE_ANONYMOUS")) {
			return "redirect:/login";
		}
		BoardDTO boardUpdate = boardService.findById(no);
		model.addAttribute("boardUpdate", boardUpdate);
		// 수정페이지
		return "boardUpdate";
	}

	// 수정버튼 누르면 db에 저장
	@PostMapping("/boardUpdate")
	public String boardUpdate(BoardDTO board, Model model) {
		// 로그인 정보를 가져와서 로그인을 하지 않은 상태면 로그인 화면으로 보냄
		UserDTO user = userUtil.getUserData();
		model.addAttribute("user", userUtil.getUserNameAndRole());
		if (user == null || user.getROLE().equals("ROLE_ANONYMOUS")) {
			return "redirect:/login";
		}
		// Update 요청
		boardService.boardUpdate(board);
			// FindByID로 수정된 내용 다시 조회
			return "redirect:/boardview?no="+board.getBoard_no();
		}
		//-------------댓글 수정---------------------------	
		//수정버튼 누르면 db에 저장
		@PostMapping("/comUpdate")
		public String comUpdate(CommentDTO com , Model model) {
			// 로그인 정보를 가져와서 로그인을 하지 않은 상태면 로그인 화면으로 보냄
			UserDTO user = userUtil.getUserData();
			model.addAttribute("user", userUtil.getUserNameAndRole());
			if (user == null || user.getROLE().equals("ROLE_ANONYMOUS")) {
				return "redirect:/login";
			}
			//Update 요청
		boardService.comUpdate(com);
			// FindByID로 수정된 내용 다시 조회
			return "redirect:/boardview?no="+com.getBoard_no();
		}		
		
		
	@GetMapping("/submitPost")
	public String submitPost(Model model) {
		return "boardWrite";
	}

	// 수정버튼 누르면 db에 저장
	@PostMapping("/boardUpdate")
	public String boardUpdate(BoardDTO board, Model model) {
		// 로그인 정보를 가져와서 로그인을 하지 않은 상태면 로그인 화면으로 보냄
		UserDTO user = userUtil.getUserData();
		model.addAttribute("user", userUtil.getUserNameAndRole());
		if (user == null || user.getROLE().equals("ROLE_ANONYMOUS")) {
			return "redirect:/login";
		}
		// Update 요청
		boardService.boardUpdate(board);
		// FindByID로 수정된 내용 다시 조회
		return "redirect:/boardview?no=" + board.getBoard_no();
	}

	// -------------댓글 수정---------------------------
	// 수정버튼 누르면 db에 저장
	@PostMapping("/comUpdate")
	public String comUpdate(CommentDTO com, Model model) {
		// 로그인 정보를 가져와서 로그인을 하지 않은 상태면 로그인 화면으로 보냄
		UserDTO user = userUtil.getUserData();
		model.addAttribute("user", userUtil.getUserNameAndRole());
		if (user == null || user.getROLE().equals("ROLE_ANONYMOUS")) {
			return "redirect:/login";
		}
		// Update 요청
		boardService.comUpdate(com);
		// FindByID로 수정된 내용 다시 조회
		return "redirect:/boardview?no=" + com.getBoard_no();
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

	// 댓글 작성 버튼
	@PostMapping("/comWrite")
	public String comWrite(Model model, CommentDTO comment) {
		// 로그인 정보를 가져와서 로그인을 하지 않은 상태면 로그인 화면으로 보냄
		UserDTO user = userUtil.getUserData();
		model.addAttribute("user", userUtil.getUserNameAndRole());
		if (user == null || user.getROLE().equals("ROLE_ANONYMOUS")) {
			return "redirect:/login";
		}

		// 데이터 베이스에 내용 입력
		boardService.setComment(comment, user.getUser_no());

		// 게시글 상세 페이지로 돌아감
		return "redirect:/boardview?no=" + comment.getBoard_no();
	}

	// 게시판 리스트 페이지
	@GetMapping("/board")
	public String boardList(Model model) {
		// 현재 유저 받아옴. 로그인 안 한 경우엔 역할이 "ROLE_ANONYMOUS"거나, user 자체가 null이 됨
		UserDTO user = userUtil.getUserData();
		model.addAttribute("user", userUtil.getUserNameAndRole());

		// 게시글 가져와서 model에 추가
		List<BoardDTO> BoardList = boardService.getBoardList();
		model.addAttribute("BoardList", BoardList);

		return "board";
	}

}
