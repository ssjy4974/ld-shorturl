package com.laundrygo.shorturl.controller;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.laundrygo.shorturl.controller.request.ShortUrlRequest;
import com.laundrygo.shorturl.service.ShortUrlService;
import com.laundrygo.shorturl.utils.UrlValidator;

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

	@GetMapping("/origin-url/{shortUrl}")
	public ResponseEntity<String> getOriginUrl(@PathVariable String shortUrl) {
		UrlValidator.validateShortUrl(shortUrl);
		String originUrl = shortUrlService.getOriginUrl(shortUrl);
		return ResponseEntity.ok(originUrl);
	}
}

