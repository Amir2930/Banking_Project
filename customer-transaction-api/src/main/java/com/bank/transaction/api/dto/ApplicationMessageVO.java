package com.bank.transaction.api.dto;

public class ApplicationMessageVO {

	private String message;
	private String url;
	private int code;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	@Override
	public String toString() {
		return "ApplicationMessageVO [message=" + message + ", url=" + url + ", code=" + code + "]";
	}

}
