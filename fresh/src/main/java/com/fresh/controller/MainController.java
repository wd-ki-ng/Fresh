package com.fresh.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.fresh.dto.BoardDTO;
import com.fresh.service.BoardService;
import com.fresh.util.UserUtil;

@Controller
public class MainController {
	
	@Autowired
	private BoardService boardService;
	
	@Autowired
	UserUtil userUtil;
	
	@GetMapping("/index")
	public String index(Model model) {
		model.addAttribute("user", userUtil.getUserNameAndRole());
		return "index";
	}
	
	@GetMapping({"/","/main"})
	public String main(Model model) {
		model.addAttribute("user", userUtil.getUserNameAndRole());
		
		// 인기글 10개 가져와서 model에 추가
		List<BoardDTO> MainHotPosts = boardService.getMainHotPost();
		model.addAttribute("hot_boards", MainHotPosts);
		
		// 최신글 10개 가져와서 model에 추가
		List<BoardDTO> MainNewPosts = boardService.getMainNewPost();
		model.addAttribute("new_boards", MainNewPosts);
		
		return "main";
	}
}
