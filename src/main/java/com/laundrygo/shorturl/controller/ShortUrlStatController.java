package com.laundrygo.shorturl.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.laundrygo.shorturl.repository.response.UrlStatResponse;
import com.laundrygo.shorturl.service.UrlStatService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/statistics")
public class ShortUrlStatController {

	private final UrlStatService urlStatService;

	@GetMapping("/last24hours")
	public ResponseEntity<Page<UrlStatResponse>> getLast24HoursStatisticsWithPaging(
		@PageableDefault(size = 10) Pageable pageable) {

		Page<UrlStatResponse> statistics = urlStatService.getLast24HoursStatisticsWithPaging(pageable);
		return ResponseEntity.ok(statistics);
	}

}
