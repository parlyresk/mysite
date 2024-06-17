package com.poscodx.mysite.event;

import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;

public class ApplicationContextEventListener {
	
	@EventListener({ContextRefreshedEvent.class})
	public void handleContextRefreshEvent() {
		System.out.println("--- Context Refreshed Event Received ---");
	}
}
