package com.fresh.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.fresh.dto.BoardDTO;
import com.fresh.dto.CustomCommentDTO;
import com.fresh.dto.UserDTO;
import com.fresh.service.AdminService;
import com.fresh.service.UserService;
import com.fresh.util.UserUtil;

@Controller
public class AdminContoller {

	@Autowired
	private UserService userService;
	
	@Autowired
	private AdminService adminService;

	@Autowired
	private UserUtil userUtil;

	@GetMapping({"/admin","/adminMem"})
	public String adminMem(Model model) {
		UserDTO user = userUtil.getUserNameAndRole();
		model.addAttribute("user", user);
		if (!user.getROLE().equals("ROLE_ADMIN")) {
			return "main";
		}
		
		// 회원 정보 가져오기
		List<UserDTO> members = adminService.getMemList();
		model.addAttribute("members", members);
		
		// 게시글 정보 가져오기
		List<BoardDTO> posts = adminService.getAllPosts();
		model.addAttribute("posts", posts);
		
		// 댓글 정보 가져오기
		List<CustomCommentDTO> comments = adminService.getAllComments();
		model.addAttribute("comments", comments);
		
		// 공지사항 정보 가져오기
		List<BoardDTO> notices = adminService.getAllNotices();
		model.addAttribute("notices", notices);
		
		return "admin";
	}
}
