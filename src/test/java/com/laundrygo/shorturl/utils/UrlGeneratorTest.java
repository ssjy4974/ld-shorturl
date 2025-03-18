package com.laundrygo.shorturl.utils;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

class UrlGeneratorTest {

	@Test
	@DisplayName("원본 url을 입력받아 8자의 단축 url을 생성하여 반환한다.")
	void createShortenUrl() {
		// given
		String originUrl = "http://www.naver.com";
		// when
		String shorten = UrlGenerator.shorten(originUrl);
		// then
		assertEquals(8, shorten.length());
	}

	@ParameterizedTest
	@NullAndEmptySource
	@DisplayName("입력받은 원본 url이 null인경우 예외를 반환한다.")
	void originUrlIsNull(String originUrl) {
		// when then
		assertThrows(IllegalArgumentException.class, () -> {
			UrlGenerator.shorten(originUrl);
		});
	}
}