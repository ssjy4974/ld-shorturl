package com.laundrygo.shorturl.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * originUrl을 입력받아 shortUrl을 반환하는 클래스
 */
public abstract class UrlGenerator {

	private static final int SHORT_URL_LENGTH = 8;

	public static String shorten(String originUrl) {
		if (originUrl == null || originUrl.trim().isEmpty()) {
			throw new IllegalArgumentException("The provided URL cannot be null or empty.");
		}
		MessageDigest digest = null;
		try {
			digest = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("MD5 algorithm is not available.");
		}
		byte[] hashBytes = digest.digest(originUrl.getBytes(StandardCharsets.UTF_8));

		String base64Encoded = Base64.getEncoder().encodeToString(hashBytes);

		String urlSafe = base64Encoded.replace("+", "")
			.replace("/", "")
			.replace("=", "");

		return urlSafe.substring(0, SHORT_URL_LENGTH);
	}
}
