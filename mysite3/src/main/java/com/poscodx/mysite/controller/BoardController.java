package com.poscodx.mysite.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

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
    public String index(Model model, @RequestParam(value="page",defaultValue = "1") int page, @RequestParam(value="keyword",defaultValue = "") String keyword) {
		
        Map<String, Object> map = boardService.getContentsList(page, keyword);
        
        model.addAllAttributes(map);
        model.addAttribute("keyword", keyword);
        
        
        
        return "board/list";
    }

	@RequestMapping("/view")
	public String view(@RequestParam("no") Long no, Model model,
			@RequestParam(value="page",defaultValue = "1") int page, @RequestParam(value="keyword",defaultValue = "") String keyword) {
		
		BoardVo boardVo = boardService.getContents(no);
		model.addAttribute("boardVo", boardVo);
		model.addAttribute("no", no);
		model.addAttribute("page", page);
		model.addAttribute("keyword", keyword);
		return "board/view";
	}

	@RequestMapping("/delete")
	public String delete(HttpSession session, @RequestParam("no") Long no, Model model,
			@RequestParam(value="page",defaultValue = "1") int page, @RequestParam(value="keyword",defaultValue = "") String keyword) {
	    // Access control
	    UserVo authUser = (UserVo) session.getAttribute("authUser");
	    if (authUser == null) {
	        return "redirect:/";
	    }
	    boardService.deleteContents(no, authUser.getNo());
	    return "redirect:/board?page=" + page + "&keyword=" + keyword;
	}
	
	@Auth
	@RequestMapping(value = "/write", method = RequestMethod.GET)
	public String write(HttpSession session) {
		// access control
				UserVo authUser = (UserVo)session.getAttribute("authUser");
				if(authUser == null) {
					return "redirect:/";
				}
	    return "board/write";
	}
	
	@Auth
	@RequestMapping(value = "/write",method = RequestMethod.POST)
	public String write(HttpSession session, @ModelAttribute BoardVo boardVo,@RequestParam(value="page",defaultValue = "1") int page, 
			@RequestParam(value="keyword",defaultValue = "") String keyword) {
	    // Access control
	    UserVo authUser = (UserVo) session.getAttribute("authUser");
	    if (authUser == null) {
	        return "redirect:/";
	    }
	    boardVo.setUserNo(authUser.getNo());
	    
		boardService.addContents(boardVo);
	    
	    
	    return "redirect:/board?page=" + page + "&keyword=" + keyword;
	}
	
	@RequestMapping(value = "/modify", method = RequestMethod.GET)
	public String modify(HttpSession session, @RequestParam("no") Long no, Model model) {
		// access control
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		if(authUser == null) {
			return "redirect:/";
		}
		////////////////////////
		
		BoardVo boardVo = boardService.getContents(no, authUser.getNo());
		model.addAttribute("boardVo", boardVo);
		return "board/modify";
	}

	@RequestMapping(value="/modify", method=RequestMethod.POST)	
	public String modify(
		HttpSession session, 
		BoardVo boardVo,
		@RequestParam(value="page",defaultValue = "1") int page, 
		@RequestParam(value="keyword",defaultValue = "") String keyword) {		
		// access control
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		if(authUser == null) {
			return "redirect:/";
		}
		////////////////////////
		
		boardVo.setUserNo(authUser.getNo());
		
		boardService.updateContents(boardVo);
		return "redirect:/board?page=" + page + "&keyword=" + keyword;
	}
	
	@RequestMapping(value = "/reply", method = RequestMethod.GET)
	public String reply(HttpSession session, @RequestParam("no") Long no,Model model,
			@RequestParam(value="page",defaultValue = "1") int page, @RequestParam(value="keyword",defaultValue = "") String keyword) {
		// access control
				UserVo authUser = (UserVo)session.getAttribute("authUser");
				if(authUser == null) {
					return "redirect:/";
				}
				
				
				model.addAttribute("no", no);
				model.addAttribute("page", page);
				model.addAttribute("keyword", keyword);
				
			
	    return "board/reply";
	}
	
	@RequestMapping(value="/reply", method=RequestMethod.POST)	
	public String reply(
		HttpSession session,
		@RequestParam("no") Long no,
		@ModelAttribute BoardVo boardVo,
		@RequestParam(value="page",defaultValue = "1") int page, 
		@RequestParam(value="keyword",defaultValue = "") String keyword) {
		// access control
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		if(authUser == null) {
			return "redirect:/";
		}
		////////////////////////
		
		
		
		
		BoardVo parentboardVo = boardService.getContents(no);
		boardVo.setUserNo(authUser.getNo());
		boardVo.setGroupNo(parentboardVo.getGroupNo());
		boardVo.setOrderNo(parentboardVo.getOrderNo() + 1);
		boardVo.setDepth(parentboardVo.getDepth() + 1);
		
		boardService.replyContents(boardVo);
		
		return "redirect:/board?page=" + page + "&keyword=" + keyword;
	}
}
