package com.fresh.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fresh.dto.BoardDTO;
import com.fresh.dto.UserDTO;
import com.fresh.service.BoardService;
import com.fresh.util.UserUtil;


@Controller
public class MainController {
	
	@Autowired
	private BoardService boardService;
	
	@Autowired
	UserUtil userUtil;
	
	@GetMapping({"/","/main"})
	public String main(Model model) {
		// 현재 유저 받아옴. 로그인 안 한 경우엔 역할이 "ROLE_ANONYMOUS"거나, user 자체가 null이 됨
		UserDTO user = userUtil.getUserData();
		model.addAttribute("idandname",user);
		model.addAttribute("user", userUtil.getUserNameAndRole());
		
		// 인기글 10개 가져와서 model에 추가
		List<BoardDTO> MainHotPosts = boardService.getMainHotPost();
		model.addAttribute("MainHotPosts", MainHotPosts);
		
		// 최신글 5개 가져와서 model에 추가
		List<BoardDTO> MainNewPosts = boardService.getMainNewPost();
		model.addAttribute("MainNewPosts", MainNewPosts);
		
		// 공지사항 5개 가져와서 model에 추가
		List<BoardDTO> MainNotice = boardService.getMainNotice();
		model.addAttribute("MainNotice", MainNotice);

		return "main";
	}
	
	@GetMapping("/search")
	public String search(Model model, @RequestParam(value = "keyword") String keyword) {
		// 현재 유저 받아옴. 로그인 안 한 경우엔 역할이 "ROLE_ANONYMOUS"거나, user 자체가 null이 됨
		UserDTO user = userUtil.getUserData();
		model.addAttribute("idandname",user);
		model.addAttribute("user", userUtil.getUserNameAndRole());
		
		// 검색어 적용한 게시판 리스트 가져와서 넘기기
		List<BoardDTO> BoardList = boardService.getSearchBoard(keyword);
		model.addAttribute("BoardList", BoardList);

		return "board";
	}
}
