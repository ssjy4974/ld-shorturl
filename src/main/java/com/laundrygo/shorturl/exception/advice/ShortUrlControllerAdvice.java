package com.laundrygo.shorturl.exception.advice;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.laundrygo.shorturl.exception.response.ErrorResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
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

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(DataIntegrityViolationException.class)
	public ErrorResponse dataIntegrityViolationException(DataIntegrityViolationException e) {
		// 단축 url 생성 후 insert 시도 시 중복 url 인 경우 재시도 1번 수행 후 이후에도 실패하면 advice에서 catch
		log.error("DataIntegrityViolationException :  {}", e.getMessage(), e);
		return ErrorResponse.builder()
			.code(HttpStatus.BAD_REQUEST)
			.message("단축URL 생성에 실패하였습니다. 재시도 부탁드립니다.")
			.build();
	}
}
