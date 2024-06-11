package com.poscodx.mysite.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.poscodx.mysite.security.Auth;
import com.poscodx.mysite.service.BoardService;
import com.poscodx.mysite.vo.BoardVo;
import com.poscodx.mysite.vo.UserVo;

@Controller
@RequestMapping("/board")
public class BoardController {
	@Autowired
	private BoardService boardService;

	@RequestMapping("")
	public String index(Model model, @RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "keyword", defaultValue = "") String keyword) {

		Map<String, Object> map = boardService.getContentsList(page, keyword);

		model.addAllAttributes(map);
		model.addAttribute("keyword", keyword);

		return "board/list";
	}

	@RequestMapping("/view")
	public String view(@RequestParam("no") Long no, Model model,
			@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "keyword", defaultValue = "") String keyword) {

		BoardVo boardVo = boardService.getContents(no);
		model.addAttribute("boardVo", boardVo);
		model.addAttribute("no", no);
		model.addAttribute("page", page);
		model.addAttribute("keyword", keyword);
		return "board/view";
	}

	@Auth
	@RequestMapping("/delete")
	public String delete(@AuthUser UserVo authUser, @RequestParam("no") Long no, Model model,
			@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "keyword", defaultValue = "") String keyword) {

		boardService.deleteContents(no, authUser.getNo());
		return "redirect:/board?page=" + page + "&keyword=" + keyword;
	}

	@Auth
	@RequestMapping(value = "/write", method = RequestMethod.GET)
	public String write() {

		return "board/write";
	}

	@Auth
	@RequestMapping(value = "/write", method = RequestMethod.POST)
	public String write(@AuthUser UserVo authUser, @ModelAttribute BoardVo boardVo,
			@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "keyword", defaultValue = "") String keyword) {

		boardVo.setUserNo(authUser.getNo());

		boardService.addContents(boardVo);

		return "redirect:/board?page=" + page + "&keyword=" + keyword;
	}

	@Auth
	@RequestMapping(value = "/modify", method = RequestMethod.GET)
	public String modify(@AuthUser UserVo authUser, @RequestParam("no") Long no, Model model) {

		BoardVo boardVo = boardService.getContents(no, authUser.getNo());
		model.addAttribute("boardVo", boardVo);
		return "board/modify";
	}

	@Auth
	@RequestMapping(value = "/modify", method = RequestMethod.POST)
	public String modify(@AuthUser UserVo authUser, BoardVo boardVo,
			@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "keyword", defaultValue = "") String keyword) {

		boardVo.setUserNo(authUser.getNo());

		boardService.updateContents(boardVo);
		return "redirect:/board?page=" + page + "&keyword=" + keyword;
	}

	@Auth
	@RequestMapping(value = "/reply", method = RequestMethod.GET)
	public String reply(@AuthUser UserVo authUser, @RequestParam("no") Long no, Model model,
			@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "keyword", defaultValue = "") String keyword) {

		model.addAttribute("no", no);
		model.addAttribute("page", page);
		model.addAttribute("keyword", keyword);

		return "board/reply";
	}

	@Auth
	@RequestMapping(value = "/reply", method = RequestMethod.POST)
	public String reply(@AuthUser UserVo authUser, @RequestParam("no") Long no, @ModelAttribute BoardVo boardVo,
			@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "keyword", defaultValue = "") String keyword) {

		BoardVo parentboardVo = boardService.getContents(no);
		boardVo.setUserNo(authUser.getNo());
		boardVo.setGroupNo(parentboardVo.getGroupNo());
		boardVo.setOrderNo(parentboardVo.getOrderNo() + 1);
		boardVo.setDepth(parentboardVo.getDepth() + 1);

		boardService.replyContents(boardVo);

		return "redirect:/board?page=" + page + "&keyword=" + keyword;
	}
}
