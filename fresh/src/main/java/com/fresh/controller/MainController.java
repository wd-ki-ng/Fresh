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
		// 現在ユーザーの情報をもらう.ログインしていない場合、"ROLE_ANONYMOUS"や, userのバリューがnullになる
		UserDTO user = userUtil.getUserData();
		model.addAttribute("idandname",user);
		model.addAttribute("user", userUtil.getUserNameAndRole());
		
		// 人気ポストを10個もらってmodelに追加
		List<BoardDTO> MainHotPosts = boardService.getMainHotPost();
		model.addAttribute("MainHotPosts", MainHotPosts);
		
		// 最新ポストを5個もらってmodelに追加
		List<BoardDTO> MainNewPosts = boardService.getMainNewPost();
		model.addAttribute("MainNewPosts", MainNewPosts);
		
		// お知らせを5個もらってmodelに追加
		List<BoardDTO> MainNotice = boardService.getMainNotice();
		model.addAttribute("MainNotice", MainNotice);

		return "main";
	}
	
	@GetMapping("/search")
	public String search(Model model, @RequestParam(value = "keyword") String keyword) {
		// 現在ユーザーの情報をもらう.ログインしていない場合、"ROLE_ANONYMOUS"や, userのバリューがnullになる
		UserDTO user = userUtil.getUserData();
		model.addAttribute("idandname",user);
		model.addAttribute("user", userUtil.getUserNameAndRole());
		
		// 検索語を適用された掲示板リストをもらって伝達する
		List<BoardDTO> BoardList = boardService.getSearchBoard(keyword);
		model.addAttribute("BoardList", BoardList);

		return "board";
	}
}
