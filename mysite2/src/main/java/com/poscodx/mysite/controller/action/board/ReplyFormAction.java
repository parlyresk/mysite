package com.poscodx.mysite.controller.action.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.poscodx.mysite.controller.ActionServlet.Action;

public class ReplyFormAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Long no = Long.parseLong(request.getParameter("no"));
		int page=Integer.parseInt(request.getParameter("page"));
		request.setAttribute("no", no);
		request.setAttribute("page", page);
		request.getRequestDispatcher("/WEB-INF/views/board/reply.jsp").forward(request, response);

	}

}
