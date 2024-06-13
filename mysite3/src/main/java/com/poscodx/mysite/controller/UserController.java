package com.poscodx.mysite.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.poscodx.mysite.security.Auth;
import com.poscodx.mysite.service.UserService;
import com.poscodx.mysite.vo.UserVo;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService userService;
	
	@RequestMapping(value="/join",method=RequestMethod.GET)
	public String join() {
		return "user/join";
		
	}
	
	@RequestMapping(value="/join",method=RequestMethod.POST)
	public String join(@ModelAttribute @Valid UserVo vo, BindingResult result, Model model) {
		
		if(result.hasErrors()) {
			

			model.addAllAttributes(result.getModel());
			return "user/join";
		}
		
		// userService.join(vo);
		
		
		return "redirect:/user/joinsuccess";
		
	}
	
	@RequestMapping(value="/joinsuccess",method=RequestMethod.GET)
	public String joinsuccess() {
		return "user/joinsuccess";
		
	}
	
	@RequestMapping(value="/login",method=RequestMethod.GET)
	public String login() {
		return "user/login";
	}
	
	
	
	
	
	@Auth
	@RequestMapping(value="/update",method=RequestMethod.GET)
	public String update(@AuthUser UserVo authUser, Model model) {
		
		UserVo vo = userService.getUser(authUser.getNo());
		model.addAttribute("userVo",vo);
		
		return "user/update";
	}
	
	@Auth(value="hello")
	@RequestMapping(value="/update",method=RequestMethod.POST)
	public String update(@AuthUser UserVo authUser, UserVo vo) {
		// access control
		
		if(authUser==null) {
			return "redirect:/";
		}
		
		vo.setNo(authUser.getNo());
		userService.update(vo);
		
		authUser.setName(vo.getName());
		return "redirect:/user/update";
	}
}
