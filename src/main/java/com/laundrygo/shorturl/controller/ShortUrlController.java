package com.laundrygo.shorturl.controller;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.laundrygo.shorturl.controller.request.ShortUrlRequest;
import com.laundrygo.shorturl.service.ShortUrlService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class ShortUrlController {
	private final ShortUrlService shortUrlService;

	@PostMapping("/short-url")
	public ResponseEntity<String> getShortUrl(@RequestBody @Valid ShortUrlRequest request) {
		return ResponseEntity.ok(shortUrlService.getShortUrl(request.getOriginUrl()));
	}
}

