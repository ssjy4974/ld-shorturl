package com.laundrygo.shorturl.service;

import java.util.NoSuchElementException;
import java.util.UUID;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.laundrygo.shorturl.domain.UrlMapping;
import com.laundrygo.shorturl.repository.UrlAccessHistoryRepository;
import com.laundrygo.shorturl.repository.UrlMappingRepository;
import com.laundrygo.shorturl.utils.UrlGenerator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ShortUrlService {
	private final UrlMappingRepository urlMappingRepository;
	private final UrlAccessHistoryRepository urlAccessHistoryRepository;

	public String getShortUrl(String originUrl) {
		UrlMapping originUrlMapping = urlMappingRepository.findByOriginUrl(originUrl)
			.orElseGet(() -> createShortUrl(originUrl));
		return originUrlMapping.getShortUrl();
	}

	public UrlMapping getOriginUrl(String shortUrl) {
		return urlMappingRepository.findByShortUrl(shortUrl)
			.orElseThrow(() -> new NoSuchElementException("요청정보가 존재하지 않습니다."));
	}

	private UrlMapping createShortUrl(String originUrl) {
		try {
			String shorten = UrlGenerator.shorten(originUrl);
			return urlMappingRepository.save(UrlMapping.create(originUrl, shorten));
		} catch (DataIntegrityViolationException e) {
			String shorten = UrlGenerator.shorten(originUrl + UUID.randomUUID());
			return urlMappingRepository.save(UrlMapping.create(originUrl, shorten));
		}
	}
}
