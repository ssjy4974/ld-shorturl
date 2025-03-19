package com.laundrygo.shorturl.service;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import com.laundrygo.shorturl.domain.UrlAccessHistory;
import com.laundrygo.shorturl.domain.UrlMapping;
import com.laundrygo.shorturl.repository.UrlAccessHistoryRepository;
import com.laundrygo.shorturl.repository.UrlMappingRepository;
import com.laundrygo.shorturl.repository.response.UrlStatResponse;

@SpringBootTest
@Transactional
class UrlStatServiceTest {
	@Autowired
	UrlStatService urlStatService;

	@Autowired
	private UrlAccessHistoryRepository urlAccessHistoryRepository;

	@Autowired
	private UrlMappingRepository urlMappingRepository;

	private LocalDateTime now;

	@BeforeEach
	void setUp() {
		// 테스트 데이터 초기화
		urlAccessHistoryRepository.deleteAll();
		urlMappingRepository.deleteAll();

		now = LocalDateTime.now();

		// 테스트용 URL 매핑 생성
		createUrlMappings();

		// 테스트용 접근 기록 생성
		createUrlAccessHistories();
	}

	@Test
	@DisplayName("최근 24시간 URL 통계를 페이징하여 조회한다")
	void getLast24HoursStatisticsWithPaging() {
		// given
		Pageable pageable = PageRequest.of(0, 2);

		// when
		Page<UrlStatResponse> result = urlStatService.getLast24HoursStatisticsWithPaging(pageable);

		// then
		assertThat(result).isNotNull();
		assertThat(result.getContent()).hasSize(2);
		assertThat(result.getTotalElements()).isEqualTo(3);
		assertThat(result.getTotalPages()).isEqualTo(2);

		// 첫 번째 페이지 내용 확인
		assertThat(result.getContent().get(0).getShortUrl()).isEqualTo("abc123");
		assertThat(result.getContent().get(0).getRequestCount()).isEqualTo(5L);

		assertThat(result.getContent().get(1).getShortUrl()).isEqualTo("def456");
		assertThat(result.getContent().get(1).getRequestCount()).isEqualTo(3L);
	}

	@Test
	@DisplayName("두 번째 페이지의 URL 통계를 정상적으로 조회한다")
	void getLast24HoursStatisticsWithPaging_SecondPage() {
		// given
		Pageable pageable = PageRequest.of(1, 2);

		// when
		Page<UrlStatResponse> result = urlStatService.getLast24HoursStatisticsWithPaging(pageable);

		// then
		assertThat(result).isNotNull();
		assertThat(result.getContent()).hasSize(1); // 두 번째 페이지에는 1개만 있음
		assertThat(result.getNumber()).isEqualTo(1); // 페이지 번호 (0부터 시작)

		// 두 번째 페이지 내용 확인
		assertThat(result.getContent().get(0).getShortUrl()).isEqualTo("ghi789");
		assertThat(result.getContent().get(0).getRequestCount()).isEqualTo(2L);
	}

	@Test
	@DisplayName("빈 결과가 있는 경우 정상적으로 처리한다")
	void getLast24HoursStatisticsWithPaging_EmptyResult() {
		// given
		urlAccessHistoryRepository.deleteAll(); // 모든 접근 기록 삭제
		Pageable pageable = PageRequest.of(0, 10);

		// when
		Page<UrlStatResponse> result = urlStatService.getLast24HoursStatisticsWithPaging(pageable);

		// then
		assertThat(result).isNotNull();
		assertThat(result.getContent()).isEmpty();
		assertThat(result.getTotalElements()).isEqualTo(0);
	}

	private void createUrlMappings() {
		// URL 매핑 생성
		UrlMapping mapping1 = UrlMapping.create("https://naver.com/page1", "abc123");
		urlMappingRepository.save(mapping1);

		UrlMapping mapping2 = UrlMapping.create("https://naver.com/page2", "def456");
		urlMappingRepository.save(mapping2);

		UrlMapping mapping3 = UrlMapping.create("https://naver.com/page3", "ghi789");
		urlMappingRepository.save(mapping3);
	}

	private void createUrlAccessHistories() {
		// abc123에 대한 접근 기록 5개 생성 (최근 24시간 내)
		for (int i = 0; i < 5; i++) {
			UrlAccessHistory history = UrlAccessHistory.create("https://naver.com/page1", "abc123");
			// 새로 추가된 requestDttm 필드 설정
			history.setTestRequestDttm(now.minusHours(i));
			urlAccessHistoryRepository.save(history);
		}

		// def456에 대한 접근 기록 3개 생성
		for (int i = 0; i < 3; i++) {
			UrlAccessHistory history = UrlAccessHistory.create("https://naver.com/page2", "def456");
			history.setTestRequestDttm(now.minusHours(i + 1));
			urlAccessHistoryRepository.save(history);
		}

		// ghi789에 대한 접근 기록 2개 생성
		for (int i = 0; i < 2; i++) {
			UrlAccessHistory history = UrlAccessHistory.create("https://naver.com/page3", "ghi789");
			history.setTestRequestDttm(now.minusHours(i + 2));
			urlAccessHistoryRepository.save(history);
		}

		// 24시간 이전의 접근 기록 생성 (통계에 포함되지 않아야 함)
		UrlAccessHistory oldHistory = UrlAccessHistory.create("https://naver.com/page1", "abc123");
		oldHistory.setTestRequestDttm(now.minusHours(25));
		urlAccessHistoryRepository.save(oldHistory);
	}
}
