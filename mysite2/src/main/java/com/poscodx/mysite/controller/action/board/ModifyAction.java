package com.poscodx.mysite.controller.action.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.poscodx.mysite.controller.ActionServlet.Action;
import com.poscodx.mysite.dao.BoardDao;

public class ModifyAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int page=Integer.parseInt(request.getParameter("page"));
		String title=request.getParameter("title");
		String contents=request.getParameter("content");
		Long no=Long.parseLong(request.getParameter("no"));
		BoardDao boardDao = new BoardDao();
		boardDao.updateWithTitleAndContentsByNo(title,contents,no);
		
		response.sendRedirect(request.getContextPath() + "/board?page="+page);

	}

}
