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
		
		// 유저가 작성한 게시글 가져오기
		List<BoardDTO> memPosts = adminService.getMemPosts(user_no);
		model.addAttribute("memPosts", memPosts);
		
		return "admin/adminMem";
	}
	
	@PostMapping("/admin/memUpdate")
	public String memUpdate(Model model, UserDTO member) {
		UserDTO user = userUtil.getUserNameAndRole();
		model.addAttribute("user", user);
		if (!user.getROLE().equals("ROLE_ADMIN")) {
			return "main";
		}
		
		// 수정사항 반영 - update
		adminService.setOneMem(member);
		
		return "redirect:/admin/adminMem?user_no="+member.getUser_no();
	}
	
	@GetMapping("/admin/memDel")
	public String memDel(Model model, @RequestParam(value = "user_no") Long user_no) {
		UserDTO user = userUtil.getUserNameAndRole();
		model.addAttribute("user", user);
		if (!user.getROLE().equals("ROLE_ADMIN")) {
			return "main";
		}
		
		// 유저 탈퇴처리
		adminService.delOneMem(user_no);
		
		return "redirect:/admin/admin";
	}
	
	@GetMapping("/admin/postDel")
	public String postDel(Model model, @RequestParam(value = "board_no") Long board_no) {
		UserDTO user = userUtil.getUserNameAndRole();
		model.addAttribute("user", user);
		if (!user.getROLE().equals("ROLE_ADMIN")) {
			return "main";
		}
		
		// 게시글 삭제 처리 - board_del을 0으로
		adminService.setOneBoard_del(board_no);
		
		return "redirect:/admin/admin";
	}
	
	@GetMapping("/admin/comDel")
	public String comDel(Model model, @RequestParam(value = "com_no") Long com_no) {
		UserDTO user = userUtil.getUserNameAndRole();
		model.addAttribute("user", user);
		if (!user.getROLE().equals("ROLE_ADMIN")) {
			return "main";
		}
		
		// 댓글 삭제 처리 - com_del을 0으로/submitNotice
		adminService.setOneCom_del(com_no);
		
		return "redirect:/admin/admin";
	}
	
	// 글쓰기 페이지로 단순 이동
	@GetMapping("/admin/noticeWrite")
	public String noticeWrite(Model model) {
		// 로그인 정보를 가져와서 로그인을 하지 않은 상태면 로그인 화면으로 보냄
		UserDTO user = userUtil.getUserData();
		model.addAttribute("user", userUtil.getUserNameAndRole());
		if (user == null || user.getROLE().equals("ROLE_ANONYMOUS")) {
			return "redirect:/login";
		}
		
		// 로그인한 상태라면　화면 이동
		return "admin/noticeWrite";
	}
	
	// 글쓰고 제출
	@PostMapping("/admin/submitNotice")
	public String submitNotice(Model model, BoardDTO notice) {
		// 로그인 정보를 가져와서 로그인을 하지 않은 상태면 로그인 화면으로 보냄
		UserDTO user = userUtil.getUserData();
		model.addAttribute("user", userUtil.getUserNameAndRole());
		if (user == null || user.getROLE().equals("ROLE_ANONYMOUS")) {
			return "redirect:/login";
		}

		// 데이터 베이스에 내용 입력
		adminService.setNotice(notice, user.getUser_no(), user.getUser_username());
		
		// 게시글 목록으로 돌아감
		return "redirect:/admin/admin";
	}
	
	// 휴지통 복구 - 게시글, 공지
	@PostMapping("/admin/postRestore")
	public String postRestore(Model model, @RequestParam(value = "postNum") String postNum) {
		// 로그인 정보를 가져와서 로그인을 하지 않은 상태면 로그인 화면으로 보냄
		UserDTO user = userUtil.getUserData();
		model.addAttribute("user", userUtil.getUserNameAndRole());
		if (user == null || user.getROLE().equals("ROLE_ANONYMOUS")) {
			return "redirect:/login";
		}
		
		String[] postnums = postNum.split(",");
		for (int i = 0; i < postnums.length; i++) {
			Long no = (long) Integer.parseInt(postnums[i]);
			adminService.restorePost(no);
		}
		
		// 게시글 목록으로 돌아감
		return "redirect:/admin/admin";
	}
	
	// 휴지통 복구 - 댓글
	@PostMapping("/admin/comRestore")
	public String comRestore(Model model, @RequestParam(value = "comNum") String comNum) {
		// 로그인 정보를 가져와서 로그인을 하지 않은 상태면 로그인 화면으로 보냄
		UserDTO user = userUtil.getUserData();
		model.addAttribute("user", userUtil.getUserNameAndRole());
		if (user == null || user.getROLE().equals("ROLE_ANONYMOUS")) {
			return "redirect:/login";
		}
		
		String[] comnums = comNum.split(",");
		for (int i = 0; i < comnums.length; i++) {
			Long num = (long) Integer.parseInt(comnums[i]);
			adminService.restoreComment(num);
		}
		
		// 게시글 목록으로 돌아감
		return "redirect:/admin/admin";
	}
	
	// 휴지통 영구 삭제 - 게시글, 공지
	@PostMapping("/admin/postEliminate")
	public String postEliminate(Model model, @RequestParam(value = "postNum") String postNum) {
		// 로그인 정보를 가져와서 로그인을 하지 않은 상태면 로그인 화면으로 보냄
		UserDTO user = userUtil.getUserData();
		model.addAttribute("user", userUtil.getUserNameAndRole());
		if (user == null || user.getROLE().equals("ROLE_ANONYMOUS")) {
			return "redirect:/login";
		}
		
		String[] postnums = postNum.split(",");
		for (int i = 0; i < postnums.length; i++) {
			Long no = (long) Integer.parseInt(postnums[i]);
			adminService.eliminatePost(no);
		}
		
		// 게시글 목록으로 돌아감
		return "redirect:/admin/admin";
	}
	
	// 휴지통 영구 삭제 - 댓글
	@PostMapping("/admin/comsEliminate")
	public String comsEliminate(Model model, @RequestParam(value = "comNum") String comNum) {
		// 로그인 정보를 가져와서 로그인을 하지 않은 상태면 로그인 화면으로 보냄
		UserDTO user = userUtil.getUserData();
		model.addAttribute("user", userUtil.getUserNameAndRole());
		if (user == null || user.getROLE().equals("ROLE_ANONYMOUS")) {
			return "redirect:/login";
		}
		
		String[] comnums = comNum.split(",");
		for (int i = 0; i < comnums.length; i++) {
			Long num = (long) Integer.parseInt(comnums[i]);
			adminService.eliminateComment(num);
		}
		
		// 게시글 목록으로 돌아감
		return "redirect:/admin/admin";
	}
}
