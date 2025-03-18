package com.laundrygo.shorturl.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import com.laundrygo.shorturl.domain.UrlAccessHistory;
import com.laundrygo.shorturl.domain.UrlMapping;
import com.laundrygo.shorturl.repository.UrlAccessHistoryRepository;
import com.laundrygo.shorturl.repository.UrlMappingRepository;

@SpringBootTest
class ShortUrlServiceTest {

	@Autowired
	private ShortUrlService shortUrlService;
	@Autowired
	private UrlMappingRepository urlMappingRepository;
	@Autowired
	private UrlAccessHistoryRepository urlAccessHistoryRepository;

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
	void getOriginUrl_success() {
		// given
		String originUrl = "https://www.naver.com";
		String shortUrl = "abcdefgh";
		UrlMapping urlMapping = UrlMapping.create(originUrl, shortUrl);
		urlMappingRepository.save(urlMapping);

		// when
		String resultUrl = shortUrlService.getOriginUrl(shortUrl);

		// then
		// 원본 URL이 올바르게 반환되는지 확인
		assertNotNull(resultUrl);
		assertEquals(originUrl, resultUrl);

		// 이력이 저장되었는지 확인
		List<UrlAccessHistory> histories = urlAccessHistoryRepository.findByShortUrl(shortUrl);
		assertFalse(histories.isEmpty());
		assertEquals(1, histories.size());
		assertEquals(originUrl, histories.get(0).getOriginUrl());
		assertEquals(shortUrl, histories.get(0).getShortUrl());
	}

	@Test
	@DisplayName("존재하지 않는 단축url로 원본 url 조회 시 실패한다.")
	void getOriginUrl_fail_not_exist() {
		// given
		String nonExistentShortUrl = "nonexist";

		// when & then
		Exception exception = assertThrows(NoSuchElementException.class, () -> {
			shortUrlService.getOriginUrl(nonExistentShortUrl);
		});

		assertEquals("요청정보가 존재하지 않습니다.", exception.getMessage());

		// 접근 이력이 저장되지 않았는지 확인
		List<UrlAccessHistory> histories = urlAccessHistoryRepository.findByShortUrl(nonExistentShortUrl);
		assertTrue(histories.isEmpty());
	}

	@Test
	@DisplayName("동일한 단축URL 여러번 조회시 접근 이력이 누적된다.")
	void getOriginUrl_multiple_access() {
		// given:
		String originUrl = "https://www.naver.com";
		String shortUrl = "abcdefgh";
		UrlMapping urlMapping = UrlMapping.create(originUrl, shortUrl);
		urlMappingRepository.save(urlMapping);

		// when: 3번 연속 조회
		shortUrlService.getOriginUrl(shortUrl);
		shortUrlService.getOriginUrl(shortUrl);
		shortUrlService.getOriginUrl(shortUrl);

		// then: 접근 이력이 3개 생성되었는지 확인
		List<UrlAccessHistory> histories = urlAccessHistoryRepository.findByShortUrl(shortUrl);
		assertEquals(3, histories.size());

		// 모든 이력에 올바른 정보가 저장되었는지 확인
		for (UrlAccessHistory history : histories) {
			assertEquals(originUrl, history.getOriginUrl());
			assertEquals(shortUrl, history.getShortUrl());
		}
	}
}