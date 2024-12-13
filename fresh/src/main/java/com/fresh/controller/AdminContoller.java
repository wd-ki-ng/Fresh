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
		
		// 会員の情報をもらう
		List<UserDTO> members = adminService.getMemList();
		model.addAttribute("members", members);
		
		// ポストの情報をもらう
		List<BoardDTO> posts = adminService.getAllPosts();
		model.addAttribute("posts", posts);
		
		// コメントの情報をもらう
		List<CustomCommentDTO> comments = adminService.getAllComments();
		model.addAttribute("comments", comments);
		
		// お知らせの情報をもらう
		List<BoardDTO> notices = adminService.getAllNotices();
		model.addAttribute("notices", notices);
		
		// 削除したポストの情報をもらう
		List<BoardDTO> del_posts = adminService.getDelPosts();
		model.addAttribute("del_posts", del_posts);
		
		// 削除したコメントの情報をもらう
		List<CustomCommentDTO> del_coms = adminService.getDelComs();
		model.addAttribute("del_coms", del_coms);
		
		// 削除したお知らせの情報をもらう
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
		// 照会したいユーザーの全ての情報をもらう
		UserDTO member = adminService.getOneMem(user_no);
		model.addAttribute("member", member);
		
		// ユーザーが作成した全てのポストの情報をもらう
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
		
		// 修正の反映 - update
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
		
		// ユーザーの退会の処理
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
		
		// ポストの削除の処理 - board_del을 0で
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
		
		// コメントの削除の処理 - com_del을 0で/submitNotice
		adminService.setOneCom_del(com_no);
		
		return "redirect:/admin/admin";
	}
	
	// お知らせの作成ページへ移動
	@GetMapping("/admin/noticeWrite")
	public String noticeWrite(Model model) {
		// ログインの情報をもらってログインをしていない場合はログインページに移動
		UserDTO user = userUtil.getUserData();
		model.addAttribute("user", userUtil.getUserNameAndRole());
		if (user == null || user.getROLE().equals("ROLE_ANONYMOUS")) {
			return "redirect:/login";
		}
		
		// ログインしている場合はお知らせの作成ページに移動
		return "admin/noticeWrite";
	}
	
	// お知らせを作成して、提出
	@PostMapping("/admin/submitNotice")
	public String submitNotice(Model model, BoardDTO notice) {
		// ログインの情報をもらってログインをしていない場合はログインページに移動
		UserDTO user = userUtil.getUserData();
		model.addAttribute("user", userUtil.getUserNameAndRole());
		if (user == null || user.getROLE().equals("ROLE_ANONYMOUS")) {
			return "redirect:/login";
		}

		// データベースに内容を入力
		adminService.setNotice(notice, user.getUser_no(), user.getUser_username());
		
		// リストページへ
		return "redirect:/admin/admin";
	}
	
	// ゴミ箱の復旧 - ポスト、お知らせ
	@PostMapping("/admin/postRestore")
	public String postRestore(Model model, @RequestParam(value = "postNum") String postNum) {
		// ログインの情報をもらって来てログインをしていない場合はログインページに移動
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
		
		// リストページへ
		return "redirect:/admin/admin";
	}
	
	// ゴミ箱の復旧 - コメント
	@PostMapping("/admin/comRestore")
	public String comRestore(Model model, @RequestParam(value = "comNum") String comNum) {
		// ログインの情報をもらって来てログインをしていない場合はログインページに移動
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
		
		// リストページへ
		return "redirect:/admin/admin";
	}
	
	// ゴミ箱、永久削除 - ポスト, お知らせ
	@PostMapping("/admin/postEliminate")
	public String postEliminate(Model model, @RequestParam(value = "postNum") String postNum) {
		// ログインの情報をもらって来てログインをしていない場合はログインページに移動
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
		
		// リストページへ
		return "redirect:/admin/admin";
	}
	
	// ゴミ箱、永久削除 - コメント
	@PostMapping("/admin/comsEliminate")
	public String comsEliminate(Model model, @RequestParam(value = "comNum") String comNum) {
		// ログインの情報をもらって来てログインをしていない場合はログインページに移動
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
		
		// リストページへ
		return "redirect:/admin/admin";
	}
	
	@GetMapping("/admin/boardUpdate")
	public String boardUpdate(Model model, @RequestParam(value = "no") Long no) {
		// ログインの情報をもらって来てログインをしていない場合はログインページに移動
		UserDTO user = userUtil.getUserData();
		model.addAttribute("user", userUtil.getUserNameAndRole());
		if (user == null || user.getROLE().equals("ROLE_ANONYMOUS")) {
			return "redirect:/login";
		}
		BoardDTO boardUpdate = adminService.findById(no);
		model.addAttribute("boardUpdate", boardUpdate);
		// ポストの修正ページ
		return "boardUpdate";
	}

	// 修正のボタンを押す場合、データベースにセーブ
	@PostMapping("/admin/boardUpdate")
	public String boardUpdate(BoardDTO board, Model model) {
		// ログインの情報をもらって来てログインをしていない場合はログインページに移動
		UserDTO user = userUtil.getUserData();
		model.addAttribute("user", userUtil.getUserNameAndRole());
		if (user == null || user.getROLE().equals("ROLE_ANONYMOUS")) {
			return "redirect:/login";
		}
		//Updateを要請
		adminService.boardUpdate(board);
		// FindByIDで修正された内容を照会
		return "redirect:/boardview?no=" + board.getBoard_no();
	}
}
