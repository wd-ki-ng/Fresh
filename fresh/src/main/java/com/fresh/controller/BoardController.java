package com.fresh.controller;

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
}
