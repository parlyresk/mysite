package com.poscodx.mysite.controller.action.guestbook;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.poscodx.mysite.controller.ActionServlet.Action;
import com.poscodx.mysite.dao.GuestbookDao;
import com.poscodx.mysite.vo.GuestbookVo;

public class AddAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		String contents = request.getParameter("contents");
		String regDate = request.getParameter("reg_date");

		GuestbookVo vo = new GuestbookVo();
		vo.setName(name);
		vo.setPassword(password);
		vo.setContents(contents);
		vo.setRegDate(regDate);

		GuestbookDao dao = new GuestbookDao();
		dao.insert(vo);

		response.sendRedirect(request.getContextPath() + "/guestbook");

	}

}
