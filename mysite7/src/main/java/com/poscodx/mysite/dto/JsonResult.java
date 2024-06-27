package com.poscodx.mysite.dto;

public class JsonResult {
	private String result; // "success" or "fail"
	private String message; // if fail, set "error message"
	private Object data; // if success, set

	private JsonResult(Object data) {
		this.result = "success";
		this.data = data;
	}

	private JsonResult(String message) {
		this.result = "fail";
		this.message = message;
	}

	public static JsonResult success(Object data) {

		return new JsonResult(data);
	}

	public static JsonResult fail(String message) {

		return new JsonResult(message);
	}

	public String getResult() {
		return result;
	}

	public String getMessage() {
		return message;
	}

	public Object getData() {
		return data;
	}

}
