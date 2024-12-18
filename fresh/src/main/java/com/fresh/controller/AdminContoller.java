package com.fresh.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fresh.dto.BoardDTO;
import com.fresh.dto.CommentDTO;
import com.fresh.dto.CustomCommentDTO;
import com.fresh.dto.UserDTO;
import com.fresh.service.AdminService;
import com.fresh.util.UserUtil;

@Controller
public class AdminContoller {

	@Autowired
	private AdminService adminService;

	@Autowired
	private UserUtil userUtil;
	
	
	//アドミン画面に遷移
	@GetMapping("/admin/admin")
	public String admin(Model model) {
		UserDTO user = userUtil.getUserNameAndRole();
		model.addAttribute("user", user);
		if (!user.getROLE().equals("ROLE_ADMIN")) {
			return "main";						//権限がアドミンではない場合、メイン画面に遷移
		}
		
		List<UserDTO> members = adminService.getMemList();				// 会員の情報を抽出して取り込む
		model.addAttribute("members", members);
		
		List<BoardDTO> posts = adminService.getAllPosts();				// ポストの情報を抽出して取り込む
		model.addAttribute("posts", posts);
		
		List<CommentDTO> comments = adminService.getAllComments(); // コメントの情報を抽出して取り込む
		model.addAttribute("comments", comments);
		
		List<BoardDTO> notices = adminService.getAllNotices();			 // お知らせの情報を抽出して取り込む
		model.addAttribute("notices", notices);
		
		List<BoardDTO> del_posts = adminService.getDelPosts();			// 削除したポストの情報を抽出して取り込む
		model.addAttribute("del_posts", del_posts);
		
		List<CommentDTO> del_coms = adminService.getDelComs();	// 削除したコメントの情報を抽出して取り込む
		model.addAttribute("del_coms", del_coms);
		
		List<BoardDTO> del_notis = adminService.getDelNotis();			// 削除したお知らせの情報を抽出して取り込む
		model.addAttribute("del_notis", del_notis);
		
		return "admin/admin";
	}
	
	@GetMapping("/admin/adminMem")
	public String adminMem(Model model, @RequestParam("user_no") Long user_no) {
		UserDTO user = userUtil.getUserNameAndRole();
		model.addAttribute("user", user);
		if (!user.getROLE().equals("ROLE_ADMIN")) {
			return "main";
		}
		
		UserDTO member = adminService.getOneMem(user_no);				// ユーザーの全ての情報を抽出して取り込む
		model.addAttribute("member", member);
		
		List<BoardDTO> memPosts = adminService.getMemPosts(user_no);	// ユーザーが作成した全てのポストの情報を抽出して取り込む
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
		
		adminService.setOneMem(member);			// 修正の反映 - update
		
		return "redirect:/admin/adminMem?user_no="+member.getUser_no();
	}
	
	@GetMapping("/admin/memDel")
	public String memDel(Model model, @RequestParam(value = "user_no") Long user_no) {
		UserDTO user = userUtil.getUserNameAndRole();
		model.addAttribute("user", user);
		if (!user.getROLE().equals("ROLE_ADMIN")) {
			return "main";
		}
		
		adminService.delOneMem(user_no);		// ユーザーの退会の処理
		return "redirect:/admin/admin";
	}
	
	@GetMapping("/admin/postDel")
	public String postDel(Model model, @RequestParam(value = "board_no") Long board_no) {
		UserDTO user = userUtil.getUserNameAndRole();
		model.addAttribute("user", user);
		if (!user.getROLE().equals("ROLE_ADMIN")) {
			return "main";
		}
		
		adminService.setOneBoard_del(board_no);		// ポストの削除の処理 - board_delのバリューを0にする
		return "redirect:/admin/admin";
	}
	
	@GetMapping("/admin/comDel")
	public String comDel(Model model, @RequestParam(value = "com_no") Long com_no) {
		UserDTO user = userUtil.getUserNameAndRole();
		model.addAttribute("user", user);
		if (!user.getROLE().equals("ROLE_ADMIN")) {
			return "main";
		}
		
		adminService.setOneCom_del(com_no);			// コメントの削除の処理 - com_delのバリューを0にする
		return "redirect:/admin/admin";
	}
	
	// お知らせ作成の画面に遷移
	@GetMapping("/admin/noticeWrite")
	public String noticeWrite(Model model) {
		// ログイン情報を取り込んでログイン中ではない場合はログイン画面に遷移
		UserDTO user = userUtil.getUserData();
		model.addAttribute("user", userUtil.getUserNameAndRole());
		if (user == null || user.getROLE().equals("ROLE_ANONYMOUS")) {
			return "redirect:/login";
		}
		
		
		return "admin/noticeWrite";			// ログイン中の場合はお知らせ作成の画面に遷移
	}
	
	// お知らせを作成して追加
	@PostMapping("/admin/submitNotice")
	public String submitNotice(Model model, BoardDTO notice) {
		// ログイン情報をもらってログインをしていない場合はログインページに移動
		UserDTO user = userUtil.getUserData();
		model.addAttribute("user", userUtil.getUserNameAndRole());
		if (user == null || user.getROLE().equals("ROLE_ANONYMOUS")) {
			return "redirect:/login";
		}

		adminService.setNotice(notice, user.getUser_no(), user.getUser_username());			// データベースに内容を入力
		return "redirect:/admin/admin";		// リストページへ
	}
	
	// ゴミ箱の復旧 - ポスト&お知らせ
	@PostMapping("/admin/postRestore")
	public String postRestore(Model model, @RequestParam(value = "postNum") String postNum) {
		// ログインの情報を取り込んでログイン中ではない場合はログイン画面に遷移
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
		
		return "redirect:/admin/admin";		// リスト画面に遷移
	}
	
	// ゴミ箱の復旧 - コメント
	@PostMapping("/admin/comRestore")
	public String comRestore(Model model, @RequestParam(value = "comNum") String comNum) {
		// ログインの情報を取り込んでログイン中ではない場合はログイン画面に遷移
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
		
		return "redirect:/admin/admin";			// リスト画面に遷移
	}
	
	// ゴミ箱、永久削除 - ポスト, お知らせ
	@PostMapping("/admin/postEliminate")
	public String postEliminate(Model model, @RequestParam(value = "postNum") String postNum) {
		// ログインの情報を取り込んでログイン中ではない場合はログイン画面に遷移
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
		
		return "redirect:/admin/admin";				// リスト画面に遷移
	}
	
	// ゴミ箱、永久削除 - コメント
	@PostMapping("/admin/comsEliminate")
	public String comsEliminate(Model model, @RequestParam(value = "comNum") String comNum) {
		// ログインの情報を取り込んでログイン中ではない場合はログイン画面に遷移
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
		
		// リスト画面に遷移
		return "redirect:/admin/admin";
	}
	
	@GetMapping("/admin/boardUpdate")
	public String boardUpdate(Model model, @RequestParam(value = "no") Long no) {
		// ログインの情報を取り込んでログイン中ではない場合はログイン画面に遷移
		UserDTO user = userUtil.getUserData();
		model.addAttribute("user", userUtil.getUserNameAndRole());
		if (user == null || user.getROLE().equals("ROLE_ANONYMOUS")) {
			return "redirect:/login";
		}
		BoardDTO boardUpdate = adminService.findById(no);
		model.addAttribute("boardUpdate", boardUpdate);
		// ポストの修正画面に遷移
		return "boardUpdate";
	}

	// 修正のボタンを押す場合、データベースにセーブ
	@PostMapping("/admin/boardUpdate")
	public String boardUpdate(BoardDTO board, Model model) {
		// ログインの情報を取り込んでログイン中ではない場合はログイン画面に遷移
		UserDTO user = userUtil.getUserData();
		model.addAttribute("user", userUtil.getUserNameAndRole());
		if (user == null || user.getROLE().equals("ROLE_ANONYMOUS")) {
			return "redirect:/login";
		}
		//データを更新
		adminService.boardUpdate(board);
		// FindByIDで修正された内容を抽出して取り込む
		return "redirect:/boardview?no=" + board.getBoard_no();
	}
}
