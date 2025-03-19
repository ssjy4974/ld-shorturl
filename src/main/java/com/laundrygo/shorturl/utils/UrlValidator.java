package com.laundrygo.shorturl.utils;

import org.springframework.util.Assert;

public abstract class UrlValidator {
	private static final int MAX_SHORT_URL_LENGTH = 8;
	private static final String SHORT_URL_PATTERN = "^[a-zA-Z0-9]+$";

	public static void validateShortUrl(String shortUrl) {
		Assert.hasText(shortUrl, "단축 URL은 필수 값입니다.");
		Assert.isTrue(shortUrl.length() <= MAX_SHORT_URL_LENGTH,
			"단축 URL은 " + MAX_SHORT_URL_LENGTH + "자를 초과할 수 없습니다.");
		Assert.isTrue(shortUrl.matches(SHORT_URL_PATTERN),
			"단축 URL은 영문자와 숫자만 포함할 수 있습니다.");
	}
}
