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
	
	public Page<UrlStatResponse> getLast24HoursStatisticsWithPaging(Pageable pageable) {
		LocalDateTime startTime = LocalDateTime.now().minusHours(24);
		return urlAccessHistoryRepository.findRequestCountsLast24HoursWithPaging(startTime, pageable);
	}
}
