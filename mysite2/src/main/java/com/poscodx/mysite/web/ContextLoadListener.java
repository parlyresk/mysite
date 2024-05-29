package com.poscodx.mysite.web;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ContextLoadListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		ServletContext sc = sce.getServletContext();
		String contextConfigLocation = sc.getInitParameter("contextConfigLocation");
		System.out.println("Application[Mysite2] starts..." + contextConfigLocation);
	}

	public ContextLoadListener() {

	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {

	}

}
