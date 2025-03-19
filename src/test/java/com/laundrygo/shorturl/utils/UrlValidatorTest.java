package com.laundrygo.shorturl.utils;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class UrlValidatorTest {

	@Test
	void validateShortUrl_ValidUrl_NoExceptionThrown() {
		// given
		String validShortUrl = "aabbccdd";

		// when & then
		assertDoesNotThrow(() -> UrlValidator.validateShortUrl(validShortUrl));
	}

	@ParameterizedTest
	@NullAndEmptySource
	@ValueSource(strings = {" ", "\t", "\n"})
	void validateShortUrl_EmptyOrBlankUrl(String invalidUrl) {
		// when & then
		Exception exception = assertThrows(IllegalArgumentException.class,
			() -> UrlValidator.validateShortUrl(invalidUrl));

		assertEquals("단축 URL은 필수 값입니다.", exception.getMessage());
	}

	@ParameterizedTest
	@ValueSource(strings = {"123456789", "abcdefghi"})
	void validateShortUrl_TooLongUrl(String tooLongUrl) {
		// when & then
		Exception exception = assertThrows(IllegalArgumentException.class,
			() -> UrlValidator.validateShortUrl(tooLongUrl));

		assertEquals("단축 URL은 8자를 초과할 수 없습니다.", exception.getMessage());
	}

	@ParameterizedTest
	@ValueSource(strings = {"abc-123", "test_url", "url@web", "한글123"})
	void validateShortUrl_InvalidCharacters(String urlWithInvalidChars) {
		// when & then
		Exception exception = assertThrows(IllegalArgumentException.class,
			() -> UrlValidator.validateShortUrl(urlWithInvalidChars));

		assertEquals("단축 URL은 영문자와 숫자만 포함할 수 있습니다.", exception.getMessage());
	}
}