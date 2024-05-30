package com.poscodx.mysite.controller.action.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.poscodx.mysite.controller.ActionServlet.Action;
import com.poscodx.mysite.dao.BoardDao;
import com.poscodx.mysite.vo.BoardVo;

public class ModifyFormAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Long no = Long.parseLong(request.getParameter("no"));
		int page=Integer.parseInt(request.getParameter("page"));

		BoardVo boardVo = new BoardVo();
		

		BoardDao boardDao = new BoardDao();
		boardVo=boardDao.findByBoardNo(no);
		
		request.setAttribute("boardVo", boardVo);
		request.setAttribute("page", page);
		request.getRequestDispatcher("/WEB-INF/views/board/modify.jsp").forward(request, response);

	}

}
