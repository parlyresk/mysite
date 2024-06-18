package com.poscodx.mysite.exception;

public class UserRepositoryException extends RuntimeException {
	private static final long serialVersionUID=1L;
	
	public UserRepositoryException() {
		super("UserRepositoryException Occurs");
	}
	
	public UserRepositoryException(String message) {
		super(message);
	}
}
