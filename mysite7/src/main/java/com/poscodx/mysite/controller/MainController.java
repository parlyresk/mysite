package com.poscodx.mysite.controller;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.poscodx.mysite.vo.UserVo;

@Controller
public class MainController {
	
	@Autowired
	private ServletContext servletContext;
	
	
	
	@RequestMapping({"/", "/main"})
    public String index(Model model) {
    	model.addAttribute("servletContext",servletContext);
    	
        return "main/index";
    }
    
    @ResponseBody
    @RequestMapping("/msg01")
    public String message01() {
    	return "Hello World";
    }
    
    @ResponseBody
    @RequestMapping("/msg02")
    public String message02(String name) {
    	return "안녕~~" + name;
    }
    
    @ResponseBody
    @RequestMapping("/msg03")
    public Object message03() {
    	UserVo vo = new UserVo();
    	vo.setNo(1L);
    	vo.setName("둘리");
    	vo.setEmail("dooly@gmail.com");
    	
    	return vo;
    }

}	
