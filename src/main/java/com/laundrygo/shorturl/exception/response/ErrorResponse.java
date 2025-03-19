package com.laundrygo.shorturl.exception.response;

import org.springframework.http.HttpStatus;

import lombok.Builder;
import lombok.Data;

@Data
public class ErrorResponse {

	private final HttpStatus code;

	private final String message;

	@Builder
	public ErrorResponse(HttpStatus code, String message) {
		this.code = code;
		this.message = message;
	}
}
