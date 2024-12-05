package com.fresh.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fresh.dto.BoardDTO;
import com.fresh.dto.UserDTO;
import com.fresh.service.BoardService;
import com.fresh.util.UserUtil;


@Controller
public class BoardController {
	
	@Autowired
	private BoardService boardService;
	
	@Autowired
	private UserUtil userUtil;
	
	@GetMapping("/detail")
	public String detail(Model model, @RequestParam(name = "no") int no) {
		UserDTO user = userUtil.getUserData();
		// 로그인 안한 사람은 못보게 막음
		// 'or' 연산 시 앞이 true면 뒤의 연산은 하지 않기 때문에 순서도 잘 지정해야 함
		// == null 을 뒤로 밀면 user가 null 일 경우 nullpoint 에러 발생하여 null 체크를 우선으로 진행
		if (user == null || user.getROLE().equals("ROLE_ANONYMOUS")) {
			return "redirect:/login";
		}
		
		// 해당 글 정보를 가져옴
		BoardDTO detail = boardService.getDetail(no);
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
	@GetMapping("/submitPost")
	public String submitPost(Model model, @RequestParam(name = "board") BoardDTO board) {
		// 로그인 정보를 가져와서 로그인을 하지 않은 상태면 로그인 화면으로 보냄
		UserDTO user = userUtil.getUserData();
		model.addAttribute("user", userUtil.getUserNameAndRole());
		if (user == null || user.getROLE().equals("ROLE_ANONYMOUS")) {
			return "redirect:/login";
		}
		// board 내용이 없으면 다시 boardWrite로 보냄
		
		// 로그인한 상태라면 입력받은 board에 현재 로그인한 회원의 번호와 닉네임을 입력함
		board.setUser_no(user.getUser_no());
		board.setBoard_write(user.getUser_username());
		// 데이터 베이스에 내용 입력
		boardService.setBoard(board);
		// 게시글 목록으로 돌아감
		return "redirect:/board";
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
