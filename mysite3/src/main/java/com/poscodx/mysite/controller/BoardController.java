package com.poscodx.mysite.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.poscodx.mysite.service.BoardService;
import com.poscodx.mysite.vo.BoardVo;
import com.poscodx.mysite.vo.UserVo;

@Controller
@RequestMapping("/board")
public class BoardController {
	@Autowired
	private BoardService boardService;

	@RequestMapping("")
    public String index(Model model, @RequestParam(value="page",defaultValue = "1") int page, @RequestParam(value="keyword",defaultValue = "") String keyword) {
		System.out.println("index come");
        Map<String, Object> map = boardService.getContentsList(page, keyword);
        model.addAllAttributes(map);
        model.addAttribute("keyword", keyword);
        return "board/list";
    }

	@RequestMapping("/view/{no}/{page}/{keyword}")
	public String view(@PathVariable("no") Long no, Model model,@PathVariable("page") int page,@PathVariable("keyword") String keyword) {
		BoardVo boardVo = boardService.getContents(no);
		model.addAttribute("boardVo", boardVo);
		model.addAttribute("no", no);
		model.addAttribute("page", page);
		model.addAttribute("keyword", keyword);
		return "board/view";
	}

	@RequestMapping("/delete/{no}/{page}/{keyword}")
	public String delete(HttpSession session, @PathVariable("no") Long no, @PathVariable("page") int page,@PathVariable("keyword") String keyword) {
	    // Access control
	    UserVo authUser = (UserVo) session.getAttribute("authUser");
	    if (authUser == null) {
	        return "redirect:/";
	    }
	    boardService.deleteContents(session, no, authUser.getNo());
	    return "redirect:/board/" + page +"/"+keyword;
	}
	
	@RequestMapping(value = "/write", method = RequestMethod.GET)
	public String write() {
	    return "board/write";
	}
	
	@RequestMapping(value = "/write/{no}/{page}/{keyword}",method = RequestMethod.POST)
	public String write(HttpSession session, @PathVariable("no") Long no, @PathVariable("page") int page,@PathVariable("page") String keyword,@RequestParam String title,@RequestParam String contents) {
	    // Access control
	    UserVo authUser = (UserVo) session.getAttribute("authUser");
	    if (authUser == null) {
	        return "redirect:/";
	    }
	    BoardVo vo=new BoardVo();
	    vo.setTitle(title);
	    vo.setContents(contents);
	    vo.setUserNo(authUser.getNo());
	    boardService.addContents(vo);
	    
	    return "redirect:/board/" + page + "/" + keyword;
	}
	
	@RequestMapping("/modify/{no}")	
	public String modify(HttpSession session, @PathVariable("no") Long no, Model model) {
		// access control
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		if(authUser == null) {
			return "redirect:/";
		}
		////////////////////////
		
		BoardVo boardVo = boardService.getContents(no);
		model.addAttribute("boardVo", boardVo);
		return "board/modify";
	}

	@RequestMapping(value="/modify", method=RequestMethod.POST)	
	public String modify(
		HttpSession session, 
		BoardVo boardVo,
		@RequestParam(value="page") int page,
		@RequestParam(value="keyword") String keyword) {		
		// access control
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		if(authUser == null) {
			return "redirect:/";
		}
		////////////////////////
		
		boardVo.setUserNo(authUser.getNo());
		boardService.updateContents(boardVo);
		return "redirect:/board/" + page + "/" + keyword;
	}
	
	@RequestMapping(value="/reply/{no}")	
	public String reply(
		HttpSession session,
		@PathVariable("no") Long no,
		Model model) {
		// access control
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		if(authUser == null) {
			return "redirect:/";
		}
		////////////////////////
		
		BoardVo boardVo = boardService.getContents(no);
		boardVo.setOrderNo(boardVo.getOrderNo() + 1);
		boardVo.setDepth(boardVo.getDepth() + 1);
		
		model.addAttribute("boardVo", boardVo);
		
		return "board/reply";
	}
}
