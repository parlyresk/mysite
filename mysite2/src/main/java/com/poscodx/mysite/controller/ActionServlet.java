package com.poscodx.mysite.controller;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class ActionServlet extends HttpServlet {
	private static final long serialVersionUID=1L;
	
	protected abstract Action getAction(String actionName);

	@Override
	protected void doGet(HttpServletRequest reqest, HttpServletResponse response) throws ServletException, IOException {
		
		reqest.setCharacterEncoding("utf-8");
		String actionName = Optional.ofNullable(reqest.getParameter("a")).orElse("");
		if(actionName == null) {
			actionName="";
		}
		Action action = getAction(actionName);
		if(action==null) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST,"...");
			return;
		}
		
		action.execute(reqest,response);
		
	}

	

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}
	
	public static interface Action {

		void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
		
		
	}
}
