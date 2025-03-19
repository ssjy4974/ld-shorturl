package com.laundrygo.shorturl.service;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.laundrygo.shorturl.repository.UrlAccessHistoryRepository;
import com.laundrygo.shorturl.repository.response.UrlStatResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UrlStatService {
	private final UrlAccessHistoryRepository urlAccessHistoryRepository;

	/**
	 * 모든 URL의 최근 24시간 요청 통계를 페이징하여 조회합니다.
	 *
	 * @param pageable 페이징 정보 (페이지 번호, 페이지 크기, 정렬 정보)
	 * @return 페이징된 URL 통계 응답
	 */
	public Page<UrlStatResponse> getLast24HoursStatisticsWithPaging(Pageable pageable) {
		LocalDateTime startTime = LocalDateTime.now().minusHours(24);
		return urlAccessHistoryRepository.findRequestCountsLast24HoursWithPaging(startTime, pageable);
	}
}
