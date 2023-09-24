package com.excel.handler.exceptions;

import lombok.Getter;

@Getter
public class UserGeneralException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final int code;

	private final String message;

	public UserGeneralException(int code, String message) {
		super(message);
		this.code = code;
		this.message = message;
	}

}
