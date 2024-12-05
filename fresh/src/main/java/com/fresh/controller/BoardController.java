package com.fresh.controller;
 
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fresh.dto.BoardDTO;
import com.fresh.dto.CustomCommentDTO;
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
		// 로그인 안한 사람은 못보게 막음
		// 'or' 연산 시 앞이 true면 뒤의 연산은 하지 않기 때문에 순서도 잘 지정해야 함
		// == null 을 뒤로 밀면 user가 null 일 경우 nullpoint 에러 발생하여 null 체크를 우선으로 진행
		if (user == null || user.getROLE().equals("ROLE_ANONYMOUS")) {
			return "redirect:/login";
		}
		
		// 해당 글 정보를 가져옴
		BoardDTO detail = boardService.getDetail(no);
		
		// 만약 detail이 null이면, 존재하지 않는 글이거나 삭제된 게시글(board_del은 mapper에서 검사해서 따로 체크하지 않아도 됨)
		/*
		if(detail == null) {
			model.addAttribute("isExist", 0);
			return "detail";
		}
		*/
		// model.addAttribute("isExist", 1);
		
		// 해당 게시글의 댓글 수
		int com_count = boardService.getCommentCount(no);
		
		// 해당 게시글의 댓글이 1개라도 있다면, 댓글 리스트 가져오기
		List<CustomCommentDTO> comments = boardService.getComments(no);
		model.addAttribute("comments", comments);
		model.addAttribute("com_count", com_count);
		// 게시글을 성공적으로 가져온 경우
		model.addAttribute("detail", detail);
		model.addAttribute("user", user);

		return "boardview";		
		
		
		/*
		// 로그인 한 사람이 학생이 아니면 => 관리자, 교직원임
		// or 로그인 한 사람과 작성자가 같으면 통과
		if (!user.getROLE().equals("ROLE_USER")) {
			if ("1".equals(detail.getBoard_del())) {
				model.addAttribute("answer", boardService.getAnswerContent(detail.getBoard_del()));
			}
			model.addAttribute("detail", detail);
			model.addAttribute("user", user);
			return "detail";
		} else {
			return "redirect:/login";
		}
		*/
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
		
		// 로그인한 상태라면　화면 이동
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
	
	@GetMapping("/submitPost")
	public String submitPost(Model model) {
		return "boardWrite";
	}
	
	// 게시판 리스트 페이지
	@GetMapping({"/board"})
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
