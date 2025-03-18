package com.laundrygo.shorturl.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import org.springframework.util.Assert;

/**
 * originUrl을 입력받아 shortUrl을 반환하는 클래스
 */
public abstract class UrlGenerator {

	private static final int SHORT_URL_LENGTH = 8;
	private static final String HASH_ALGORITHM = "MD5";

	public static String shorten(String originUrl) {
		Assert.hasText(originUrl, "URL은 필수 값 입니다.");
		byte[] hashBytes = createHash(originUrl);
		String urlSafeEncoded = Base64.getUrlEncoder().withoutPadding().encodeToString(hashBytes);
		String alphanumericOnly = urlSafeEncoded.replace("-", "A").replace("_", "Z");
		return alphanumericOnly.substring(0, SHORT_URL_LENGTH);
	}

	private static byte[] createHash(String input) {
		try {
			MessageDigest digest = MessageDigest.getInstance(HASH_ALGORITHM);
			return digest.digest(input.getBytes(StandardCharsets.UTF_8));
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(HASH_ALGORITHM + " algorithm is not available.", e);
		}
	}
}
