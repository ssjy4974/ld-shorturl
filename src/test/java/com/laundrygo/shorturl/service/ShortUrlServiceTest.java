package com.laundrygo.shorturl.service;

import static org.junit.jupiter.api.Assertions.*;

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
}