package com.poscodx.mysite.controller.action.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.poscodx.mysite.controller.ActionServlet.Action;
import com.poscodx.mysite.dao.BoardDao;

public class BoardAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String kwd=request.getParameter("kwd");
		

		String pageParam = request.getParameter("page");

		int page = (pageParam != null) ? Integer.parseInt(pageParam) : 1;
		int totalPosts = new BoardDao().getTotalPosts(kwd);
		int postsPerPage = 5;
		int totalPages = (int) Math.ceil((double) totalPosts / postsPerPage);
		
		
		int pageNavSize = 5;
	    int currentNavStart = ((page - 1) / pageNavSize) * pageNavSize + 1;
	    int currentNavEnd = currentNavStart+4;

		request.setAttribute("list", new BoardDao().findByPageAndKeyword(page,kwd));
		request.setAttribute("kwd", kwd);
		request.setAttribute("currentPage", page);
		request.setAttribute("totalPages", totalPages);
		request.setAttribute("totalPosts", totalPosts);
		request.setAttribute("postsPerPage", postsPerPage);
		request.setAttribute("currentNavStart", currentNavStart);
	    request.setAttribute("currentNavEnd", currentNavEnd);
	    
		request.getRequestDispatcher("/WEB-INF/views/board/list.jsp").forward(request, response);

	}

}
