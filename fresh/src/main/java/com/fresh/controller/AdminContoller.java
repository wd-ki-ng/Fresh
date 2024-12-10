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

	@GetMapping("/admin/admin")
	public String admin(Model model) {
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
		
		// 삭제한 게시글 가져오기
		List<BoardDTO> del_posts = adminService.getDelPosts();
		model.addAttribute("del_posts", del_posts);
		
		// 삭제한 댓글 가져오기
		List<CustomCommentDTO> del_coms = adminService.getDelComs();
		model.addAttribute("del_coms", del_coms);
		
		// 삭제한 공지 가져오기
		List<BoardDTO> del_notis = adminService.getDelNotis();
		model.addAttribute("del_notis", del_notis);
		
		return "admin/admin";
	}
	
	@GetMapping("/admin/adminMem")
	public String adminMem(Model model, @RequestParam(value = "user_no") Long user_no) {
		UserDTO user = userUtil.getUserNameAndRole();
		model.addAttribute("user", user);
		if (!user.getROLE().equals("ROLE_ADMIN")) {
			return "main";
		}
		
		// 조회하려는 유저의 모든 정보 가져오기
		UserDTO member = adminService.getOneMem(user_no);
		model.addAttribute("member", member);
		
		return "admin/adminMem";
	}
}
