package com.poscodx.mysite.controller.action.board;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.poscodx.mysite.controller.ActionServlet.Action;
import com.poscodx.mysite.dao.BoardDao;
import com.poscodx.mysite.vo.BoardVo;
import com.poscodx.mysite.vo.UserVo;

public class ReplyAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		UserVo authUser = (UserVo) session.getAttribute("authUser");
		if (authUser == null) {
			response.sendRedirect(request.getContextPath());
			return;
		}
		BoardDao boardDao = new BoardDao();
		
		Long no = Long.parseLong(request.getParameter("no"));
		int page=Integer.parseInt(request.getParameter("page"));
		
		Map<String, Long> groupNoAndOrderNoAndDepth = boardDao.findGroupAndOrderNoAndDepthByNo(no);
		Long g_no = groupNoAndOrderNoAndDepth.get("g_no");
        Long o_no = groupNoAndOrderNoAndDepth.get("o_no");
        Long depth = groupNoAndOrderNoAndDepth.get("depth");
        
		String title = request.getParameter("title");
		String contents = request.getParameter("content");
		
		
		

		BoardVo boardVo = new BoardVo();
		boardVo.setTitle(title);
		boardVo.setContents(contents);
		boardVo.setUserNo(authUser.getNo());

		BoardVo ParentboardVo = new BoardVo();
		ParentboardVo.setGroupNo(g_no);
		ParentboardVo.setOrderNo(o_no+1);
		ParentboardVo.setDepth(depth+1);
		boardDao.reply(boardVo,ParentboardVo);

		response.sendRedirect(request.getContextPath() + "/board?page="+page);

	}

}
