package com.laundrygo.shorturl.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.NoSuchElementException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import com.laundrygo.shorturl.domain.UrlMapping;
import com.laundrygo.shorturl.repository.UrlMappingRepository;

@SpringBootTest
class ShortUrlServiceTest {

	@Autowired
	private ShortUrlService shortUrlService;
	@Autowired
	private UrlMappingRepository urlMappingRepository;

	@Test
	@DisplayName("원본 url을 입력받아 단축 url을 반환한다.")
	@Rollback(false)
	void getShortUrl() {
		// given
		String originUrl = "http://www.naver.com";
		// when
		String shortUrl = shortUrlService.getShortUrl(originUrl);
		// then
		UrlMapping urlMapping = urlMappingRepository.findByOriginUrl(originUrl).get();
		assertNotNull(shortUrl);
		assertEquals(8, shortUrl.length());
		assertEquals(shortUrl, urlMapping.getShortUrl());
	}

	@Test
	@DisplayName("단축URL로 조회시 원본 URL을 반환한다.")
	public void getOriginUrl_success() {
		// given:
		String originUrl = "https://www.naver.com";
		String shortUrl = "abcdefgh";
		UrlMapping urlMapping = UrlMapping.create(originUrl, shortUrl);

		UrlMapping savedUrl = urlMappingRepository.save(urlMapping);

		// when:
		UrlMapping result = shortUrlService.getOriginUrl(savedUrl.getShortUrl());

		// then
		assertNotNull(result);
		assertEquals(originUrl, result.getOriginUrl());
		assertEquals(shortUrl, result.getShortUrl());
	}

	@Test
	@DisplayName("존재하지 않는 단축url로 원본 url 조회 시 실패한다.")
	public void getOriginUrl_fail_not_exist() {
		// given:
		String nonExistentShortUrl = "aabbccddeeggff";

		// when & then
		Exception exception = assertThrows(NoSuchElementException.class, () -> {
			shortUrlService.getOriginUrl(nonExistentShortUrl);
		});

		assertEquals("요청정보가 존재하지 않습니다.", exception.getMessage());
	}
}