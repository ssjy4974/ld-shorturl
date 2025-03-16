package com.laundrygo.shorturl.exception.advice;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.laundrygo.shorturl.exception.response.ErrorResponse;

@RestControllerAdvice()
public class ShortUrlControllerAdvice {

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ErrorResponse invalidRequestHandler(MethodArgumentNotValidException e) {
		FieldError fieldError = e.getFieldErrors().get(0);
		return ErrorResponse.builder()
			.code(HttpStatus.BAD_REQUEST)
			.message(fieldError.getDefaultMessage())
			.build();
	}
}
