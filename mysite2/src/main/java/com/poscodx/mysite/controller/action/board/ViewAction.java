package com.poscodx.mysite.controller.action.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.poscodx.mysite.controller.ActionServlet.Action;
import com.poscodx.mysite.dao.BoardDao;
import com.poscodx.mysite.vo.BoardVo;
import com.poscodx.mysite.vo.UserVo;

public class ViewAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		UserVo authUser = (UserVo) session.getAttribute("authUser");
		
		
		Long no = Long.parseLong(request.getParameter("no"));
		int page=Integer.parseInt(request.getParameter("page"));
		boolean isVisited = false;
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                if (("visit_" + no).equals(cookie.getName())) {
                    isVisited = true;
                    break;
                }
            }
        }

        if (!isVisited) {
            
            BoardDao boardDao = new BoardDao();
            boardDao.increaseHit(no);

            
            Cookie cookie = new Cookie("visit_" + no, "true");
            cookie.setPath(request.getContextPath());
            cookie.setMaxAge(24 * 60 * 60); 
            response.addCookie(cookie);
        }
		

		BoardVo boardVo = new BoardVo();
		

		BoardDao boardDao = new BoardDao();
		boardVo=boardDao.findByBoardNo(no);
		request.setAttribute("boardVo", boardVo);
		request.setAttribute("page", page);
		request
		.getRequestDispatcher("/WEB-INF/views/board/view.jsp")
		.forward(request, response);

	}

}
